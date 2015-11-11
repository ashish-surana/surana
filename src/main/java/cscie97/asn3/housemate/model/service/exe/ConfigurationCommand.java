package cscie97.asn3.housemate.model.service.exe;


import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.model.service.exe.util.CommandParser;

/**
 *
 */
public class ConfigurationCommand extends Command {

    private static final String CONFIGURATION = "configuration";
    private static final String HOUSE = "house";
    private static final String ROOM = "room";

    public ConfigurationCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeShowCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException {
        commandParser.ensureNextToken(CONFIGURATION);

        String entityType =  commandParser.safeGetNextToken();

        if(entityType == null){
            service.showConfiguration(accessToken);
        }else if(HOUSE.equals(entityType)){
            String houseId = commandParser.getNextToken("House identifier");
            commandParser.ensureTermination();

            service.showHouseConfiguration(accessToken, houseId);
        }
        else if(ROOM.equals(entityType)){
            String houseAndRoomId = commandParser.getNextToken("House identifier: Room identifier");
            commandParser.ensureTermination();

            String[] identifiers = houseAndRoomId.split(":");

            if(identifiers.length != 2){
                throw new InvalidCommandException(commandParser.getInputCommand(),
                        "Illegal house and room identifiers: '"+ houseAndRoomId+ "'");
            }

            String houseId = identifiers[0];
            String roomId = identifiers[1];

            service.showRoomConfiguration(accessToken, houseId, roomId);
        }
    }
}
