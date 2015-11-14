package cscie97.asn3.housemate.entitlement.credential;

import cscie97.asn3.housemate.entitlement.Credential;

/**
 * This class represents a password based credential in HouseMate automation system.
 */
public class PasswordCredential extends Credential {

    public PasswordCredential(String plainTextPassword){
        super("password", plainTextPassword);
    }
}
