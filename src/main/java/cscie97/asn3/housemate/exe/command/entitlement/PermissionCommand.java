package cscie97.asn3.housemate.exe.command.entitlement;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.exe.util.CommandParser;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.model.service.exception.InvalidEntityTypeException;

/**
 * This class should be used to define a permission.
 */
public class PermissionCommand extends Command {

    private static final String PERMISSION = "permission";

    public PermissionCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException, EntityNotFoundException, InvalidEntityTypeException, cscie97.asn3.housemate.entitlement.exception.EntityExistsException {
        //Input command format is:
        //define permission control_oven "Control Oven" "Full Control of Oven"
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(PERMISSION);
        String permissionId = commandParser.getNextToken("Permission identifier");
        String permissionName = commandParser.getNextTokenInDoubleQuotes("Permission name");
        String permissionDescription = commandParser.getNextTokenInDoubleQuotes("Permission description");
        commandParser.ensureTermination();

        entitlementService.createPermission(permissionId, permissionName, permissionDescription);
    }
}
