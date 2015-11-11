package cscie97.asn3.housemate.model.service.exe;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.model.service.exe.util.CommandParser;

/**
 *
 */
public class HouseCommand extends Command {

    private static final String HOUSE = "house";

    public HouseCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException {
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(HOUSE);
        String houseId = commandParser.getNextToken("House identifier");

        commandParser.ensureTermination();

        service.defineHouse(null, houseId);
    }

}
