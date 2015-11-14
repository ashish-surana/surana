package cscie97.asn3.housemate.exe.command.model;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.service.HouseMateModelService;
import cscie97.asn3.housemate.model.service.exception.*;
import cscie97.asn3.housemate.exe.util.CommandParser;
import cscie97.asn3.housemate.model.service.factory.HouseMateModelServiceFactory;

/**
 * A base class for all the commands that can be invoked using HouseMateModelServiceExecutor.
 * The default implementation provided by this class simply throws an InvalidCommandException.
 * Concrete subclasses of this class are expected to override appropriate methods to execute a
 * command.
 */
public abstract class Command {

    HouseMateModelService service = new HouseMateModelServiceFactory().getService();

    final AccessToken accessToken;

    public Command(AccessToken accessToken){
        assert accessToken != null : "Access token cannot be null";
        assert !accessToken.isExpired() : "Access token '"+ accessToken.getIdentifier() +"' has expired";

        this.accessToken = accessToken;
    }

    public void execute(String inputCommand) throws InvalidCommandException, EntityException {
        assert inputCommand!= null && !"".equals(inputCommand) :
                "Input command cannot be null or empty string";

        CommandParser commandParser = new CommandParser(inputCommand);

        String action = commandParser.getNextToken("Command");

        switch (action){
            case "define":
                executeDefineCommand(commandParser);
                break;
            case "show":
                executeShowCommand(commandParser);
                break;
            case "add":
                executeAddCommand(commandParser);
                break;
            case "set":
                executeSetCommand(commandParser);
                break;
            default:
                throw new InvalidCommandException(inputCommand, "Unrecognized command");

        }

    }

    protected void executeSetCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, InvalidStatusException {
        throw new InvalidCommandException(commandParser.getInputCommand(), "Unsupported command");
    }

    protected void executeAddCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntityExistsException {
        throw new InvalidCommandException(commandParser.getInputCommand(), "Unsupported command");
    }

    protected void executeShowCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException {
        throw new InvalidCommandException(commandParser.getInputCommand(), "Unsupported command");
    }

    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException, EntityNotFoundException, InvalidEntityTypeException {
        throw new InvalidCommandException(commandParser.getInputCommand(), "Unsupported command");
    }

}
