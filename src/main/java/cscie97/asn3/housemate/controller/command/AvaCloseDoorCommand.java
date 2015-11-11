package cscie97.asn3.housemate.controller.command;

import cscie97.asn3.housemate.controller.command.ControllerCommand;
import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.appliance.Door;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

import java.util.Set;

/**
 * This command should be executed when Ava is asked for closing doors.
 */
public class AvaCloseDoorCommand extends ControllerCommand {

    private final String houseId;

    private final String roomId;

    public AvaCloseDoorCommand(AccessToken accessToken, String houseId, String roomId) {
        super(accessToken);

        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
    }

    /**
     * This method closes all the doors in this Ava's room.
     * @throws EntityNotFoundException if the given house or room do not exists.
     * @throws InvalidStatusException if the door appliance does not accept CLOSED mode.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException {
        Set<String> doorIds = modelService.getDeviceIds(accessToken, houseId, roomId, Door.DEVICE_TYPE);
        for (String doorId : doorIds) {
            modelService.setDeviceStatus(accessToken, houseId, roomId, doorId, Door.MODE, Door.CLOSED);
        }
    }
}
