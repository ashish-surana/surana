package cscie97.asn4.housemate.model.service.exception;

/**
 * This exception should be thrown to indicate that a given entity type is unrecognized.
 */
public class InvalidEntityTypeException extends EntityException{

    /**
     * @param message Error message.
     * @param entityType The unrecognized entity type.
     */
    public InvalidEntityTypeException(String message, String entityType) {
        super(message, entityType);
    }
}
