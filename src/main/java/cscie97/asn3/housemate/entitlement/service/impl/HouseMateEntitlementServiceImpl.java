package cscie97.asn3.housemate.entitlement.service.impl;

import cscie97.asn3.housemate.entitlement.*;
import cscie97.asn3.housemate.entitlement.credential.PasswordCredential;
import cscie97.asn3.housemate.entitlement.credential.VoicePrintCredential;
import cscie97.asn3.housemate.entitlement.exception.AuthenticationException;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.EntityExistsException;
import cscie97.asn3.housemate.entitlement.exception.EntityNotFoundException;
import cscie97.asn3.housemate.entitlement.factory.EntitlementResourceFactory;
import cscie97.asn3.housemate.entitlement.factory.impl.HouseMateEntitlementResourceFactory;
import cscie97.asn3.housemate.entitlement.service.HouseMateEntitlementService;

/**
 *
 */
public class HouseMateEntitlementServiceImpl implements HouseMateEntitlementService{

    private static final HouseMateEntitlementService INSTANCE = new HouseMateEntitlementServiceImpl();

    private final EntitlementResourceFactory resourceFactory = new HouseMateEntitlementResourceFactory();

    private HouseMateEntitlementServiceImpl(){
    }

    public static HouseMateEntitlementService getInstance() {
        return INSTANCE;
    }

    public void createUser(String userId, String userName) throws EntitlementServiceException, EntityExistsException {
        resourceFactory.createUser(userId, userName);
        System.out.printf("Created user with id: '%s' and name: '%s'.\n", userId, userName);
    }

    @Override
    public void setUserCredential(String userId, Credential credential) throws EntitlementServiceException {
        assert credential != null : "Credential cannot be null";

        User user = resourceFactory.getUser(userId);

        if(credential instanceof VoicePrintCredential){
            user.setVoicePrint(credential);
            System.out.printf("New voice print for user id: '%s' has been set.\n", userId);
        }else if(credential instanceof PasswordCredential){
            user.setPassword(credential);
            System.out.printf("New password for user id: '%s' has been set.\n", userId);
        }else {
            throw new EntitlementServiceException("Unrecognized credential type: '"+ credential.getClass().getName()+
                    "' for user id: '" + userId + "'");
        }
    }

    @Override
    public void addRoleToUser(String userId, String roleId) throws EntitlementServiceException {
        User user = resourceFactory.getUser(userId);
        Role role = resourceFactory.getRole(roleId);
        Resource resource = Resource.ALL_RESOURCE;

        String resourceRoleName = resource.getIdentifier() + ":" + roleId;

        ResourceRole resourceRole;
        try{
            resourceRole = resourceFactory.getResourceRole(resourceRoleName);
        }catch (EntityNotFoundException e){
            resourceRole = resourceFactory.createResourceRole(resourceRoleName, resource, role);
        }

        user.addResourceRole(resourceRole);
        System.out.printf("Added role '%s' to user id: '%s'.\n", roleId, userId);
    }

    @Override
    public void createResource(String resourceId, String resourceDescription) throws EntityExistsException {
        resourceFactory.createResource(resourceId, resourceDescription);
        System.out.printf("Created resource with id: '%s' and description: '%s'.\n",
                resourceId, resourceDescription);
    }

    @Override
    public void createResourceRole(String resourceRoleName, String roleId, String resourceId) throws EntityExistsException, EntityNotFoundException {
        Role role = resourceFactory.getRole(roleId);
        Resource resource = resourceFactory.getResource(resourceId);

        resourceFactory.createResourceRole(resourceRoleName, resource, role);
        System.out.printf("Created resource role with name: '%s', role id: '%s', and resource id: '%s'. \n",
                resourceRoleName, roleId, resourceId);
    }

    @Override
    public void addResourceRoleToUser(String userId, String resourceRoleId) throws EntitlementServiceException {
        User user = resourceFactory.getUser(userId);
        ResourceRole resourceRole = resourceFactory.getResourceRole(resourceRoleId);

        user.addResourceRole(resourceRole);
        System.out.printf("Added resource role '%s' to user id: '%s'.\n", resourceRoleId, userId);
    }

    public AccessToken login(VoicePrintCredential proposedCredential) throws EntitlementServiceException, EntityNotFoundException, AuthenticationException {
        if(proposedCredential == null){
            throw new EntitlementServiceException("VoicePrintCredential cannot be null");
        }

        String userId = proposedCredential.getUserId();
        User user = resourceFactory.getUser(userId);

        if(user.getVoicePrint() == null){
            throw new EntityNotFoundException("Voice print for "+ userId);
        }

        if(!user.getVoicePrint().equals(proposedCredential)){
            throw new AuthenticationException(userId, "Invalid user id or voice print");
        }

        AccessToken accessToken = resourceFactory.getOrCreateAccessToken(userId);
        return accessToken;
    }

    @Override
    public AccessToken login(PasswordCredential proposedCredential) throws EntitlementServiceException, EntityNotFoundException, AuthenticationException {
        if(proposedCredential == null){
            throw new EntitlementServiceException("PasswordCredential cannot be null");
        }

        String userId = proposedCredential.getUserId();
        User user = resourceFactory.getUser(userId);

        if(user.getPassword() == null){
            throw new EntityNotFoundException("Password for "+ userId);
        }
        //if hashed passwords are not equals, then throw an exception.
        if(!user.getPassword().equals(proposedCredential)){
            throw new AuthenticationException(userId, "Invalid user id or password");
        }

        AccessToken accessToken = resourceFactory.getOrCreateAccessToken(userId);
        return accessToken;
    }

    @Override
    public void createPermission(String permissionId, String permissionName, String permissionDescription) throws EntityExistsException {
        resourceFactory.createPermission(permissionId, permissionName, permissionDescription);
        System.out.printf("Created permission with id: '%s', name: '%s', description: '%s' \n",
                permissionId, permissionName, permissionDescription);
    }

    @Override
    public void createRole(String identifier, String name, String description) throws EntityExistsException {
        resourceFactory.createRole(identifier, name, description);
        System.out.printf("Created role with id: '%s', name: '%s', description: '%s' \n",
                identifier, name, description);
    }

    @Override
    public void addEntitlementToRole(String roleId, String entitlementId) throws EntitlementServiceException {
        Role role = resourceFactory.getRole(roleId);
        Entitlement entitlement = resourceFactory.getEntitlement(entitlementId);

        if(roleId.equals(entitlementId)){
            throw new EntitlementServiceException("Cannot add a role to itself. Offending role is: '" + roleId + "'");
        }

        role.addEntitlement(entitlement);
        System.out.printf("Added entitlement: '%s' to role '%s' \n", entitlementId, roleId);
    }
}
