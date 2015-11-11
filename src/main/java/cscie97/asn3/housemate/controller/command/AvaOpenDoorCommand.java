package cscie97.asn3.housemate.controller.command;

import cscie97.asn3.housemate.controller.command.ControllerCommand;
import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.appliance.Door;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

import java.util.Set;

/**
 * This command should be used to open all the doors in this Ava's room.
 */
public class AvaOpenDoorCommand extends ControllerCommand {

    private final String houseId;

    private final String roomId;

    public AvaOpenDoorCommand(AccessToken accessToken, String houseId, String roomId) {
        super(accessToken);

        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
    }

    /**
     * This method opens all the doors in this Ava's room.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException {
        Set<String> doorIds = modelService.getDeviceIds(accessToken, houseId, roomId, Door.DEVICE_TYPE);
        for (String doorId : doorIds) {
            modelService.setDeviceStatus(accessToken, houseId, roomId, doorId, Door.MODE, Door.OPEN);
        }
    }
}
