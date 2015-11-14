package cscie97.asn3.housemate.entitlement;

import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An instance of this class represents a user in HouseMate automation system.
 * Each occupant of HouseMate automation system has a corresponding user.
 * However, there can be a user which is not an occupant.
 */
public class User extends Entity{

    private final String name;

    private Credential password;

    private Credential voicePrint;

    private final Map<String, ResourceRole> resourceRoles;

    public User(String identifier, String name){
        super(identifier);

        assert name != null && !"".equals(name) : "User name cannot be null or empty string";

        this.name = name;
        this.resourceRoles = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Credential getPassword() {
        return password;
    }

    public Credential getVoicePrint() {
        return voicePrint;
    }

    public void setPassword(Credential password) {
        this.password = password;
    }

    public void setVoicePrint(Credential voicePrint) {
        this.voicePrint = voicePrint;
    }

    public void addResourceRole(ResourceRole resourceRole) throws EntitlementServiceException {
        if(resourceRole == null){
            throw new EntitlementServiceException("ResourceRole cannot be null");
        }

        resourceRoles.put(resourceRole.getIdentifier(), resourceRole);
    }

}
