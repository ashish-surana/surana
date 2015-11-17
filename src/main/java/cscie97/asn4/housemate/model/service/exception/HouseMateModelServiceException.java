package cscie97.asn4.housemate.model.service.exception;

/**
 * This is base exception class for any possible exception in the HouseMate model service.
 */
public class HouseMateModelServiceException extends Exception{

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public HouseMateModelServiceException(String message) {
        super(message);
    }
}
