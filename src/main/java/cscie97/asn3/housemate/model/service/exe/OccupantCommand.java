package cscie97.asn3.housemate.model.service.exe;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.support.OccupantType;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.model.service.exe.util.CommandParser;

/**
 *
 */
public class OccupantCommand extends Command {

    private static final String OCCUPANT = "occupant";
    private static final String TYPE = "type";
    private static final String TO_HOUSE = "to_house";

    public OccupantCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException {
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(OCCUPANT);
        String occupantId = commandParser.getNextToken("Occupant identifier");
        occupantId = occupantId.toLowerCase();

        commandParser.ensureNextToken(TYPE);

        String occupantTypeString = commandParser.getNextToken("Occupant type");
        OccupantType occupantType = OccupantType.getEnum(occupantTypeString);

        if(occupantType == null){
            throw new InvalidCommandException(commandParser.getInputCommand(),
                    "Unrecognized occupant type: '" + occupantTypeString + "'");
        }

        commandParser.ensureTermination();
        service.defineOccupant(accessToken, occupantId, occupantType);
    }

    @Override
    protected void executeAddCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntityExistsException {
        //occupant joe_smith to_house house1
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(OCCUPANT);
        String occupantId = commandParser.getNextToken("Occupant identifier");
        occupantId = occupantId.toLowerCase();

        commandParser.ensureNextToken(TO_HOUSE);
        String houseId = commandParser.getNextToken("House identifier");

        commandParser.ensureTermination();
        service.addOccupant(accessToken, occupantId, houseId);
    }
}
