package cscie97.asn3.housemate.controller.command;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn3.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn3.housemate.model.service.HouseMateModelService;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;
import cscie97.asn3.housemate.model.service.factory.HouseMateModelServiceFactory;

/**
 * This is a base class for all the commands that the HouseMateControllerServiceImpl
 * can execute in response to change in a device's status.
 */
public abstract class ControllerCommand {

    final AccessToken accessToken;

    public ControllerCommand(AccessToken accessToken){
        assert accessToken!=null : "Access token cannot be null";
        assert !accessToken.isExpired() : "Access token '"+ accessToken.getIdentifier() +"' has expired";

        this.accessToken = accessToken;
    }

    //This HouseMateModelService is used in the child concrete classes.
    HouseMateModelService modelService = new HouseMateModelServiceFactory().getService();

    /**
     * This method should be implemented by the child classes to execute the business logic in
     * response to a change in a device's status.
     * @throws EntityNotFoundException if a requested entity does not exists.
     * @throws InvalidStatusException if invalid status is being set on a device.
     * @throws EntityExistsException if an entity with the given id already exists.
     */
    public abstract void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException;
}
