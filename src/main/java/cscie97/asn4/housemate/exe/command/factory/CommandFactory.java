package cscie97.asn4.housemate.exe.command.factory;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.exe.command.Command;
import cscie97.asn4.housemate.exe.command.entitlement.*;
import cscie97.asn4.housemate.exe.command.model.*;
import cscie97.asn4.housemate.model.service.exception.InvalidCommandException;

/**
 * This class provides a mechanism to create appropriate {@link cscie97.asn4.housemate.exe.command.Command} for the input command string.
 */
public class CommandFactory {

    public CommandFactory(){

    }

    /**
     * Looks up an appropriate command to fulfill baseCommand.
     * @param inputCommand
     * @return
     * @throws InvalidCommandException If no appropriate command is found for fulfilling the baseCommand.
     */
    public Command createCommand(AccessToken accessToken, String inputCommand) throws InvalidCommandException {
        assert inputCommand!=null && !"".equals(inputCommand) : "Input command cannot be null or empty string.";

        Command command = getCommand(accessToken, inputCommand);

        if(command == null){
            throw new InvalidCommandException(inputCommand, "Unrecognized command: '"+inputCommand+"'");
        }

        return command;
    }

    private static Command getCommand(AccessToken accessToken, final String inputCommand) {
        String[] commandArray = inputCommand.split(" ", 2);

        String operation = commandArray[0];
        String operand = commandArray[1].trim().split(" ", 2)[0];

        String baseCommand = operation + " " + operand;

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
            //start of entitlement service commands
            case "define permission":
                return new PermissionCommand(accessToken);
            case "define role":
            case "add entitlement_to_role":
                return new RoleCommand(accessToken);
            case "create user":
            case "add user_credential":
            case "add role_to_user" :
            case "add resource_role_to_user":
                return new UserCommand(accessToken);
            case "define resource":
                return new ResourceCommand(accessToken);
            case "create resource_role":
                return new ResourceRoleCommand(accessToken);
            case "show entitlements":
                return new EntitlementsCommand(accessToken);
            default:
                return null;
        }
    }
}
