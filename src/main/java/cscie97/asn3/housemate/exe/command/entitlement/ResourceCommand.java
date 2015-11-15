package cscie97.asn3.housemate.exe.command.entitlement;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.exe.util.CommandParser;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.model.service.exception.InvalidEntityTypeException;

/**
 * This command should be executed to create a new resource using entitlement service.
 */
public class ResourceCommand extends Command {

    private static final String RESOURCE = "resource";

    public ResourceCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException, EntityNotFoundException, InvalidEntityTypeException, cscie97.asn3.housemate.entitlement.exception.EntityExistsException {
        //Input command format is:
        //define resource house1 "Represents house1 and all resources within house1"
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(RESOURCE);
        String resourceId = commandParser.getNextToken("Resource identifier");
        String resourceDescription = commandParser.getNextTokenInDoubleQuotes("Resource description");
        commandParser.ensureTermination();

        entitlementService.createResource(resourceId, resourceDescription);
    }
}
