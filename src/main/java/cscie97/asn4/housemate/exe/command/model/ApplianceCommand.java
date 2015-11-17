package cscie97.asn4.housemate.exe.command.model;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn4.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn4.housemate.model.service.exception.EntityExistsException;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn4.housemate.model.service.exception.InvalidEntityTypeException;
import cscie97.asn4.housemate.exe.util.CommandParser;
import cscie97.asn4.housemate.model.support.DeviceType;

/**
 *
 */
public class ApplianceCommand extends DeviceCommand{

    private static final String TYPE = "type";
    private static final String ROOM = "room";

    public ApplianceCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeDefineCommand(CommandParser commandParser) throws InvalidCommandException, EntityExistsException, EntityNotFoundException, InvalidEntityTypeException, AccessDeniedException, InvalidAccessTokenException {
        assert commandParser != null : "Command parser cannot be null";

        commandParser.ensureNextToken(APPLIANCE);

        String applianceId = commandParser.getNextToken("Appliance identifier");

        commandParser.ensureNextToken(TYPE);
        String applianceTypeString = commandParser.getNextToken("Appliance type").toLowerCase();

        if(!DeviceType.isValidApplianceType(applianceTypeString)){
            throw new InvalidCommandException(commandParser.getInputCommand(), "Unrecognized appliance type: '"+applianceTypeString+"'");
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

        modelService.defineAppliance(accessToken, houseId, roomId, applianceTypeString, applianceId);
    }

}
