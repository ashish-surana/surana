package cscie97.asn3.housemate.controller.command.factory;

import cscie97.asn3.housemate.controller.command.*;
import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.appliance.Ava;
import cscie97.asn3.housemate.model.appliance.Oven;
import cscie97.asn3.housemate.model.appliance.Refrigerator;
import cscie97.asn3.housemate.model.listener.support.DeviceStatusChangeEvent;
import cscie97.asn3.housemate.model.sensor.Camera;
import cscie97.asn3.housemate.model.sensor.SmokeDetector;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;
import cscie97.asn3.housemate.exe.util.CommandParser;
import cscie97.asn3.housemate.model.support.DeviceType;

/**
 * A factory for creating ControllerCommand objects. Each ControllerCommand object reacts to a change in
 * status of a particular Sensor or Appliance.
 */
public class ControllerCommandFactory {

    //private constructor to avoid instantiation of this class.
    private ControllerCommandFactory(){
    }

    /**
     * This method creates an appropriate ControllerCommand object in response to
     * the given DeviceStatusChangeEvent.
     * If no specific ControllerCommand is found for the given event, then a NoOpCommand is returned.
     */
    public static ControllerCommand createCommand(AccessToken accessToken, DeviceStatusChangeEvent event) throws InvalidCommandException {
        assert event!=null : "DeviceStatusChangeEvent cannot be null";

        switch (event.getDeviceType()){
            case Camera.DEVICE_TYPE:
                return handleCameraCommand(accessToken, event);
            case SmokeDetector.DEVICE_TYPE:
                return handleSmokeDetectorCommand(accessToken, event);
            case Oven.DEVICE_TYPE:
                return handleOvenCommand(accessToken, event);
            case Ava.DEVICE_TYPE:
                return handleAvaCommand(accessToken, event);
            case Refrigerator.DEVICE_TYPE:
                return handleRefrigeratorCommand(accessToken, event);
            default:
                return handleNoOpCommand(accessToken, event);
        }
    }

    /**
     * This method is responsible for creating a ControllerCommand object in response to a change in
     * refrigerator's beer-count status.
     */
    private static ControllerCommand handleRefrigeratorCommand(AccessToken accessToken, DeviceStatusChangeEvent event) {
        if(Refrigerator.BEER_COUNT.equalsIgnoreCase(event.getStatusKey())){
            int beerCount = Integer.parseInt(event.getNewStatusValue());
            if(beerCount < 4){
                logCommandInfo(OrderBeerCommand.class.getSimpleName(), event);
                return new OrderBeerCommand(accessToken, event.getDeviceId(), event.getHouseId(), event.getRoomId());
            }
        }
        return handleNoOpCommand(accessToken, event);
    }

    /**
     * This method is responsible for creating a ControllerCommand object in response to a change in
     * Ava's status.
     */
    private static ControllerCommand handleAvaCommand(AccessToken accessToken, DeviceStatusChangeEvent event) throws InvalidCommandException {
        if(Ava.LISTENING.equalsIgnoreCase(event.getStatusKey())){
            String avaCommand = getAvaCommand(event.getNewStatusValue());
            switch (avaCommand){
                case "open door":
                    logCommandInfo(AvaOpenDoorCommand.class.getSimpleName(), event);
                    return new AvaOpenDoorCommand(accessToken, event.getHouseId(), event.getRoomId());
                case "close door":
                    logCommandInfo(AvaCloseDoorCommand.class.getSimpleName(), event);
                    return new AvaCloseDoorCommand(accessToken, event.getHouseId(), event.getRoomId());
                case "lights on":
                    logCommandInfo(AvaLightsOnCommand.class.getSimpleName(), event);
                    return new AvaLightsOnCommand(accessToken, event.getHouseId(), event.getRoomId());
                case "lights off":
                    logCommandInfo(AvaLightsOnCommand.class.getSimpleName(), event);
                    return new AvaLightsOffCommand(accessToken, event.getHouseId(), event.getRoomId());
            }

            if(avaCommand.startsWith("where-is")){
                return handleAvaOccupantLocationCommand(accessToken, event, avaCommand);
            }else {
                return handleGenericApplianceCommand(accessToken, event, avaCommand);
            }
        }

        return handleNoOpCommand(accessToken, event);
    }

    /**
     * This method logs the given event, and the corresponding command.
     * This logging can be helpful in debugging the HouseMate system.
     */
    private static void logCommandInfo(String commandClass, DeviceStatusChangeEvent event) {
        System.out.println("Found "+ commandClass +" for responding to following event:");
        System.out.println(event);
    }

    /**
     * This method is responsible for creating a ControllerCommand object in response to an Ava command
     * for changing an appliance's status.
     */
    private static ControllerCommand handleGenericApplianceCommand(AccessToken accessToken, DeviceStatusChangeEvent event, String avaCommand) throws InvalidCommandException {
        //Ava command should be of format: thermostat temperature 69
        CommandParser commandParser = new CommandParser(avaCommand);
        String applianceType  = commandParser.getNextToken("Appliance type");

        if(!DeviceType.isValidApplianceType(applianceType)){
            return handleNoOpCommand(accessToken, event);
        }

        String statusKey = commandParser.getNextToken("Status key").toUpperCase();
        String statusValue = commandParser.getNextToken("Status value");
        commandParser.ensureTermination();

        logCommandInfo(AvaGenericApplianceCommand.class.getSimpleName(), event);
        return new AvaGenericApplianceCommand(accessToken, event.getDeviceId(), event.getHouseId(), event.getRoomId(),
                applianceType, statusKey, statusValue);
    }

    /**
     * This method is responsible for creating a ControllerCommand object in response to a change in
     * Ava's listening status, when the Ava listens for where-is command.
     */
    private static ControllerCommand handleAvaOccupantLocationCommand(AccessToken accessToken, DeviceStatusChangeEvent event, String avaCommand) throws InvalidCommandException {
        //The format of avaCommand is:  where-is joe_smith?
        CommandParser commandParser = new CommandParser(avaCommand);
        commandParser.ensureNextToken("where-is");
        String occupantId = commandParser.getNextToken("Occupant identifier?");

        //let's remove the tailing question mark
        occupantId = occupantId.substring(0, occupantId.length()-1);

        commandParser.ensureTermination();
        logCommandInfo(AvaOccupantLocationCommand.class.getSimpleName(), event);
        return new AvaOccupantLocationCommand(accessToken, event.getHouseId(),event.getRoomId(), event.getDeviceId(), occupantId);
    }

    /**
     * This method is responsible for creating a ControllerCommand object in response to a change in
     * Ava's LISTENING status.
     */
    private static String getAvaCommand(String listenedCommand) throws InvalidCommandException {
        //listenedCommand is of the format: "joe_smith says: 'open door'"
        CommandParser commandParser = new CommandParser(listenedCommand);
        String occupantId = commandParser.getNextToken("Occupant identifier");
        commandParser.ensureNextToken("says:");
        String avaCommand = commandParser.getNextTokenInSingleQuotes("Ava command").toLowerCase();
        commandParser.ensureTermination();
        return avaCommand;
    }

    /**
     * This method is responsible for creating a ControllerCommand object in response to a change in
     * Oven's time-to-cook status.
     */
    private static ControllerCommand handleOvenCommand(AccessToken accessToken, DeviceStatusChangeEvent event) {
        if(!Oven.TIME_TO_COOK.equalsIgnoreCase(event.getStatusKey())){
            return handleNoOpCommand(accessToken, event);
        }

        int timeToCook = Integer.parseInt(event.getNewStatusValue());

        if(timeToCook == 0){
            logCommandInfo(OvenCookingCompletedCommand.class.getSimpleName(), event);
            return new OvenCookingCompletedCommand(accessToken,
                    event.getHouseId(), event.getRoomId(),
                    event.getDeviceType(), event.getDeviceId());
        }

        return handleNoOpCommand(accessToken, event);
    }

    /**
     * This method is responsible for creating a ControllerCommand object in response to a change in
     * smoke detector's status.
     */
    private static ControllerCommand handleSmokeDetectorCommand(AccessToken accessToken, DeviceStatusChangeEvent event) {
        if (SmokeDetector.MODE.equalsIgnoreCase(event.getStatusKey())
                && SmokeDetector.FIRE.equalsIgnoreCase(event.getNewStatusValue())){
            logCommandInfo(FireDetectedCommand.class.getSimpleName(), event);
            return new FireDetectedCommand(accessToken, event.getHouseId(), event.getRoomId());
        }

        return handleNoOpCommand(accessToken, event);
    }

    /**
     * This method creates a NoOpCommand. NoOpCommand simply does nothing.
     */
    private static ControllerCommand handleNoOpCommand(AccessToken accessToken, DeviceStatusChangeEvent event) {
        logCommandInfo(NoOpCommand.class.getSimpleName(), event);
        return new NoOpCommand(accessToken, event.getDeviceId(), event.getDeviceType(), event.getStatusKey(),
                event.getOldStatusValue(), event.getNewStatusValue());
    }

    /**
     * This method is responsible for creating a ControllerCommand object in response to a change in
     * camera status.
     */
    private static ControllerCommand handleCameraCommand(AccessToken accessToken, DeviceStatusChangeEvent event) {
        switch (event.getStatusKey()){
            case Camera.OCCUPANT_DETECTED:
                logCommandInfo(OccupantDetectedCommand.class.getSimpleName(), event);
                return new OccupantDetectedCommand(accessToken, event.getHouseId(), event.getRoomId(), event.getNewStatusValue());
            case Camera.OCCUPANT_LEAVING:
                logCommandInfo(OccupantLeavingCommand.class.getSimpleName(), event);
                return new OccupantLeavingCommand(accessToken, event.getHouseId(), event.getRoomId(), event.getNewStatusValue());
            case Camera.OCCUPANT_RESTING:
                logCommandInfo(OccupantRestingCommand.class.getSimpleName(), event);
                return new OccupantRestingCommand(accessToken, event.getHouseId(), event.getRoomId(), event.getNewStatusValue());
            case Camera.OCCUPANT_ACTIVE:
                logCommandInfo(OccupantActiveCommand.class.getSimpleName(), event);
                return new OccupantActiveCommand(accessToken, event.getHouseId(), event.getRoomId(), event.getNewStatusValue());
        }
        return handleNoOpCommand(accessToken, event);
    }
}
