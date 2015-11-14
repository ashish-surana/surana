package cscie97.asn3.housemate.entitlement;

import cscie97.asn3.housemate.entitlement.exception.AuthenticationException;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * This class represents user credential in HouseMate automation system.
 */
public abstract class Credential {

    private final static int ITERATION_NUMBER = 1000;

    private final String encryptedCredential;

    private final String salt;

    private final String userId;

    public Credential(String userId, String plainTextCredential) throws EntitlementServiceException {
        assert userId != null && !"".equals(userId)
                : "User id cannot be null or empty string";

        assert plainTextCredential != null && !"".equals(plainTextCredential)
                : userId + " cannot be null or empty string";

        this.userId = userId;
        this.salt = generateSalt();
        this.encryptedCredential = encryptCredential(plainTextCredential, salt);
    }

    private String generateSalt() throws EntitlementServiceException {
        // Uses a secure Random not a simple Random
        SecureRandom random = null;
        // Salt generation 64 bits long
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bSalt = new byte[8];
            random.nextBytes(bSalt);
            return byteToBase64(bSalt);
        } catch (NoSuchAlgorithmException e) {
            throw new EntitlementServiceException(e);
        }
    }

    private String encryptCredential(String plainTextCredential, String salt) throws EntitlementServiceException {
        // Digest computation
        byte[] bDigest = new byte[0];
        try {
            bDigest = getHash(ITERATION_NUMBER,plainTextCredential, base64ToByte(salt));
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
     * @param iterationNb int The number of iterations of the algorithm
     * @param password String The password to encrypt
     * @param salt byte[] The salt
     * @return byte[] The digested password
     * @throws NoSuchAlgorithmException If the algorithm doesn't exist
     */
    private byte[] getHash(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-2");
        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < iterationNb; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        return input;
    }

    /**
     * From a base 64 representation, returns the corresponding byte[]
     * @param data String The base64 representation
     * @return byte[]
     * @throws IOException
     */
    private static byte[] base64ToByte(String data) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }

    /**
     * From a byte[] returns a base 64 representation
     * @param data byte[]
     * @return String
     * @throws IOException
     */
    private static String byteToBase64(byte[] data){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public void authenticate(String plainTextCredential) throws EntitlementServiceException, AuthenticationException {
        String proposedCredential = encryptCredential(plainTextCredential, salt);
        if(!encryptedCredential.equals(proposedCredential)){
            throw new AuthenticationException(userId, "Invalid user id or password");
        }
    }
}
