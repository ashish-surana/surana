package cscie97.asn4.housemate.exe.command.model;


import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn4.housemate.exe.command.Command;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn4.housemate.exe.util.CommandParser;

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
    protected void executeShowCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        commandParser.ensureNextToken(CONFIGURATION);

        String entityType =  commandParser.safeGetNextToken();

        if(entityType == null){
            modelService.showConfiguration(accessToken);
        }else if(HOUSE.equals(entityType)){
            String houseId = commandParser.getNextToken("House identifier");
            commandParser.ensureTermination();

            modelService.showHouseConfiguration(accessToken, houseId);
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

            modelService.showRoomConfiguration(accessToken, houseId, roomId);
        }
    }
}
