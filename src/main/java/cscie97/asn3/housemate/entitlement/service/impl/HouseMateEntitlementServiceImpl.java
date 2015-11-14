package cscie97.asn3.housemate.entitlement.service.impl;

import cscie97.asn3.housemate.entitlement.User;
import cscie97.asn3.housemate.entitlement.exception.AuthenticationException;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.EntityExistsException;
import cscie97.asn3.housemate.entitlement.exception.EntityNotFoundException;
import cscie97.asn3.housemate.entitlement.service.HouseMateEntitlementService;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class HouseMateEntitlementServiceImpl implements HouseMateEntitlementService{

    private final Map<String, User> users;

    private HouseMateEntitlementServiceImpl(){
        users = new HashMap<>();
    }

    public void createUser(String userId, String userName) throws EntitlementServiceException, EntityExistsException {
        if(userId == null || "".equals(userId)){
            throw new EntitlementServiceException("User identifier cannot be null or empty string");
        }

        if(userName == null || "".equals(userName)){
            throw new EntitlementServiceException("User name cannot be null or empty string");
        }

        User user = users.get(userId);

        if(user != null){
            throw new EntityExistsException(user);
        }

        user = new User(userId, userName);
        users.put(userId, user);
    }

    public void authenticateViaVoicePrint(String userId, String plainTextVoicePrint) throws EntitlementServiceException, EntityNotFoundException, AuthenticationException {
        User user = getExistingUser(userId);

        if(user.getVoicePrint() == null){
            throw new EntityNotFoundException("Voice print for "+ userId);
        }

        user.getVoicePrint().authenticate(plainTextVoicePrint);
    }

    private User getExistingUser(String userId) throws EntitlementServiceException {
        if(userId == null || "".equals(userId)){
            throw new EntitlementServiceException("User identifier cannot be null or empty string");
        }

        User user = users.get(userId);

        if(user == null){
            throw new EntityNotFoundException(userId);
        }

        return user;
    }

}
