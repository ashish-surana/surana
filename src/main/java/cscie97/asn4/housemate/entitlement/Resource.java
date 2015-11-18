package cscie97.asn4.housemate.entitlement;

import cscie97.asn4.housemate.entitlement.visitor.EntitlementVisitor;

/**
 * This class represents a resource in HouseMate entitlement service.
 */
public class Resource extends Entity{

    public static final String ALL_RESOURCE_ID = Resource.class.getName() + "_ALL_RESOURCE";

    private final String description;

    public Resource(String identifier, String description) {
        super(identifier);

        assert description != null && !"".equals(description) : "Resource description cannot be null or empty string";
        this.description = description;
    }

    /**
     * @return the description of this resource.
     */
    public String getDescription() {
        return description;
    }

    /**
     * A resource that can be used to denote all possible resources.
     */
    public static Resource ALL_RESOURCE = new Resource(ALL_RESOURCE_ID,
            "A resource that can be used to denote all possible resources.");

    @Override
    public void acceptVisitor(EntitlementVisitor visitor) {
        visitor.visitResource(this);
    }
}
