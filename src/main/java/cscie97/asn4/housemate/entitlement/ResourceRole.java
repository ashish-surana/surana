package cscie97.asn4.housemate.entitlement;

import cscie97.asn4.housemate.entitlement.visitor.EntitlementVisitor;

/**
 * This class represents a resource role in the HouseMate entitlement service.
 */
public class ResourceRole extends Entity{

    private final Role role;

    private final Resource resource;
    
    public ResourceRole(String identifier, Role role, Resource resource) {
        super(identifier);

        assert role != null : "Role cannot be null";

        assert resource != null : "Resource cannot be null";

        this.resource = resource;
        this.role = role;
    }

    /**
     *
     * @return the resource associated with this resource-role.
     */
    public Resource getResource() {
        return resource;
    }

    /**
     *
     * @return the role associated with this resource-role.
     */
    public Role getRole() {
        return role;
    }

    @Override
    public void acceptVisitor(EntitlementVisitor visitor) {
        visitor.visitResourceRole(this);
    }
}
