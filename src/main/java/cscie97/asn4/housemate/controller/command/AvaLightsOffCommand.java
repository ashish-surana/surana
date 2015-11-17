package cscie97.asn4.housemate.controller.command;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn4.housemate.model.appliance.Light;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;

import java.util.Set;

/**
 * This command should be executed when Ava is asked to turn off lights.
 */
public class AvaLightsOffCommand extends ControllerCommand {

    private final String houseId;

    private final String roomId;

    public AvaLightsOffCommand(AccessToken accessToken, String houseId, String roomId) {
        super(accessToken);

        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
    }

    /**
     * This method retrieves all light appliances in this Ava's room, and sets their MODE to OFF.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, AccessDeniedException, InvalidAccessTokenException {
        Set<String> lightIds = modelService.getDeviceIds(accessToken, houseId, roomId, Light.DEVICE_TYPE);
        for (String lightId : lightIds) {
            modelService.setDeviceStatus(accessToken, houseId, roomId, lightId, Light.MODE, Light.OFF);
        }
    }
}
