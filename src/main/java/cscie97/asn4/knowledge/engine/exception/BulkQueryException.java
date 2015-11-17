package cscie97.asn4.knowledge.engine.exception;

/**
 * This class represents an exception while processing bulk queries (file-based queries).
 * This exception class provides details about the offending query file path, and the offending line number.
 */
public class BulkQueryException extends QueryEngineException {

    private final String queryFilePath;

    private final int lineNum;

    /**
     * This constructor creates a new BulkQueryException using given parameters.
     * @param queryFilePath Path of the offending query file.
     * @param lineNum Offending line number in the query file.
     * @param query Offending query.
     * @param message Error message.
     */
    public BulkQueryException(String queryFilePath, int lineNum, String query, String message){
        super(query, message);
        this.queryFilePath = queryFilePath;
        this.lineNum = lineNum;
    }

    public BulkQueryException(String queryFilePath, String message) {
        super(null, message);
        this.queryFilePath = queryFilePath;
        this.lineNum = -1;
    }

    /**
     * @return the offending line number in the query file, or -1 if the error occurred while accessing query file.
     */
    public int getLineNum() {
        return lineNum;
    }

    /**
     * @return the offending query file path.
     */
    public String getQueryFilePath() {
        return queryFilePath;
    }
}
