package cscie97.asn3.housemate.entitlement;

import cscie97.asn3.housemate.entitlement.exception.AuthenticationException;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.visitor.EntitlementVisitor;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * This class represents user credential in HouseMate automation system.
 */
public abstract class Credential extends Entity{

    private final String encryptedCredential;

    private final String userId;

    public Credential(String userId, String plainTextCredential) throws EntitlementServiceException {
        //Assign a randomly generated unique id to each credential instance
        super(UUID.randomUUID().toString());
        assert userId != null && !"".equals(userId)
                : "User id cannot be null or empty string";

        assert plainTextCredential != null && !"".equals(plainTextCredential)
                : userId + " cannot be null or empty string";

        this.userId = userId;
        this.encryptedCredential = encryptCredential(plainTextCredential);
    }

    private String encryptCredential(String plainTextCredential) throws EntitlementServiceException {
        // Digest computation
        byte[] bDigest = new byte[0];
        try {
            bDigest = getHash(plainTextCredential);
        } catch (NoSuchAlgorithmException e) {
            throw new EntitlementServiceException(e);
        } catch (IOException e) {
            throw new EntitlementServiceException(e);
        }
        return byteToBase64(bDigest);

    }

    /**
     * From a password, a number of iterations and a salt,
     * returns the corresponding digest
     * @param plaintTextCredential String The password to encrypt
     * @return byte[] The digested password
     * @throws NoSuchAlgorithmException If the algorithm doesn't exist
     */
    private byte[] getHash(String plaintTextCredential) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();

        byte[] input = digest.digest(plaintTextCredential.getBytes("UTF-8"));
        return input;
    }

    /**
     * From a byte[] returns a base 64 representation
     * @param data byte[]
     * @return String
     */
    private static String byteToBase64(byte[] data){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  Credential)){
            return false;
        }

        Credential otherCred = (Credential) obj;

        //Two credentials are equal if they belong to the same user,
        //and their encrypted values are equal.
        return (this.getUserId().equals(otherCred.getUserId())
            && this.encryptedCredential.equals(otherCred.encryptedCredential)
        );
    }

    @Override
    public void acceptVisitor(EntitlementVisitor visitor) {
        visitor.visitCredential(this);
    }
}
