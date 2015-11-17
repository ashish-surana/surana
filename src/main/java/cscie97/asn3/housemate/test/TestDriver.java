package cscie97.asn3.housemate.test;

import cscie97.asn3.housemate.core.init.StartUpService;
import cscie97.asn3.housemate.core.init.exception.InitializationException;
import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.AuthenticationException;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.service.HouseMateEntitlementService;
import cscie97.asn3.housemate.entitlement.service.factory.HouseMateEntitlementServiceFactory;
import cscie97.asn3.housemate.model.service.exception.*;
import cscie97.asn3.housemate.exe.HouseMateServiceExecutor;
import cscie97.asn3.housemate.model.service.exception.EntityException;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;

import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is a test driver for {@link cscie97.asn3.housemate.model.service.HouseMateModelService}.
 * This class can be invoked using following command:
 * <p>
 * {@code
 *     java -cp . cscie97.asn3.housemate.test.TestDriver <configFilePath>
 *     }
 * </p>, where <configFilePath> is the absolute or relative path to HouseMate model service configuration file.
 */
public class TestDriver {

    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            //Initialize HouseMate Automation System.
            StartUpService.init();

            while (true) {
                //Nice to do: Ask whether user wants to execute a file or a command
                //read next file path
                //execute file
                printUsage();
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if("q".equalsIgnoreCase(input)){
                    break;
                }
                executeCommandsFromFile(input);
                System.out.println();
                System.out.println();
            }
        }catch (NoSuchElementException e){
            //No file was entered by user.
            //Do nothing.
        }catch (InitializationException e) {
            String message = "Error occurred with message: '" + e.getMessage() + "' during HouseMate automation system initialization.";
            System.out.println(message);
        }
    }

    private static void executeCommandsFromFile(String configFilePath){
        try {
            //Get Entitlement Service, and login as admin user.
            HouseMateEntitlementServiceFactory entitlementServiceFactory = new HouseMateEntitlementServiceFactory();
            HouseMateEntitlementService entitlementService = entitlementServiceFactory.getService();
            AccessToken accessToken = entitlementService.login(StartUpService.getAdminPassword());

            //Pass admin access token to executor for executing the commands from config file.
            HouseMateServiceExecutor executor = new HouseMateServiceExecutor(accessToken);
            executor.executeFile(configFilePath);
        }catch (InvalidBulkCommandException e) {
            String message = "Error executing command: '" + e.getCommand() + "' from file:'"+e.getFileName()
                    +"', at line: "+e.getLineNum() +
                    ". Error message is: '";
            message += e.getMessage() + "'.";
            System.out.println(message);
        }catch (InvalidCommandException e) {
            String message = "Error executing command: '" + e.getCommand() + "'. Error message is: '";
            message += e.getMessage() + "'.";
            System.out.println(message);
        }catch (InvalidStatusException e){
            String message = "Error executing a command on entity with id: '"+ e.getEntity().getIdentifier();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            message += " Invalid status being set was: '" + e.getStatusKey();
            message += "' with value: '" + e.getStatusValue() + "'.";
            System.out.println(message);
        }catch (EntityExistsException e){
            String message = "Error executing a command on entity with id: '"+ e.getEntity().getIdentifier();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.out.println(message);
        }catch (EntityNotFoundException e) {
            String message = "Error executing a command on entity with id: '"+ e.getEntity();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.out.println(message);
        } catch (EntityException e) {
            String message = "Error executing a command on entity: '"+ e.getEntity();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.out.println(message);
        } catch (HouseMateModelServiceException e) {
            String message = "Error occurred with message: '"+ e.getMessage()+"'.";
            System.out.println(message);
        } catch (cscie97.asn3.housemate.entitlement.exception.EntityNotFoundException e) {
            String message = "Error executing a command on entity with id: '"+ e.getEntity();
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

    /**
     * Prints usage instructions for {@link TestDriver}.
     */
    private static void printUsage() {
        System.out.println("Usage instructions:");
        System.out.println("Enter config file path to run commands. Enter q to quit:");
    }
}
