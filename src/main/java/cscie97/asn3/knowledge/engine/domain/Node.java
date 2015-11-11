package cscie97.asn3.knowledge.engine.domain;

/**
 * This class can be used to represent a Subject or an Object of a Triple.
 */
public class Node {

    private final String identifier;

    /**
     * This constructor creates a node for given identifier. It also trims white-space in the given
     * identifier, and converts it to lowercase.
     * @param identifier identifier for the new node.
     */
    public Node(String identifier){
        this.identifier = identifier.trim().toLowerCase();
    }

    /**
     * This method returns identifier of this node. Identifiers are case-insensitive, and are always converted to lowercase.
     * @return the identifier.
     */
    public String getIdentifier() {
        return identifier;
    }
}
