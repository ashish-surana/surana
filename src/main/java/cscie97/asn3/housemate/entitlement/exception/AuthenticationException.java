package cscie97.asn3.housemate.entitlement.exception;

/**
 * This exception is thrown when invalid user id or password is provided for authentication.
 */
public class AuthenticationException extends EntitlementServiceException {

    public AuthenticationException(Exception exception) {
        super(exception);
    }

    public AuthenticationException(String userId, String message) {
        super(message);
    }
}
