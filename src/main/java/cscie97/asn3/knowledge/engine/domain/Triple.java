package cscie97.asn3.knowledge.engine.domain;

/**
 * This class represents a Triple consisting of subject, predicate and object.
 */
public class Triple {

    private final Node subject;

    private final Predicate predicate;

    private final Node object;

    private final String identifier;

    /**
     * This constructor creates a triple by using given subject, predicate and object.
     * It generates an identifier for this new triple using  {@link #getIdentifier()} method.
     * @param subject subject of this triple. Cannot be {@code null}.
     * @param predicate predicate of this triple. Cannot be {@code null}.
     * @param object object of this triple. Cannot be {@code null}.
     */
    public Triple(Node subject, Predicate predicate, Node object){
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
        this.identifier = getIdentifier(subject, predicate, object);
    }

    /**
     * This method returns an identifier for a triple of given subject, predicate and object.
     * The returned identifier is of form {@code 'subject-id predicate-id object-id},
     * where subject-id, predicate-id, and object-id are identifiers of given subject, predicate
     * and object respectively.
     * @param subject subject of this triple. Cannot be {@code null}.
     * @param predicate predicate of this triple. Cannot be {@code null}.
     * @param object object of this triple. Cannot be {@code null}.
     * @return identifier of a triple of given subject, predicate and object.
     */
    public static String getIdentifier(Node subject, Predicate predicate, Node object){
        StringBuilder tripleIdentifierBuilder = new StringBuilder().append(subject.getIdentifier());
        tripleIdentifierBuilder.append(" ");
        tripleIdentifierBuilder.append(predicate.getIdentifier());
        tripleIdentifierBuilder.append(" ");
        tripleIdentifierBuilder.append(object.getIdentifier());
        return tripleIdentifierBuilder.toString();
    }

    /**
     * This method returns identifier of this triple.
     * Identifier follow the format specified in {@link #getIdentifier()}.
     * @return the identifier of this triple.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @return Object of this triple.
     */
    public Node getObject() {
        return object;
    }

    /**
     * @return Subject of this triple.
     */
    public Node getSubject() {
        return subject;
    }

    /**
     * @return Prediate of this triple.
     */
    public Predicate getPredicate() {
        return predicate;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
