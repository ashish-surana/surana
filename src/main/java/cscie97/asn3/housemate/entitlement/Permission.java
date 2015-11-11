package cscie97.asn3.housemate.entitlement;

import cscie97.asn3.housemate.model.Entity;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by assurana on 11/3/2015.
 */
public class Permission extends Entitlement{

    private final String name;

    private final String description;

    private static final Map<String, Permission> PERMISSIONS_CACHE = new HashMap<>();

    private Permission(String identifier, String name, String description) {
        super(identifier);

        assert name!= null && !"".equals(name) : "Permission name cannot be null or empty string";
        assert description!= null && !"".equals(description) : "Permission description cannot be null or empty string";

        this.description = description;
        this.name = name;

        PERMISSIONS_CACHE.put(identifier, this);
    }

    public static Permission getById(String identifier){
        return PERMISSIONS_CACHE.get(identifier);
    }

    public static Permission create(String identifier, String name, String description) throws EntityExistsException {
        Permission permission = getById(identifier);

        if (permission != null){
            throw new EntityExistsException(permission);
        }

        permission = new Permission(identifier, name, description);
        return permission;
    }

    @Override
    public String getIdentifier() {
        return super.getIdentifier();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
