package cscie97.asn4.housemate.entitlement.credential;

import cscie97.asn4.housemate.entitlement.Credential;
import cscie97.asn4.housemate.entitlement.exception.EntitlementServiceException;

/**
 * This class represents a password based credential in HouseMate automation system.
 */
public class PasswordCredential extends Credential {

    public PasswordCredential(String userId, String plainTextPassword) throws EntitlementServiceException {
        super(userId, plainTextPassword);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PasswordCredential)){
            return false;
        }

        return super.equals(obj);
    }
}
