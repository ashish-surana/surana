package cscie97.asn4.housemate.entitlement;

import cscie97.asn4.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn4.housemate.entitlement.visitor.EntitlementVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * An instance of this class represents a user in HouseMate automation system.
 * Each occupant of HouseMate automation system has a corresponding user.
 * However, there can be a user which is not an occupant.
 */
public class User extends Entity{

    private final String name;

    private Credential password;

    private Credential voicePrint;

    private AccessToken accessToken;

    private final Map<String, ResourceRole> resourceRoles;

    public User(String identifier, String name){
        super(identifier);

        assert name != null && !"".equals(name) : "User name cannot be null or empty string";

        this.name = name;
        this.resourceRoles = new HashMap<>();
    }

    /**
     * @return the name of this user.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the encrypted password credential for this user. Or null if no password
     * has been set.
     */
    public Credential getPassword() {
        return password;
    }

    /**
     * @return the encrypted voice print credential for this user.
     * Or null if no voice print has been set.
     */
    public Credential getVoicePrint() {
        return voicePrint;
    }

    /**
     * This method sets the given password for this user.
     * Existing password is removed.
     * @param password
     */
    public void setPassword(Credential password) {
        this.password = password;
    }

    /**
     * * This method sets the given voice print for this user.
     * Existing voice print is removed.
     * @param voicePrint
     */
    public void setVoicePrint(Credential voicePrint) {
        this.voicePrint = voicePrint;
    }

    /**
     * This method adds the given resource role to this user, thereby giving this
     * user a role for operating on the associated resource.
     * @param resourceRole
     * @throws EntitlementServiceException
     */
    public void addResourceRole(ResourceRole resourceRole) throws EntitlementServiceException {
        if(resourceRole == null){
            throw new EntitlementServiceException("ResourceRole cannot be null");
        }

        resourceRoles.put(resourceRole.getIdentifier(), resourceRole);
    }

    /**
     * This method returns a modifiable map of resource role identifiers,
     * and resource roles associated to this user.
     * @return
     */
    public Map<String, ResourceRole> getResourceRoles() {
        return resourceRoles;
    }

    /**
     * @return the access token associated with this user. Or null if no access token
     * is currently associated.
     */
    public AccessToken getAccessToken() {
        return accessToken;
    }

    /**
     * Sets given access token for this user.
     * @param accessToken
     */
    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void acceptVisitor(EntitlementVisitor visitor) {
        visitor.visitUser(this);
    }
}
