package cscie97.asn4.housemate.entitlement.exception;

/**
 * This exception is thrown to indicate that the requested entity with given
 * id does not exists in the HouseMate model service.
 */
public class EntityNotFoundException extends EntityException {

    public EntityNotFoundException(String entityIdentifier) {
        super("An entity with given identifier does not exists", entityIdentifier);
    }

}
