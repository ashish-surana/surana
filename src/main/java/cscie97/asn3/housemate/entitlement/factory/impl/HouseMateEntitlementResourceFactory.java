package cscie97.asn3.housemate.entitlement.factory.impl;

import cscie97.asn3.housemate.entitlement.*;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.EntityExistsException;
import cscie97.asn3.housemate.entitlement.exception.EntityNotFoundException;
import cscie97.asn3.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn3.housemate.entitlement.factory.EntitlementResourceFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This factory provides methods for creating all resources managed by HouseMate entitlement
 * service.
 */
public class HouseMateEntitlementResourceFactory implements EntitlementResourceFactory{

    private final Map<String, User> users;

    private final Map<String, AccessToken> accessTokens;

    private final Map<String, Permission> permissions;

    private final Map<String, Role> roles;

    private final Map<String, ResourceRole> resourceRoles;

    private final Map<String, Resource> resources;

    public HouseMateEntitlementResourceFactory(){
        users = new HashMap<>();
        accessTokens = new HashMap<>();
        permissions = new HashMap<>();
        roles = new HashMap<>();
        resourceRoles = new HashMap<>();
        resources = new HashMap<>();
    }

    @Override
    public void createUser(String userId, String userName) throws EntityExistsException, EntitlementServiceException {
        if(userId == null || "".equals(userId)){
            throw new EntitlementServiceException("User identifier cannot be null or empty string");
        }

        if(userName == null || "".equals(userName)){
            throw new EntitlementServiceException("User name cannot be null or empty string");
        }

        User user = users.get(userId);

        if(user != null){
            throw new EntityExistsException(user);
        }

        user = new User(userId, userName);
        users.put(userId, user);
    }

    @Override
    public User getUser(String userId) throws EntitlementServiceException {
        if(userId == null || "".equals(userId)){
            throw new EntitlementServiceException("User identifier cannot be null or empty string");
        }

        User user = users.get(userId);

        if(user == null){
            throw new EntityNotFoundException(userId);
        }

        return user;
    }

    @Override
    public AccessToken getOrCreateAccessToken(String userId) throws EntitlementServiceException {
        User user = getUser(userId);

        AccessToken accessToken = user.getAccessToken();

        if (accessToken == null) {
            accessToken = new AccessToken(userId);
            user.setAccessToken(accessToken);
            accessTokens.put(accessToken.getIdentifier(), accessToken);
        }

        return accessToken;
    }

    @Override
    public void createPermission(String identifier, String name, String description) throws EntityExistsException {
        Permission permission = permissions.get(identifier);
        Role role = roles.get(identifier);

        if (permission != null || role != null){
            throw new EntityExistsException(permission);
        }

        permission = new Permission(identifier, name, description);
        permissions.put(identifier, permission);
    }

    @Override
    public void createRole(String identifier, String name, String description) throws EntityExistsException {
        Role role = roles.get(identifier);
        Permission permission = permissions.get(identifier);
        if(role != null || permission != null){
            throw new EntityExistsException(role);
        }

        role = new Role(identifier, name, description);
        roles.put(identifier, role);
    }

    @Override
    public Role getRole(String roleId) throws EntityNotFoundException {
        Role role = roles.get(roleId);

        if(role == null){
            throw new EntityNotFoundException(roleId);
        }

        return role;
    }

    @Override
    public Entitlement getEntitlement(String entitlementId) throws EntityNotFoundException {
        try{
            //first look up the entitlement as a role, then as a permission.
            //throw an exception, if it is neither a role, nor a permission.
            Entitlement entitlement = getRole(entitlementId);
            return entitlement;
        } catch (EntityNotFoundException e) {
            Entitlement entitlement = getPermission(entitlementId);
            return entitlement;
        }
    }

    @Override
    public ResourceRole createResourceRole(String resourceRoleName, Resource resource, Role role) throws EntityExistsException {
        assert resourceRoleName != null && !"".equals(resourceRoleName) : "Resouce role name cannot be null or empty string";
        assert resource != null : "Resource cannot be null";
        assert role != null : "Role cannot be null";

        ResourceRole resourceRole = resourceRoles.get(resourceRoleName);

        if(resourceRole != null){
            throw new EntityExistsException(resourceRole);
        }

        resourceRole = new ResourceRole(resourceRoleName, role, resource);
        resourceRoles.put(resourceRoleName, resourceRole);

        return resourceRole;
    }

    @Override
    public ResourceRole getResourceRole(String resourceRoleName) throws EntityNotFoundException {
        assert resourceRoleName != null && !"".equals(resourceRoleName) : "Resource role name cannot be null or empty string";

        ResourceRole resourceRole = resourceRoles.get(resourceRoleName);
        if(resourceRole == null){
            throw new EntityNotFoundException(resourceRoleName);
        }

        return resourceRole;
    }

    @Override
    public void createResource(String resourceId, String resourceDescription) throws EntityExistsException {
        Resource resource = resources.get(resourceId);

        if(resource != null){
            throw new EntityExistsException(resource);
        }

        resource = new Resource(resourceId, resourceDescription);
        resources.put(resourceId, resource);
    }

    @Override
    public Resource getResource(String resourceId) throws EntityNotFoundException {
        assert resourceId != null && !"".equals(resourceId) : "Resource identifier cannot be null or empty string";

        Resource resource = resources.get(resourceId);

        if (resource == null) {
            throw new EntityNotFoundException(resourceId);
        }

        return resource;
    }

    @Override
    public User getUser(AccessToken accessToken) throws InvalidAccessTokenException {
        if (accessToken == null) {
            throw new InvalidAccessTokenException(accessToken, "AccessToken cannot be null");
        }

        if(accessToken.isExpired()){
            throw new InvalidAccessTokenException(accessToken, "AccessToken is expired");
        }

        if(!accessTokens.containsKey(accessToken.getIdentifier())){
            throw new InvalidAccessTokenException(accessToken, "Unrecognized AccessToken");
        }

        String userId = accessToken.getUserId();
        User user = users.get(userId);

        if(user == null){
            throw new InvalidAccessTokenException(accessToken, "AccessToken refers to invalid user id");
        }

        return user;
    }

    @Override
    public Set<String> getUserIds() {
        return Collections.unmodifiableSet(users.keySet());
    }

    public Permission getPermission(String identifier) throws EntityNotFoundException {
        Permission permission = permissions.get(identifier);

        if(permission == null){
            throw new EntityNotFoundException(identifier);
        }

        return permission;
    }


}
