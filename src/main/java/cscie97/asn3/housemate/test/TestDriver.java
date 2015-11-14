package cscie97.asn3.housemate.test;

import cscie97.asn3.housemate.core.init.StartUpService;
import cscie97.asn3.housemate.model.service.exception.*;
import cscie97.asn3.housemate.exe.HouseMateServiceExecutor;

import java.io.UnsupportedEncodingException;

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
        if(args == null || args.length == 0){
            printUsage();
            return;
        }
        StartUpService.init();
        String configFilePath = args[0];
        HouseMateServiceExecutor executor = new HouseMateServiceExecutor();
        try {
            executor.executeFile(configFilePath);
        }catch (InvalidBulkCommandException e) {
            String message = "Error executing command: '" + e.getCommand() + "' from file:'"+e.getFileName()
                    +"', at line: "+e.getLineNum() +
                    ". Error message is: '";
            message += e.getMessage() + "'.";
            System.err.println(message);
        }catch (InvalidCommandException e) {
            String message = "Error executing command: '" + e.getCommand() + "'. Error message is: '";
            message += e.getMessage() + "'.";
            System.err.println(message);
        }catch (InvalidStatusException e){
            String message = "Error executing a command on entity with id: '"+ e.getEntity().getIdentifier();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            message += " Invalid status being set was: '" + e.getStatusKey();
            message += "' with value: '" + e.getStatusValue() + "'.";
            System.err.println(message);
        }catch (EntityExistsException e){
            String message = "Error executing a command on entity with id: '"+ e.getEntity().getIdentifier();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.err.println(message);
        }catch (EntityNotFoundException e) {
            String message = "Error executing a command on entity with id: '"+ e.getEntity();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.err.println(message);
        } catch (EntityException e) {
            String message = "Error executing a command on entity: '"+ e.getEntity();
            message += "'. Error message is: '"+ e.getMessage()+"'.";
            System.err.println(message);
        } catch (HouseMateModelServiceException e) {
            String message = "Error occurred with message: '"+ e.getMessage()+"'.";
            System.err.println(message);
        }
    }

    /**
     * Prints usage instructions for {@link TestDriver}.
     */
    private static void printUsage() {
        System.out.println("Usage instructions:" +
                "Run following command to invoke the TestDriver: ");
        System.out.println("java -cp . cscie97.asn3.housemate.test.TestDriver <configFilePath>");
        System.out.println(", where <configFilePath> is the absolute or relative path to HouseMate model service configuration file.");
    }
}
