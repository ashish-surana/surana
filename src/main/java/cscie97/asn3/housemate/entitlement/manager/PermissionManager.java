package cscie97.asn3.housemate.entitlement.manager;

import cscie97.asn3.housemate.entitlement.Permission;
import cscie97.asn3.housemate.entitlement.exception.EntityExistsException;


import java.util.HashMap;
import java.util.Map;

/**
 * This class manages instances of Permission for HouseMate automation system.
 */
public class PermissionManager {

    private static final Map<String, Permission> PERMISSIONS_CACHE = new HashMap<>();

    public static Permission getById(String identifier){
        return PERMISSIONS_CACHE.get(identifier);
    }

    public static Permission create(String identifier, String name, String description) throws EntityExistsException {
        Permission permission = getById(identifier);

        if (permission != null){
            throw new EntityExistsException(permission);
        }

        permission = new Permission(identifier, name, description);
        PERMISSIONS_CACHE.put(identifier, permission);
        return permission;
    }

}
