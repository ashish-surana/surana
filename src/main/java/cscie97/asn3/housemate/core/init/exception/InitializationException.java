package cscie97.asn3.housemate.core.init.exception;

/**
 * This exception is thrown when HouseMate automation system cannot be initialized.
 */
public class InitializationException extends Exception{

    public InitializationException(String message){
        super(message);
    }

    public InitializationException(Exception originalException){
        super(originalException);
    }
}
