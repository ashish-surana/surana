package cscie97.asn4.housemate.entitlement.factory;

import cscie97.asn4.housemate.entitlement.*;
import cscie97.asn4.housemate.entitlement.exception.*;

import java.util.Set;

/**
 * This interface represents a factory for creating all "resources" required for Entitlement Service.
 * Some examples of these resources are users, roles and resources.
 */
public interface EntitlementResourceFactory {


    public void createUser(String userId, String userName) throws EntitlementServiceException;

    public User getUser(String userId) throws EntitlementServiceException;

    public AccessToken getOrCreateAccessToken(String userId) throws EntitlementServiceException;

    public void createPermission(String permissionId, String permissionName, String permissionDescription) throws EntityExistsException;

    public void createRole(String identifier, String name, String description) throws EntityExistsException;

    public Role getRole(String roleId) throws EntityNotFoundException;

    public Entitlement getEntitlement(String entitlementId) throws EntityNotFoundException;

    public ResourceRole createResourceRole(String resourceRoleName, Resource resource, Role role) throws EntityExistsException;

    public ResourceRole getResourceRole(String resourceRoleName) throws EntityNotFoundException;

    public void createResource(String resourceId, String resourceDescription) throws EntityExistsException;

    public Resource getResource(String resourceId) throws EntityNotFoundException;

    public User getUser(AccessToken accessToken) throws InvalidAccessTokenException, AccessDeniedException;

    public Set<String> getUserIds();
}
