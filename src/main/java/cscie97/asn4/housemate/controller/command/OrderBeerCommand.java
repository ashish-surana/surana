package cscie97.asn4.housemate.controller.command;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn4.housemate.model.appliance.Ava;
import cscie97.asn4.housemate.model.service.exception.EntityExistsException;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;

import java.util.Set;

/**
 * This command should be used to order more beer, when a refrigerator's beer-count is less than four.
 */
public class OrderBeerCommand extends ControllerCommand {

    private final String refrigeratorId;

    private final String roomId;

    private final String houseId;

    public OrderBeerCommand(AccessToken accessToken, String refrigeratorId, String houseId, String roomId) {
        super(accessToken);
        
        assert refrigeratorId!=null && !"".equals(refrigeratorId) : "Refrigerator id cannot be null or empty string";
        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
        this.refrigeratorId = refrigeratorId;
    }

    /**
     * This command orders more beer for this refrigerator, and informs the occupants about it via
     * Ava(s) of the refrigerator's room.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        orderBeer();

        String avaMessage = "Ordered beer for " + refrigeratorId + ".";
        Set<String> avaIds = modelService.getDeviceIds(accessToken, houseId, roomId, Ava.DEVICE_TYPE);
        for (String avaId : avaIds) {
            modelService.setDeviceStatus(accessToken, houseId, roomId, avaId, Ava.SPEAKING, avaMessage);
        }
    }

    private void orderBeer() {
        //Ordering beer!
    }
}
