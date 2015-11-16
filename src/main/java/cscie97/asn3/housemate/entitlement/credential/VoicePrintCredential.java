package cscie97.asn3.housemate.entitlement.credential;

import cscie97.asn3.housemate.entitlement.Credential;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;

/**
 * This class represents voice print credential of a user in the HouseMate automation system.
 */
public class VoicePrintCredential extends Credential{

    public VoicePrintCredential(String userId, String unencryptedVoicePrint) throws EntitlementServiceException {
        super(userId, unencryptedVoicePrint);
    }

    public VoicePrintCredential(String userId) throws EntitlementServiceException {
        super(userId, "--" + userId + "--");
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  VoicePrintCredential)){
            return false;
        }

        return super.equals(obj);
    }
}
