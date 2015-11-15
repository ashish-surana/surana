package cscie97.asn3.housemate.exe.command.entitlement;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.exe.util.CommandParser;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.model.service.exception.InvalidEntityTypeException;

/**
 * This class is used to define role and add entitlements to a role.
 */
public class RoleCommand extends Command {

    private static final String ROLE = "role";
    private static final String ENTITLEMENT_TO_ROLE = "entitlement_to_role";

    public RoleCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException, EntityNotFoundException, InvalidEntityTypeException, cscie97.asn3.housemate.entitlement.exception.EntityExistsException {
        //Input command format is:
        //define role adult_resident "Adult Resident Role" "Has all permissions of an adult resident"
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(ROLE);
        String identifier = commandParser.getNextToken("Role identifier");
        String name = commandParser.getNextTokenInDoubleQuotes("Role name");
        String description = commandParser.getNextTokenInDoubleQuotes("Role description");
        commandParser.ensureTermination();

        entitlementService.createRole(identifier, name, description);
    }

    @Override
    protected void executeAddCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntityExistsException, EntitlementServiceException {
        //Input command format is:
        //# add entitlement_to_role <role_id> <entitlement_id>
        //add entitlement_to_role admin_role admin_permission
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(ENTITLEMENT_TO_ROLE);
        String roleId = commandParser.getNextToken("Role identifier");
        String entitlementId = commandParser.getNextToken("Entitlement identifier");
        commandParser.ensureTermination();

        entitlementService.addEntitlementToRole(roleId, entitlementId);
    }
}
