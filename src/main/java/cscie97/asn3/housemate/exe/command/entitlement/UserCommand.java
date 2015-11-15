package cscie97.asn3.housemate.exe.command.entitlement;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.Credential;
import cscie97.asn3.housemate.entitlement.credential.PasswordCredential;
import cscie97.asn3.housemate.entitlement.credential.VoicePrintCredential;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.exe.command.Command;
import cscie97.asn3.housemate.exe.util.CommandParser;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;

/**
 * This command should be execute to add a user to entitlement service.
 */
public class UserCommand extends Command{

    private static final String USER = "user";

    private static final String USER_CREDENTIAL = "user_credential";

    private static final String VOICE_PRINT = "voice_print";

    private static final String PASSWORD = "password";

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
        //add user_credential debra password secret
        assert commandParser !=null : "Command parser cannot be null";

        commandParser.ensureNextToken(USER_CREDENTIAL);
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
}
