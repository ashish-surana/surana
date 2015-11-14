package cscie97.asn3.housemate.entitlement.exception;

/**
 * This is the base class for all the exceptions that can be thrown by Entitlement module
 * of HouseMate automation system.
 */
public class EntitlementServiceException extends Exception{

    public EntitlementServiceException(Exception exception){
        super(exception);
    }

    public EntitlementServiceException(String message) {
        super(message);
    }
}
