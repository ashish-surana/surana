package cscie97.asn4.housemate.entitlement;

import cscie97.asn4.housemate.entitlement.visitor.EntitlementVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a role in the HouseMate automation system.
 */
public class Role extends Entitlement{

    private final String name;

    private final String description;

    private final Set<Entitlement> entitlements;

    public Role(String identifier, String name, String description) {
        super(identifier);

        assert name!= null && !"".equals(name) : "Permission name cannot be null or empty string";
        assert description!= null && !"".equals(description) : "Permission description cannot be null or empty string";

        this.description = description;
        this.name = name;
        this.entitlements = new HashSet<>();
    }

    /**
     * @return the name of this role.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description of this role.
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method adds the given entitlement (role or permission)
     * to this role.
     * @param entitlement given entitlement to add
     */
    public void addEntitlement(Entitlement entitlement){
        entitlements.add(entitlement);
    }

    /**
     * This method returns a modifiable set of all entitlements associated
     * with this role.
     */
    public Set<Entitlement> getEntitlements() {
        return entitlements;
    }

    /**
     * @return the unique identifier of this role.
     */
    @Override
    public String getIdentifier() {
        return super.getIdentifier();
    }

    @Override
    public void acceptVisitor(EntitlementVisitor visitor) {
        visitor.visitRole(this);
    }
}
