package cscie97.asn4.housemate.entitlement;

import cscie97.asn4.housemate.entitlement.visitor.EntitlementVisitor;

/**
 * This is a base class for all domain classes in the HouseMate entitlement service.
 */
public abstract class Entity {

    private final String identifier;

    public Entity(String identifier){
        assert identifier!= null && !"".equals(identifier) : "Entity identifier cannot be null or empty string";

        this.identifier = identifier;
    }

    /**
     * Returns the unique identifier of this entity.
     */
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }

    /**
     * This method accepts the given visitor, thereby allowing the visitor to
     * traverse the domain object.
     * @param visitor
     */
    public abstract void acceptVisitor(EntitlementVisitor visitor);
}
