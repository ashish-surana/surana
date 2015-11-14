package cscie97.asn3.housemate.entitlement.exception;

/**
 * This exception is thrown when invalid user id or password is provided for authentication.
 */
public class AuthenticationException extends EntitlementServiceException {

    private String userId;

    public AuthenticationException(String userId, String message) {
        super(message);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
