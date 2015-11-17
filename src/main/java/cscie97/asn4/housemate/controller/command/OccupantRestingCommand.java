package cscie97.asn4.housemate.controller.command;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn4.housemate.model.appliance.Light;
import cscie97.asn4.housemate.model.service.exception.EntityExistsException;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;
import cscie97.asn4.housemate.model.support.OccupantActivityStatus;

import java.util.Set;

/**
 * This command should be executed when an occupant is detected as 'resting'.
 * */
public class OccupantRestingCommand extends ControllerCommand {

    private final String roomId;

    private final String houseId;

    private final String occupantId;

    public OccupantRestingCommand(AccessToken accessToken, String houseId, String roomId, String occupantId) {
        super(accessToken);
        
        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";
        assert occupantId!=null && !"".equals(occupantId) : "Occupant id cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
        this.occupantId = occupantId.toLowerCase();
    }

    /**
     * This method sets occupant's current location as this room, and set their status to 'resting'.
     * If there are no other occupants in this room, then it sets intensity of all lights in
     * this room to low.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        modelService.setOccupantLocation(accessToken, occupantId, houseId, roomId);
        modelService.setOccupantActivityStatus(accessToken, occupantId, OccupantActivityStatus.Resting);

        Set<String> occupantIds = modelService.getOccupantIds(accessToken, houseId, roomId);

        if(occupantIds.size() == 1){
            Set<String> lightIds = modelService.getDeviceIds(accessToken, houseId, roomId, Light.DEVICE_TYPE);
            for (String lightId : lightIds){
                modelService.setDeviceStatus(accessToken, houseId, roomId, lightId, Light.INTENSITY , Light.LOW_INTENSITY.toString());
            }
        }

    }
}
