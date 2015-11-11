package cscie97.asn3.housemate.controller.service.impl;

import cscie97.asn3.housemate.controller.command.ControllerCommand;
import cscie97.asn3.housemate.controller.command.factory.ControllerCommandFactory;
import cscie97.asn3.housemate.controller.service.HouseMateControllerService;
import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.listener.support.DeviceStatusChangeEvent;
import cscie97.asn3.housemate.model.service.exception.*;

/**
 * This class is used to monitor changes in device's status. It then uses ControllerCommandFactory
 * to create appropriate ControllerCommand objects in response to the device status change.
 * Finally, it executes the ControllerCommand objects.
 */
public class HouseMateControllerServiceImpl implements HouseMateControllerService{

    private static final HouseMateControllerService INSTANCE = new HouseMateControllerServiceImpl();

    public static HouseMateControllerService getInstance() {
        return INSTANCE;
    }

    /**
     * This method reacts to a change in device's status, by creating appropriate ControllerCommand
     * using ControllerCommandFactory. It then executes those commands.
     * This method also prints a brief detail about the device's status change.
     */
    @Override
    public void statusChanged(DeviceStatusChangeEvent event) {
        try {
            AccessToken accessToken = null;
            ControllerCommand command = ControllerCommandFactory.createCommand(accessToken, event);
            command.execute();
        }catch (InvalidStatusException e){
            String message = "Error executing a controller command on entity with id: '"+ e.getEntity().getIdentifier();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            message += " Invalid status being set was: '" + e.getStatusKey();
            message += "' with value: '" + e.getStatusValue() + "'.";
            System.err.println(message);
        }catch (EntityExistsException e){
            String message = "Error executing a controller command on entity with id: '"+ e.getEntity().getIdentifier();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.err.println(message);
        }catch (EntityNotFoundException e) {
            String message = "Error executing a controller command on entity with id: '"+ e.getEntity();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.err.println(message);
        } catch (InvalidCommandException e) {
            String message = "Error executing the controller command '"+ e.getCommand();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.err.println(message);
        }
    }
}
