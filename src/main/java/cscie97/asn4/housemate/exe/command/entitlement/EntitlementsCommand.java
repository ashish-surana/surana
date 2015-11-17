package cscie97.asn4.housemate.exe.command.entitlement;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn4.housemate.exe.command.Command;
import cscie97.asn4.housemate.exe.util.CommandParser;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidCommandException;

/**
 * This command should be executed to show entire inventory of all
 * entitlement resource inventory. These include Resource, Role, User etc.
 */
public class EntitlementsCommand extends Command {

    private static final String ENTITLEMENTS = "entitlements";
    private static final String INVENTORY = "inventory";

    public EntitlementsCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeShowCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntitlementServiceException {
        assert commandParser != null : "CommandParser cannot be null";

        //Input command format is:
        //show entitlements inventory
        commandParser.ensureNextToken(ENTITLEMENTS);
        commandParser.ensureNextToken(INVENTORY);
        entitlementService.showInventory();
    }
}
