package cscie97.asn4.knowledge.engine.domain;

/**
 * This class is used for representing Predicate in a Triple.
 */
public class Predicate {

    private final String identifier;

    /**
     * This constructor creates a Predicate using given identifier.
     * Identifiers are case-insensitive, and are always converted to lowercase.
     * @param identifier
     */
    public Predicate(String identifier){
        this.identifier = identifier.trim().toLowerCase();
    }

    /**
     * This method returns identifier of this predicate. Identifiers are case-insensitive, and are always converted to lowercase.
     * @return the identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

}
