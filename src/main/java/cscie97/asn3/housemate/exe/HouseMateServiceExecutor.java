package cscie97.asn3.housemate.exe;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.*;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.model.service.exception.*;
import cscie97.asn3.housemate.exe.command.factory.CommandFactory;
import cscie97.asn3.housemate.model.service.exception.EntityException;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;

import java.io.*;

/**
 * This class executes commands against HouseMateModelService and HouseMateEntitlementService by either reading the commands from
 * an input file, or one command at a time.
 * This class looks up the appropriate {@link cscie97.asn3.housemate.exe.command.Command} by using
 * CommandRegistry, and invokes it.
 */
public class HouseMateServiceExecutor {

    private final AccessToken accessToken;
    private final CommandFactory commandFactory;

    public HouseMateServiceExecutor(AccessToken accessToken){
        assert accessToken != null : "AccessToken cannot be null";

        this.accessToken = accessToken;
        this.commandFactory = new CommandFactory();
    }


    public void executeFile(String fileName) throws InvalidBulkCommandException,
            HouseMateModelServiceException {
        BufferedReader bufferedReader = null;
        int lineNum = 0;
        String nextLine = null;
        try{
            bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
            while((nextLine = bufferedReader.readLine()) != null){
                lineNum++;
                try{
                    execute(nextLine);
                }catch (InvalidCommandException e) {
                    String message = "Error executing command: '" + e.getCommand() + "'. Error message is: '";
                    message += e.getMessage() + "'.";
                    message += " Command was read from file: '" + fileName + "' at line :" + lineNum + ".";
                    System.err.println(message);
                }catch (InvalidStatusException e){
                    String message = "Error executing a command on entity with id: '"+ e.getEntity().getIdentifier();
                    message += "'. Error message is: '"+ e.getMessage()+"'.";
                    message += " Invalid status being set was: '" + e.getStatusKey();
                    message += "' with value: '" + e.getStatusValue() + "'.";
                    message += " Command was read from file: '" + fileName + "' at line :" + lineNum + ".";
                    System.err.println(message);
                }catch (EntityExistsException e){
                    String message = "Error executing a command on entity with id: '"+ e.getEntity().getIdentifier();
                    message += "'. Error message is: '"+ e.getMessage()+"'.";
                    message += " Command was read from file: '" + fileName + "' at line :" + lineNum + ".";
                    System.err.println(message);
                }catch (EntityNotFoundException e) {
                    String message = "Error executing a command on entity with id: '"+ e.getEntity();
                    message += "'. Error message is: '"+ e.getMessage()+"'.";
                    message += " Command was read from file: '" + fileName + "' at line :" + lineNum + ".";
                    System.err.println(message);
                } catch (EntityException e) {
                    String message = "Error executing a command on entity: '"+ e.getEntity();
                    message += "'. Error message is: '"+ e.getMessage()+"'.";
                    message += " Command was read from file: '" + fileName + "' at line :" + lineNum + ".";
                    System.err.println(message);
                } catch (cscie97.asn3.housemate.entitlement.exception.EntityException e) {
                    String message = "Error executing a command on entity: '"+ e.getEntity();
                    message += "'. Error message is: '"+ e.getMessage()+"'.";
                    message += " Command was read from file: '" + fileName + "' at line :" + lineNum + ".";
                    System.err.println(message);
                }
            }
        }catch (FileNotFoundException e){
            throw new HouseMateModelServiceException(e.getMessage());
        }
        catch (IOException e){
            throw new InvalidBulkCommandException(fileName, lineNum, nextLine, e.getMessage());
        }
        finally {
            if(bufferedReader!= null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new HouseMateModelServiceException(e.getMessage());
                }
            }
        }
    }

    public void execute(String inputCommand) throws InvalidCommandException, EntityException, cscie97.asn3.housemate.entitlement.exception.EntityException {
        if(inputCommand == null){
            throw new InvalidCommandException("Command cannot be null", inputCommand);
        }

        inputCommand = inputCommand.trim();

        if(inputCommand.startsWith("#") || "".equals(inputCommand)){
            return;
        }

        Command command = commandFactory.createCommand(accessToken, inputCommand);
        command.execute(inputCommand);
    }
}
