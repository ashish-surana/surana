package cscie97.asn3.housemate.exe.command.model;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;
import cscie97.asn3.housemate.exe.util.CommandParser;

import static cscie97.asn3.housemate.model.sensor.Camera.*;
import static cscie97.asn3.housemate.model.appliance.Ava.*;

/**
 *
 */
public class DeviceCommand extends Command {

    protected static final String APPLIANCE = "appliance";
    protected static final String SENSOR = "sensor";

    private static final String STATUS = "status";
    private static final String VALUE = "value";

    public DeviceCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeSetCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, InvalidStatusException {
        assert commandParser != null : "Command parser cannot be null";
        
        commandParser.ensureNextToken(APPLIANCE, SENSOR);

        String deviceLocation = commandParser.getNextToken("Device location");
        String[] identifiers = deviceLocation.split(":");

        if(identifiers.length != 3){
            throw new InvalidCommandException(commandParser.getInputCommand(),
                    "Illegal device location: '"+ deviceLocation+ "'");
        }

        String houseId = identifiers[0];
        String roomId = identifiers[1];
        String deviceId = identifiers[2];

        commandParser.ensureNextToken(STATUS);
        String statusKey = commandParser.getNextToken("Status key");
        statusKey = statusKey.toUpperCase();

        commandParser.ensureNextToken(VALUE);

        String statusValue;
        if(OCCUPANT_DETECTED.equalsIgnoreCase(statusKey) ||
                OCCUPANT_LEAVING.equalsIgnoreCase(statusKey) ||
                OCCUPANT_RESTING.equalsIgnoreCase(statusKey) ||
                OCCUPANT_ACTIVE.equalsIgnoreCase(statusKey) ||
                LISTENING.equalsIgnoreCase(statusKey)){
            statusValue = commandParser.getNextTokenInDoubleQuotes("Status value");
        }else{
            statusValue = commandParser.getNextToken("Status value");
        }


        commandParser.ensureTermination();

        modelService.setDeviceStatus(accessToken, houseId, roomId, deviceId, statusKey, statusValue);
    }

    @Override
    protected void executeShowCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException {
        assert commandParser != null : "Command parser cannot be null";

        commandParser.ensureNextToken(APPLIANCE, SENSOR);

        String deviceLocation = commandParser.getNextToken("Device location");
        String[] identifiers = deviceLocation.split(":");

        if(identifiers.length != 3){
            throw new InvalidCommandException(commandParser.getInputCommand(),
                    "Illegal device location: '"+ deviceLocation+ "'");
        }

        String houseId = identifiers[0];
        String roomId = identifiers[1];
        String deviceId = identifiers[2];

        String status = commandParser.safeGetNextToken();
        if(status == null){
            commandParser.ensureTermination();
            modelService.showDeviceStatus(accessToken, houseId, roomId, deviceId);
            return;
        }

        if(!STATUS.equals(status)){
            throw new InvalidCommandException(commandParser.getInputCommand(),
                    "Illegal argument: '"+ status +"', expected: '"+ STATUS+"'");
        }

        String statusKey = commandParser.getNextToken("Device status key");
        commandParser.ensureTermination();
        modelService.showDeviceStatus(accessToken, houseId, roomId, deviceId, statusKey);
    }
}
