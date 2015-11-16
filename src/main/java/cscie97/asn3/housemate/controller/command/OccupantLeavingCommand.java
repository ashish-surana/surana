package cscie97.asn3.housemate.controller.command;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn3.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn3.housemate.model.appliance.Light;
import cscie97.asn3.housemate.model.appliance.Thermostat;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

import java.util.Set;

/**
 * This command fires appropriate rules when an occupants leaves a room.
 */
public class OccupantLeavingCommand extends ControllerCommand {

    private final String roomId;

    private final String houseId;

    private final String occupantId;

    public OccupantLeavingCommand(AccessToken accessToken, String houseId, String roomId, String occupantId) {
        super(accessToken);
        
        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";
        assert occupantId!=null && !"".equals(occupantId) : "Occupant id cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
        this.occupantId = occupantId.toLowerCase();
    }

    /**
     * This command sets occupant's current location as unknown. If there are no more occupants in this
     * room, then it turns off all the lights of this room, and sets all the thermostats of this room
     * to standby temperature.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        modelService.unSetOccupantLocation(accessToken, occupantId);

        Set<String> occupantIds = modelService.getOccupantIds(accessToken, houseId, roomId);

        if(occupantIds.isEmpty()){
            Set<String> lightIds = modelService.getDeviceIds(accessToken, houseId, roomId, Light.DEVICE_TYPE);
            for (String lightId : lightIds){
                modelService.setDeviceStatus(accessToken, houseId, roomId, lightId, Light.MODE , Light.OFF);
            }

            Set<String> thermostatIds = modelService.getDeviceIds(accessToken, houseId, roomId, Thermostat.DEVICE_TYPE);
            for (String thermostatId : thermostatIds){
                modelService.setDeviceStatus(accessToken, houseId, roomId, thermostatId, Thermostat.TEMPERATURE, Thermostat.STANDBY_TEMPERATURE.toString());
            }
        }
    }
}
