package cscie97.asn3.housemate.controller.command;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.appliance.Ava;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

/**
 * This command should be used when an Ava is asked to locate another occupant in the same house.
 * Note that, for security reasons, Ava will not report location of an occupant in a different house.
 */
public class AvaOccupantLocationCommand extends ControllerCommand{

    private final String avaId;

    private final String occupantId;

    private final String roomId;

    private final String houseId;


    public AvaOccupantLocationCommand(AccessToken accessToken, String houseId, String roomId, String avaId, String occupantId) {
        super(accessToken);

        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";

        assert avaId!=null || !"".equals(avaId) : "Ava identifier cannot be null or empty string";
        assert occupantId!=null || !"".equals(occupantId) : "Occupant identifier cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
        this.avaId = avaId;
        this.occupantId = occupantId;
    }

    /**
     * This method calls the HouseMateModelServiceImpl to locate the given occupant.
     * It then communicates this location via given Ava.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException {
        String occupantLocation = modelService.getOccupantLocation(accessToken, occupantId, houseId);

        String avaMessage = null;
        if(occupantLocation == null){
            avaMessage = "Location of " + occupantId + " is unknown.";
        }else {
            avaMessage = occupantId + " is in " + occupantLocation;
        }
        modelService.setDeviceStatus(accessToken, houseId, roomId, avaId, Ava.SPEAKING, avaMessage);
    }
}
