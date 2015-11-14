package cscie97.asn3.housemate.entitlement.factory.impl;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.Permission;
import cscie97.asn3.housemate.entitlement.User;
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

    public HouseMateEntitlementResourceFactory(){
        users = new HashMap<>();
        accessTokens = new HashMap<>();
        permissions = new HashMap<>();
        adminAccessToken = new AccessToken(HouseMateEntitlementResourceFactory.class.getName());
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

        if (permission != null){
            throw new EntityExistsException(permission);
        }

        permission = new Permission(identifier, name, description);
        permissions.put(identifier, permission);
        System.out.printf("Created permission with id: '%s' \n", identifier);
    }

    public Permission getPermission(String identifier) throws EntityNotFoundException {
        Permission permission = permissions.get(identifier);

        if(permission == null){
            throw new EntityNotFoundException(identifier);
        }

        return permission;
    }


}
