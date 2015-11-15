package cscie97.asn3.housemate.entitlement.factory.impl;

import cscie97.asn3.housemate.entitlement.*;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.EntityExistsException;
import cscie97.asn3.housemate.entitlement.exception.EntityNotFoundException;
import cscie97.asn3.housemate.entitlement.factory.EntitlementResourceFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * This factory provides methods for creating all resources managed by HouseMate entitlement
 * service.
 */
public class HouseMateEntitlementResourceFactory implements EntitlementResourceFactory{

    private final Map<String, User> users;

    private final Map<String, AccessToken> accessTokens;

    private final Map<String, Permission> permissions;

    private final AccessToken adminAccessToken;

    private final Map<String, Role> roles;

    public HouseMateEntitlementResourceFactory(){
        adminAccessToken = new AccessToken(HouseMateEntitlementResourceFactory.class.getName());

        users = new HashMap<>();
        accessTokens = new HashMap<>();
        permissions = new HashMap<>();
        roles = new HashMap<>();
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
    public AccessToken createAccessToken(String userId) {
        AccessToken accessToken = new AccessToken(userId);
        accessTokens.put(accessToken.getIdentifier(), accessToken);
        return accessToken;
    }

    @Override
    public AccessToken getAdminAccessToken() {
        return adminAccessToken;
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
        System.out.printf("Created permission with id: '%s', name: '%s', description: '%s' \n",
                identifier, name, description);
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
        System.out.printf("Created role with id: '%s', name: '%s', description: '%s' \n",
                identifier, name, description);
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

    public Permission getPermission(String identifier) throws EntityNotFoundException {
        Permission permission = permissions.get(identifier);

        if(permission == null){
            throw new EntityNotFoundException(identifier);
        }

        return permission;
    }


}
