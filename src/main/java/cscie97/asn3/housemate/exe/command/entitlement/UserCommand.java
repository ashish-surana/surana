package cscie97.asn3.housemate.exe.command.entitlement;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.exe.util.CommandParser;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;

/**
 * This command should be execute to add a user to entitlement service.
 */
public class UserCommand extends Command{

    private static final String USER = "user";

    public UserCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeCreateCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntityExistsException, EntitlementServiceException {
        //Input command format is:
        //create user debra "Debra Smart"
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(USER);
        String identifier = commandParser.getNextToken("User identifier");
        String name = commandParser.getNextTokenInDoubleQuotes("User name");
        commandParser.ensureTermination();

        entitlementService.createUser(identifier, name);

    }
}
