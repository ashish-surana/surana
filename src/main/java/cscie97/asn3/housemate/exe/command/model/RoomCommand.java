package cscie97.asn3.housemate.exe.command.model;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.support.RoomType;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.exe.util.CommandParser;

/**
 *
 */
public class RoomCommand extends Command {


    private static final String ROOM = "room";
    private static final String FLOOR = "floor";
    private static final String TYPE = "type";

    public RoomCommand(AccessToken accessToken) {
        super(accessToken);
    }

    /**
     * Expected format:
     * <p>
     * define room kitchen1 floor 1 type kitchen house house1
     * </p>
     * @param commandParser
     */
    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException, EntityNotFoundException {
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(ROOM);
        String roomId = commandParser.getNextToken("Room identifier");

        commandParser.ensureNextToken(FLOOR);
        Integer floorNumber = commandParser.getNextIntegerToken("Floor number");

        commandParser.ensureNextToken("type");
        String roomTypeString = commandParser.getNextToken("Room type");
        RoomType roomType = RoomType.getEnum(roomTypeString);
        if(roomType == null){
            throw new InvalidCommandException(commandParser.getInputCommand(), "Unrecognized room type: '" + roomTypeString + "'");
        }

        commandParser.ensureNextToken("house");
        String houseId = commandParser.getNextToken("House identifier");

        commandParser.ensureTermination();

        modelService.defineRoom(accessToken, houseId, roomId, roomType, floorNumber);
    }


}
