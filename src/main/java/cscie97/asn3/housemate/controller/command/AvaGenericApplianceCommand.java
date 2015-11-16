package cscie97.asn3.housemate.controller.command;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn3.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn3.housemate.model.appliance.Ava;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

import java.util.Set;

/**
 * This command should be executed when Ava is asked to set a generic appliance's status.
 */
public class AvaGenericApplianceCommand extends ControllerCommand {

    private final String houseId;

    private final String roomId;

    private final String statusValue;

    private final String avaId;

    private final String statusKey;

    private final String applianceType;

    public AvaGenericApplianceCommand(AccessToken accessToken, String avaId, String houseId, String roomId, String applianceType, String statusKey, String statusValue) {
        super(accessToken);

        assert avaId!=null && !"".equals(avaId) : "Ava id cannot be null or empty string";
        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";
        assert applianceType!=null && !"".equals(applianceType) : "Appliance type cannot be null or empty string";
        assert statusKey!=null && !"".equals(statusKey) : "Status key cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
        this.avaId = avaId;
        this.applianceType = applianceType;
        this.statusKey = statusKey;
        this.statusValue = statusValue;
    }

    /**
     * This method retrieves all the appliances of given type, and set given status on them.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        Set<String> applianceIds = modelService.getDeviceIds(accessToken, houseId, roomId, applianceType);

        for (String applianceId : applianceIds) {
            modelService.setDeviceStatus(accessToken, houseId, roomId, applianceId, statusKey, statusValue);

            String avaMessage = "Setting " + statusKey + " of " + applianceId + " to " + statusValue + ".";
            modelService.setDeviceStatus(accessToken, houseId, roomId, avaId, Ava.SPEAKING, avaMessage);
        }
    }
}
