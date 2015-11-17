package cscie97.asn4.housemate.controller.command;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn4.housemate.model.service.exception.EntityExistsException;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;
import cscie97.asn4.housemate.model.support.OccupantActivityStatus;

/**
 * This command should be used when an occupant's status is detected as 'active'.
 */
public class OccupantActiveCommand extends ControllerCommand {

    private final String roomId;

    private final String houseId;

    private final String occupantId;

    public OccupantActiveCommand(AccessToken accessToken, String houseId, String roomId, String occupantId) {
        super(accessToken);

        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";
        assert occupantId!=null && !"".equals(occupantId) : "Occupant id cannot be null or empty string";
        
        this.houseId = houseId;
        this.roomId = roomId;
        this.occupantId = occupantId.toLowerCase();
    }

    /**
     * This method invokes HouseMateModelService to update occupant's status as active.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        modelService.setOccupantLocation(accessToken, occupantId, houseId, roomId);
        modelService.setOccupantActivityStatus(accessToken, occupantId, OccupantActivityStatus.Active);
    }
}
