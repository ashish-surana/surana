package cscie97.asn4.housemate.exe.command.model;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn4.housemate.model.service.exception.InvalidEntityTypeException;
import cscie97.asn4.housemate.model.support.DeviceType;
import cscie97.asn4.housemate.model.service.exception.EntityExistsException;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn4.housemate.exe.util.CommandParser;

/**
 *
 */
public class SensorCommand extends DeviceCommand {

    private static final String TYPE = "type";
    private static final String ROOM = "room";

    public SensorCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException, EntityNotFoundException, InvalidEntityTypeException, AccessDeniedException, InvalidAccessTokenException {
        //define sensor smoke_detector1 type smoke_detector room house1:kitchen1
        assert commandParser!=null : "Command parser cannot be null";

        commandParser.ensureNextToken(SENSOR);
        String sensorId = commandParser.getNextToken("Sensor identifier");

        commandParser.ensureNextToken(TYPE);

        String sensorTypeString = commandParser.getNextToken("Sensor type").toLowerCase();

        if(!DeviceType.isValidSensorType(sensorTypeString)){
            throw new InvalidCommandException(commandParser.getInputCommand(), "Unrecognized sensor type: '"+sensorTypeString+"'");
        }

        commandParser.ensureNextToken(ROOM);

        String houseAndRoomId = commandParser.getNextToken("House identifier: Room identifier");
        commandParser.ensureTermination();

        String[] identifiers = houseAndRoomId.split(":");

        if(identifiers.length != 2){
            throw new InvalidCommandException(commandParser.getInputCommand(),
                    "Illegal house and room identifiers: '"+ houseAndRoomId+ "'");
        }

        String houseId = identifiers[0];
        String roomId = identifiers[1];

        modelService.defineSensor(accessToken, houseId, roomId, sensorTypeString, sensorId);
    }
}
