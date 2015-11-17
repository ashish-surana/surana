package cscie97.asn4.housemate.exe.command.entitlement;

import cscie97.asn4.housemate.entitlement.AccessToken;
import cscie97.asn4.housemate.entitlement.Credential;
import cscie97.asn4.housemate.entitlement.credential.PasswordCredential;
import cscie97.asn4.housemate.entitlement.credential.VoicePrintCredential;
import cscie97.asn4.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn4.housemate.exe.command.Command;
import cscie97.asn4.housemate.exe.util.CommandParser;
import cscie97.asn4.housemate.model.service.exception.EntityExistsException;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidCommandException;

/**
 * This command should be execute to add a user to entitlement service.
 */
public class UserCommand extends Command{

    private static final String USER = "user";

    private static final String USER_CREDENTIAL = "user_credential";

    private static final String VOICE_PRINT = "voice_print";

    private static final String PASSWORD = "password";

    private static final String ROLE_TO_USER = "role_to_user";

    private static final String RESOURCE_ROLE_TO_USER = "resource_role_to_user";

    public UserCommand(AccessToken accessToken) {
        super(accessToken);
    }

    @Override
    protected void executeCreateCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntityExistsException, EntitlementServiceException {
        //Input command format is:
        //create user debra "Debra Smart"
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(USER);
        String identifier = commandParser.getNextToken("User identifier");
        String name = commandParser.getNextTokenInDoubleQuotes("User name");
        commandParser.ensureTermination();

        entitlementService.createUser(identifier, name);
    }

    @Override
    protected void executeAddCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntityExistsException, EntitlementServiceException {
        //Input command format is:
        //add user_credential jimmy voice_print jimmy, or
        //add user_credential debra password secret, or
        //add role_to_user sam child_resident, or
        //add resource_role_to_user sam house1_adult_resident
        assert commandParser !=null : "Command parser cannot be null";

        String command = commandParser.ensureNextToken(USER_CREDENTIAL, ROLE_TO_USER, RESOURCE_ROLE_TO_USER);

        if(USER_CREDENTIAL.equals(command)){
            executeAddUserCredentialCommand(commandParser);
        }else if(ROLE_TO_USER.equals(command)) {
            executeAddRoleToUserCommand(commandParser);
        }else if(RESOURCE_ROLE_TO_USER.equals(command)) {
            executeAddResourceRoleToUserCommand(commandParser);
        }else {
            throw new InvalidCommandException(commandParser.getInputCommand(), "Unrecognized command");
        }

    }

    private void executeAddUserCredentialCommand(CommandParser commandParser) throws EntitlementServiceException, InvalidCommandException {
        //Input command format is:
        //add user_credential jimmy voice_print jimmy, or
        //add user_credential debra password secret
        String userId = commandParser.getNextToken("User identifier");
        String credentialType = commandParser.ensureNextToken(VOICE_PRINT, PASSWORD);
        String plainTextCredential = commandParser.getNextToken("User "+ credentialType);
        commandParser.ensureTermination();

        Credential credential = null;

        if(VOICE_PRINT.equals(credentialType)){
            credential = new VoicePrintCredential(userId, plainTextCredential);
        }else {
            credential = new PasswordCredential(userId, plainTextCredential);
        }

        entitlementService.setUserCredential(userId, credential);
    }

    private void executeAddRoleToUserCommand(CommandParser commandParser) throws InvalidCommandException, EntityNotFoundException, EntitlementServiceException {
        //Input command format is:
        //# add role_to_user <user_id> <role>
        //add role_to_user debra admin_role
        String userId = commandParser.getNextToken("User identifier");
        String roleId = commandParser.getNextToken("Role identifier");
        commandParser.ensureTermination();

        entitlementService.addRoleToUser(userId, roleId);
    }

    private void executeAddResourceRoleToUserCommand(CommandParser commandParser) throws EntitlementServiceException, InvalidCommandException {
        //Input command format is:
        //add resource_role_to_user <user_id> <resource_role>
        //add resource_role_to_user sam house1_adult_resident
        String userId = commandParser.getNextToken("User identifier");
        String resourceRoleId = commandParser.getNextToken("Resource role identifier");
        commandParser.ensureTermination();

        entitlementService.addResourceRoleToUser(userId, resourceRoleId);
    }
}
