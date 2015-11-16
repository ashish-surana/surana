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
 * This command should be executed when an occupant is detected by a camera.
 */
public class OccupantDetectedCommand extends ControllerCommand{

    private final String roomId;

    private final String houseId;

    private final String occupantId;

    public OccupantDetectedCommand(AccessToken accessToken, String houseId, String roomId, String occupantId) {
        super(accessToken);
      
        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";
        assert occupantId!=null && !"".equals(occupantId) : "Occupant id cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
        this.occupantId = occupantId.toLowerCase();
    }

    /**
     * This method invokes HouseMateModelService to turn on lights in the given room, and set all
     * the thermostat in this room to optimum temperature.
     * It also updates occupant's current location.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        Set<String> lightIds = modelService.getDeviceIds(accessToken, houseId, roomId, Light.DEVICE_TYPE);
        for (String lightId : lightIds){
            modelService.setDeviceStatus(accessToken, houseId, roomId, lightId, Light.MODE , Light.ON);
        }

        Set<String> thermostatIds = modelService.getDeviceIds(accessToken, houseId, roomId, Thermostat.DEVICE_TYPE);
        for (String thermostatId : thermostatIds){
            modelService.setDeviceStatus(accessToken, houseId, roomId, thermostatId, Thermostat.TEMPERATURE, Thermostat.OPTIMUM_TEMPERATURE.toString());
        }

        modelService.setOccupantLocation(accessToken, occupantId, houseId, roomId);
    }
}
