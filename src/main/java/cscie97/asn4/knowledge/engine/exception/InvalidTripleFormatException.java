package cscie97.asn4.knowledge.engine.exception;

/**
 * This class represents an exception while importing an invalid triple.
 * Error message can be retrieved using {@link #getMessage()}.
 */
public class InvalidTripleFormatException extends ImportException {

    private final int lineNum;

    /**
     * This constructor creates a new InvalidTripleFormatException using given parameters.
     * @param tripleFilePath path of the offending triple file.
     * @param lineNum offending file number in triple file.
     * @param message error message.
     */
    public InvalidTripleFormatException(String tripleFilePath, int lineNum, String message) {
        super(tripleFilePath, message);
        this.lineNum = lineNum;
    }

    /**
     * @return the offending line number in the triple file.
     */
    public int getLineNum() {
        return lineNum;
    }

}
