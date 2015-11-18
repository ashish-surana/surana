package cscie97.asn4.housemate.entitlement.service;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.Credential;
import cscie97.asn4.housemate.entitlement.credential.PasswordCredential;
import cscie97.asn4.housemate.entitlement.credential.VoicePrintCredential;
import cscie97.asn4.housemate.entitlement.exception.*;

/**
 * This interface defines the methods for authenticating and authorizing users, creating resource, role and permissions
 * in HouseMate Automation System.
 */
public interface HouseMateEntitlementService {

    /**
     * This method accepts voiceCredential and logs in the user.
     * @param voiceCredential
     * @return
     * @throws EntitlementServiceException if voiceCredential is null
     * @throws EntityNotFoundException if no user is associated with given voiceCredential.
     * @throws AuthenticationException if given voiceCredential is not valid for associated user.
     */
    public AccessToken login(VoicePrintCredential voiceCredential) throws EntitlementServiceException, EntityNotFoundException, AuthenticationException;

    /**
     * This method accepts passwordCredential and logs in the user.
     * @param passwordCredential
     * @return
     * @throws EntitlementServiceException if passwordCredential is null
     * @throws EntityNotFoundException if no user is associated with given passwordCredential.
     * @throws AuthenticationException if given passwordCredential is not valid for associated user.
     */
    public AccessToken login(PasswordCredential passwordCredential) throws EntitlementServiceException, EntityNotFoundException, AuthenticationException;

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
     * This method adds given entitlement to the given role.
     * @param roleId
     * @param entitlementId
     * @throws EntitlementServiceException if given entitlement or role does not exists.
     */
    public void addEntitlementToRole(String roleId, String entitlementId) throws EntitlementServiceException;

    /**
     * This method creates a user using given parameters.
     * @param userId
     * @param userName
     * @throws EntitlementServiceException if a user with given id already exists.
     */
    public void createUser(String userId, String userName) throws EntitlementServiceException;

    /**
     * This method sets the given voiceCredential or passwordCredential for given user.
     * @param userId
     * @param credential
     * @throws EntitlementServiceException if given user does not exists, or credential is not valid.
     */
    public void setUserCredential(String userId, Credential credential) throws EntitlementServiceException;

    /**
     * This method adds the given role to given user.
     * @param userId
     * @param roleId
     * @throws EntitlementServiceException if given role or user do not exists.
     */
    public void addRoleToUser(String userId, String roleId) throws EntitlementServiceException;

    /**
     * This method creates a resource using given parameters.
     * @param resourceId
     * @param resourceDescription
     * @throws EntityExistsException if a resource with given id already exists.
     */
    public void createResource(String resourceId, String resourceDescription) throws EntityExistsException;

    /**
     * This method creates a resource role using given parameters.
     * @param resourceRoleName
     * @param resourceId
     * @param roleId
     * @return
     * @throws EntityExistsException if a resource role with given id already exists.
     */
    public void createResourceRole(String resourceRoleName, String roleId, String resourceId) throws EntityExistsException, EntityNotFoundException;

    /**
     * This method adds given resource role to given user.
     * @param userId
     * @param resourceRoleId
     * @throws EntitlementServiceException if the given resource role or user does not exists.
     */
    public void addResourceRoleToUser(String userId, String resourceRoleId) throws EntitlementServiceException;

    /**
     * This method checks if the user associated with given access token
     * has the claimed permissions for given resource id.
     * @param accessToken
     * @param resourceId
     * @param claimedPermissions
     * @throws InvalidAccessTokenException if access token is null, inactive or expired.
     * @throws AccessDeniedException if corresponding user does not have claimed permissions for given resource.
     */
    public void checkAccess(AccessToken accessToken, String resourceId, String... claimedPermissions) throws InvalidAccessTokenException, AccessDeniedException;

    /**
     * Prints out detailed inventory of all entitlement "resources" such as
     * user, their roles, resources etc.
     * @throws EntitlementServiceException if an error occurs in printing the inventory.
     */
    public void showInventory() throws EntitlementServiceException;
}
