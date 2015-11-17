package cscie97.asn4.housemate.controller.command;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn4.housemate.model.appliance.Ava;
import cscie97.asn4.housemate.model.appliance.Oven;
import cscie97.asn4.housemate.model.service.exception.EntityExistsException;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;

import java.util.Set;

/**
 * This command should be used when an Oven is turned on, and its time-to-cook is set to zero.
 */
public class OvenCookingCompletedCommand extends ControllerCommand {

    private final String roomId;

    private final String houseId;

    private final String deviceId;

    private final String deviceType;


    public OvenCookingCompletedCommand(AccessToken accessToken, String houseId, String roomId, String deviceType, String deviceId) {
        super(accessToken);
        
        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";
        assert deviceType!=null && !"".equals(deviceType) : "Device type cannot be null or empty string";
        assert deviceId!=null && !"".equals(deviceId) : "Device id cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
    }

    /**
     * If the oven is turned on, then this method uses Ava(s) of this room to inform the ocupant
     * that food is ready, and it turns off the oven.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        String powerStatus = modelService.getDeviceStatus(accessToken, houseId, roomId, deviceId, Oven.POWER);

        if(Oven.ON.equalsIgnoreCase(powerStatus)){
            modelService.setDeviceStatus(accessToken, houseId, roomId, deviceId, Oven.POWER, Oven.OFF);

            Set<String> avaIds = modelService.getDeviceIds(accessToken, houseId, roomId, Ava.DEVICE_TYPE);
            String avaFoodReadyMessage = "Food is ready in '" + deviceId + "'.";
            for (String avaId : avaIds) {
                modelService.setDeviceStatus(accessToken, houseId, roomId, avaId, Ava.SPEAKING, avaFoodReadyMessage);
            }
        }
    }
}
