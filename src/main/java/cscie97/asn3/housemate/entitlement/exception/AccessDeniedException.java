package cscie97.asn3.housemate.entitlement.exception;

import java.util.Set;

/**
 * This exception should be thrown to indicate that a user does not have sufficient permissions to access a resource.
 */
public class AccessDeniedException extends EntitlementServiceException {

    private final Set<String> unverifiedPermissions;
    private final String userId;
    private final String resourceId;

    public AccessDeniedException(String userId, String resourceId, Set<String> unverifiedPermissions) {
        super("Access denied to user id: '"+ userId + "' for resource: '" + resourceId +
                "', missing permissions: "+ unverifiedPermissions);
        this.resourceId = resourceId;
        this.unverifiedPermissions = unverifiedPermissions;
        this.userId = userId;
    }

    @Override
    public String getMessage() {

        return "Access denied to user id: '"+ userId + "' for resource: '" + resourceId +
                "', missing permissions: "+ unverifiedPermissions ;
    }

    public String getResourceId() {
        return resourceId;
    }

    public Set<String> getUnverifiedPermissions() {
        return unverifiedPermissions;
    }

    public String getUserId() {
        return userId;
    }
}
