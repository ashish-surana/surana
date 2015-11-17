package cscie97.asn3.housemate.controller.service.impl;

import cscie97.asn3.housemate.controller.command.ControllerCommand;
import cscie97.asn3.housemate.controller.command.factory.ControllerCommandFactory;
import cscie97.asn3.housemate.controller.service.HouseMateControllerService;
import cscie97.asn3.housemate.core.init.StartUpService;
import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.credential.PasswordCredential;
import cscie97.asn3.housemate.entitlement.exception.*;
import cscie97.asn3.housemate.entitlement.service.HouseMateEntitlementService;
import cscie97.asn3.housemate.entitlement.service.factory.HouseMateEntitlementServiceFactory;
import cscie97.asn3.housemate.model.listener.support.DeviceStatusChangeEvent;
import cscie97.asn3.housemate.model.service.exception.*;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;

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
            ControllerCommand command = ControllerCommandFactory.createCommand(event);
            command.execute();
        }catch (InvalidStatusException e){
            String message = "Error executing a controller command on entity with id: '"+ e.getEntity().getIdentifier();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            message += " Invalid status being set was: '" + e.getStatusKey();
            message += "' with value: '" + e.getStatusValue() + "'.";
            System.out.println(message);
        }catch (EntityExistsException e){
            String message = "Error executing a controller command on entity with id: '"+ e.getEntity().getIdentifier();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.out.println(message);
        }catch (EntityNotFoundException e) {
            String message = "Error executing a controller command on entity with id: '"+ e.getEntity();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.out.println(message);
        } catch (InvalidCommandException e) {
            String message = "Error executing the controller command '"+ e.getCommand();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.out.println(message);
        } catch (AccessDeniedException e) {
            String message = "Error executing a controller command. Error message is: '"+
                    e.getMessage()+ "'";
            System.out.println(message);
        } catch (InvalidAccessTokenException e) {
            String message = "Error executing a controller command. Error message is: '" +
                   e.getMessage() + "'.";
            if(e.getAccessToken() != null){
                message += " Offending accessToken id: '" + e.getAccessToken().getIdentifier()
                        +"' and user id: " + e.getAccessToken().getUserId() +"'.";
            }else{
                message += " Offending accessToken was null.";
            }
            System.out.println(message);
        } catch (cscie97.asn3.housemate.entitlement.exception.EntityNotFoundException e) {
            String message = "Error executing a controller command on entity with id: '"+ e.getEntity();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.out.println(message);
        } catch (AuthenticationException e) {
            String message = "Error logging in as user id: '" + e.getUserId();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.out.println(message);
        } catch (EntitlementServiceException e) {
            String message = "Error executing a command";
            message += ". Error message is: '"+ e.getMessage()+"'.";
            System.out.println(message);
        }
    }
}
