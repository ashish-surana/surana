package cscie97.asn3.housemate.entitlement;

import cscie97.asn3.housemate.entitlement.visitor.EntitlementVisitor;
import cscie97.asn3.housemate.model.Entity;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a permission in the HouseMate automation system.
 */
public class Permission extends Entitlement{

    private final String name;

    private final String description;

    public Permission(String identifier, String name, String description) {
        super(identifier);

        assert name!= null && !"".equals(name) : "Permission name cannot be null or empty string";
        assert description!= null && !"".equals(description) : "Permission description cannot be null or empty string";

        this.description = description;
        this.name = name;
    }

    @Override
    public String getIdentifier() {
        return super.getIdentifier();
    }

    @Override
    public void acceptVisitor(EntitlementVisitor visitor) {
        visitor.visitPermission(this);
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
