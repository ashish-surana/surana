package cscie97.asn4.housemate.entitlement;



/**
 * This abstract class represents an entitlement in HouseMate automation system.
 * An entitlement can be of type Role or Permission.
 */
public abstract class Entitlement extends Entity{

    public Entitlement(String identifier) {
        super(identifier);
    }
}
