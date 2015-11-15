package cscie97.asn3.housemate.exe.command.entitlement;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.exe.util.CommandParser;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;

/**
 * This command should be executed to create a resource role.
 */
public class ResourceRoleCommand extends Command {

    private static final String RESOURCE_ROLE = "resource_role";

    public ResourceRoleCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeCreateCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntityExistsException, EntitlementServiceException {
        //Input command format is:
        //create resource_role <resource_role_name> <role> <resource>
        //create resource_role house1_child_resident child_resident house1
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(RESOURCE_ROLE);
        String resourceRoleName = commandParser.getNextToken("Resource role identifier");
        String roleId = commandParser.getNextToken("Role identifier");
        String resourceId = commandParser.getNextToken("Resource identifier");
        commandParser.ensureTermination();

        entitlementService.createResourceRole(resourceRoleName, roleId, resourceId);

    }
}
