package cscie97.asn3.housemate.model.service.exception;

/**
 * This is a base class for all the exceptions that can be thrown while reading,
 * writing, or editing an {@link cscie97.asn3.housemate.model.Entity}.
 */
public class EntityException extends HouseMateModelServiceException{

    private final Object entity;

    public EntityException(String message, Object entity){
        super(message);
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }
}
