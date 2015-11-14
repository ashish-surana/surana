package cscie97.asn3.housemate.entitlement.service.impl;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.User;
import cscie97.asn3.housemate.entitlement.credential.PasswordCredential;
import cscie97.asn3.housemate.entitlement.credential.VoicePrintCredential;
import cscie97.asn3.housemate.entitlement.exception.AuthenticationException;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.EntityExistsException;
import cscie97.asn3.housemate.entitlement.exception.EntityNotFoundException;
import cscie97.asn3.housemate.entitlement.factory.EntitlementResourceFactory;
import cscie97.asn3.housemate.entitlement.factory.impl.HouseMateEntitlementResourceFactory;
import cscie97.asn3.housemate.entitlement.service.HouseMateEntitlementService;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class HouseMateEntitlementServiceImpl implements HouseMateEntitlementService{

    private static final HouseMateEntitlementService INSTANCE = new HouseMateEntitlementServiceImpl();

    private final EntitlementResourceFactory resourceFactory = new HouseMateEntitlementResourceFactory();

    private HouseMateEntitlementServiceImpl(){
    }

    public static HouseMateEntitlementService getInstance() {
        return INSTANCE;
    }

    public void createUser(String userId, String userName) throws EntitlementServiceException, EntityExistsException {
        resourceFactory.createUser(userId, userName);
    }

    public AccessToken login(VoicePrintCredential proposedCredential) throws EntitlementServiceException, EntityNotFoundException, AuthenticationException {
        if(proposedCredential == null){
            throw new EntitlementServiceException("VoicePrintCredential cannot be null");
        }

        String userId = proposedCredential.getUserId();
        User user = resourceFactory.getUser(userId);

        if(user.getVoicePrint() == null){
            throw new EntityNotFoundException("Voice print for "+ userId);
        }

        if(!user.getVoicePrint().equals(proposedCredential)){
            throw new AuthenticationException(userId, "Invalid user id or voice print");
        }

        AccessToken accessToken = resourceFactory.createAccessToken(userId);
        return accessToken;
    }

    @Override
    public AccessToken login(PasswordCredential proposedCredential) throws EntitlementServiceException, EntityNotFoundException, AuthenticationException {
        if(proposedCredential == null){
            throw new EntitlementServiceException("VoicePrintCredential cannot be null");
        }

        String userId = proposedCredential.getUserId();
        User user = resourceFactory.getUser(userId);

        if(user.getVoicePrint() == null){
            throw new EntityNotFoundException("Password for "+ userId);
        }

        if(!user.getVoicePrint().equals(proposedCredential)){
            throw new AuthenticationException(userId, "Invalid user id or password");
        }

        AccessToken accessToken = resourceFactory.createAccessToken(userId);
        return accessToken;
    }

    @Override
    public AccessToken getAdminAccessToken() {
        return resourceFactory.getAdminAccessToken();
    }

    @Override
    public void createPermission(String permissionId, String permissionName, String permissionDescription) throws EntityExistsException {
        resourceFactory.createPermission(permissionId, permissionName, permissionDescription);
    }

    @Override
    public void createRole(String identifier, String name, String description) throws EntityExistsException {
        resourceFactory.createRole(identifier, name, description);
    }
}
