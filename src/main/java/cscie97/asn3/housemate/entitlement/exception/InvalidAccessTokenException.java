package cscie97.asn3.housemate.entitlement.exception;

import cscie97.asn3.housemate.entitlement.AccessToken;

/**
 * This exception should be thrown when a given AccessToken is null, expired or unrecognized in HouseMate automation system.
 */
public class InvalidAccessTokenException extends EntitlementServiceException {

    private final AccessToken accessToken;

    public InvalidAccessTokenException(AccessToken accessToken, String message) {
        super(message);
        this.accessToken = accessToken;
    }
}
