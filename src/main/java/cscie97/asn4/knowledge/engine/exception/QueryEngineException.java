package cscie97.asn4.knowledge.engine.exception;

/**
 * This class represents an exception that can be thrown during processing
 * either a single query or a file-based bulk query.
 */
public class QueryEngineException extends Exception {

    private final String query;

    public QueryEngineException(String query, String message){
        super(message);
        this.query = query;
    }

    /**
     * @return The query which caused this exception, or null if no query was executed.
     */
    public String getQuery() {
        return query;
    }

}
