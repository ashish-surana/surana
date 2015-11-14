package cscie97.asn3.housemate.entitlement.factory;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.User;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.EntityExistsException;

/**
 * This interface represents a factory for creating all "resources" required for Entitlement Service.
 * Some examples of these resources are users, roles and resources.
 */
public interface EntitlementResourceFactory {


    public void createUser(String userId, String userName) throws EntitlementServiceException;

    public User getUser(String userId) throws EntitlementServiceException;

    public AccessToken createAccessToken(String userId);

    public AccessToken getAdminAccessToken();

    public void createPermission(String permissionId, String permissionName, String permissionDescription) throws EntityExistsException;
}