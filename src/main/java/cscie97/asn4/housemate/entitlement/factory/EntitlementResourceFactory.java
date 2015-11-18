package cscie97.asn4.housemate.entitlement.factory;

import cscie97.asn4.housemate.entitlement.*;
import cscie97.asn4.housemate.entitlement.exception.*;

import java.util.Set;

/**
 * This interface represents an abstract factory for creating all "resources" required for Entitlement Service.
 * Some examples of these resources are users, roles and resources.
 */
public interface EntitlementResourceFactory {

    /**
     * This method creates a user using given parameters.
     * @param userId
     * @param userName
     * @throws EntitlementServiceException if a user with given id already exists.
     */
    public void createUser(String userId, String userName) throws EntitlementServiceException;

    /**
     * Returns the user with given id.
     * @param userId
     * @return
     * @throws EntitlementServiceException if a user with given id is not found.
     */
    public User getUser(String userId) throws EntitlementServiceException;

    /**
     * This method creates/ returns an active access token for given user id.
     * @param userId
     * @return
     * @throws EntitlementServiceException if given user is not valid.
     */
    public AccessToken getOrCreateAccessToken(String userId) throws EntitlementServiceException;


    /**
     * This method creates a permission using given parameters.
     * @param permissionId
     * @param permissionName
     * @param permissionDescription
     * @throws EntityExistsException if a permission with given id already exists.
     */
    public void createPermission(String permissionId, String permissionName, String permissionDescription) throws EntityExistsException;

    /**
     * This method creates a role using given parameters.
     * @param identifier
     * @param name
     * @param description
     * @throws EntityExistsException if a role with given id already exists.
     */
    public void createRole(String identifier, String name, String description) throws EntityExistsException;

    /**
     * Returns role with given id.
     * @param roleId
     * @return
     * @throws EntityNotFoundException if no role is found for given id.
     */
    public Role getRole(String roleId) throws EntityNotFoundException;

    /**
     * Returns a role or permission associated with given id.
     * @param entitlementId
     * @return
     * @throws EntityNotFoundException if no role or permission is found for given id.
     */
    public Entitlement getEntitlement(String entitlementId) throws EntityNotFoundException;

    /**
     * This method creates a resource role using given parameters.
     * @param resourceRoleName
     * @param resource
     * @param role
     * @return
     * @throws EntityExistsException if a resource role with given id already exists.
     */
    public ResourceRole createResourceRole(String resourceRoleName, Resource resource, Role role) throws EntityExistsException;

    /**
     * Returns the resource role for given name.
     * @param resourceRoleName
     * @return
     * @throws EntityNotFoundException if no resource role exists with given name.
     */
    public ResourceRole getResourceRole(String resourceRoleName) throws EntityNotFoundException;

    /**
     * This method creates a resource using given parameters.
     * @param resourceId
     * @param resourceDescription
     * @throws EntityExistsException if a resource with given id already exists.
     */
    public void createResource(String resourceId, String resourceDescription) throws EntityExistsException;

    /**
     * Returns a resource with given id.
     * @param resourceId
     * @return
     * @throws EntityNotFoundException if no resource exists with given id.
     */
    public Resource getResource(String resourceId) throws EntityNotFoundException;

    /**
     * Returns the user associated with given access token.
     * @param accessToken
     * @return
     * @throws InvalidAccessTokenException if accessToken is null, expired or unrecognized.
     * @throws AccessDeniedException
     */
    public User getUser(AccessToken accessToken) throws InvalidAccessTokenException;

    /**
     * This method returns a unmodifiable set of user ids for all existing users.
     * @return
     */
    public Set<String> getUserIds();
}
