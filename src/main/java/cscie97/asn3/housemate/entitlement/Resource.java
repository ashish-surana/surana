package cscie97.asn3.housemate.entitlement;

/**
 * This class represents a resource in HouseMate entitlement service.
 */
public class Resource extends Entity{

    private static final String ALL_RESOURCE_ID = Resource.class.getName() + "_ALL_RESOURCE";

    public Resource(String identifier) {
        super(identifier);
    }

    /**
     * A resource that can be used to denote all possible resources.
     */
    public static Resource ALL_RESOURCE = new Resource(ALL_RESOURCE_ID);
}
