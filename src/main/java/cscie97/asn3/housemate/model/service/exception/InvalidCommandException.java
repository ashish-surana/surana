package cscie97.asn3.housemate.model.service.exception;

/**
 * This exception is thrown to indicate that an error occurred while
 * processing the given command. This exception class provides access
 * to the erroneous input command.
 */
public class InvalidCommandException extends HouseMateModelServiceException {

    private final String command;

    public InvalidCommandException(String command, String message) {
        super(message);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
