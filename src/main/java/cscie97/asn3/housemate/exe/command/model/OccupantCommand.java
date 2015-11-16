package cscie97.asn3.housemate.exe.command.model;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.model.support.OccupantType;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.exe.util.CommandParser;

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
    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException, EntitlementServiceException {
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
        modelService.defineOccupant(accessToken, occupantId, occupantType);
    }

    @Override
    protected void executeAddCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        //occupant joe_smith to_house house1
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(OCCUPANT);
        String occupantId = commandParser.getNextToken("Occupant identifier");
        occupantId = occupantId.toLowerCase();

        commandParser.ensureNextToken(TO_HOUSE);
        String houseId = commandParser.getNextToken("House identifier");

        commandParser.ensureTermination();
        modelService.addOccupant(accessToken, occupantId, houseId);
    }
}
