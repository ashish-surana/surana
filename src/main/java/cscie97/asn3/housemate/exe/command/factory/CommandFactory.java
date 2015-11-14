package cscie97.asn3.housemate.exe.command.factory;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.exe.command.model.*;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;

/**
 * This class provides a mechanism to create appropriate {@link cscie97.asn3.housemate.exe.command.model.Command} for the input command string.
 */
public class CommandFactory {

    private CommandFactory(){

    }

    /**
     * Looks up an appropriate command to fulfill baseCommand.
     * @param baseCommand
     * @return
     * @throws InvalidCommandException If no appropriate command is found for fulfilling the baseCommand.
     */
    public static Command createCommand(AccessToken accessToken, String baseCommand) throws InvalidCommandException {
        assert baseCommand!=null && !"".equals(baseCommand) : "Input command cannot be null or empty string.";

        Command command = getCommand(accessToken, baseCommand);

        if(command == null){
            throw new InvalidCommandException(baseCommand, "Unrecognized command: '"+baseCommand+"'");
        }

        return command;
    }

    private static Command getCommand(AccessToken accessToken, final String baseCommand) {
        switch (baseCommand){
            case "define house":
                return new HouseCommand(accessToken);
            case "define room":
                return new RoomCommand(accessToken);
            case "define occupant":
                return new OccupantCommand(accessToken);
            case "add occupant":
                return new OccupantCommand(accessToken);
            case "define sensor":
                return new SensorCommand(accessToken);
            case "set sensor":
                return new SensorCommand(accessToken);
            case "show sensor":
                return new SensorCommand(accessToken);
            case "define appliance":
                return new ApplianceCommand(accessToken);
            case "set appliance":
                return new ApplianceCommand(accessToken);
            case "show appliance":
                return new ApplianceCommand(accessToken);
            case "show configuration":
                return new ConfigurationCommand(accessToken);
            default:
                return null;
        }
    }
}
