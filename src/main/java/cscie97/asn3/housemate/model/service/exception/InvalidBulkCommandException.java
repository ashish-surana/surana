package cscie97.asn3.housemate.model.service.exception;

/**
 * This exception is thrown while processing commands from an input file.
 * This exception provides information about the file path, line number,
 * and actual command that was being executed when this exception occurred.
 */
public class InvalidBulkCommandException extends InvalidCommandException {

    private final int lineNum;

    private final String fileName;

    public InvalidBulkCommandException(String fileName, int lineNum, String command, String message) {
        super(command, message);
        this.fileName = fileName;
        this.lineNum = lineNum;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNum() {
        return lineNum;
    }
}
