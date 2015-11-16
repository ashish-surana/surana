package cscie97.asn3.housemate.entitlement.visitor.impl;

import cscie97.asn3.housemate.entitlement.*;
import cscie97.asn3.housemate.entitlement.visitor.EntitlementVisitor;

import java.util.*;

/**
 * This visitor traverses a user's entitlement to determine whether the user has all claimed permissions.
 */
public class AccessVerifierVisitor implements EntitlementVisitor {

    private final String claimedResourceId;

    //Set of permissions which are yet to be verified.
    private final Set<String> unverifiedPermissions;

    //Denotes whether the given user has all claimed permissions.
    private boolean access;

    public AccessVerifierVisitor(String claimedResourceId, String[] claimedPermissions) {
        this.access = false;
        this.claimedResourceId = claimedResourceId;
        this.unverifiedPermissions = new HashSet<>(Arrays.asList(claimedPermissions));
    }

    @Override
    public void visitUser(User user) {
        if(user == null){
            return;
        }

        if(unverifiedPermissions.isEmpty()){
            access = true;
            return;
        }

        Map<String, ResourceRole> resourceRoles = user.getResourceRoles();

        for(ResourceRole resourceRole : resourceRoles.values()){
            resourceRole.acceptVisitor(this);

            //if this resource role has satisfied all the required access, then we're done verifying the access.
            if(access){
               break;
            }
        }
    }

    @Override
    public void visitResourceRole(ResourceRole resourceRole) {
        if (resourceRole == null) {
            return;
        }

        if(unverifiedPermissions.isEmpty()){
            access = true;
            return;
        }

        //If this visitor has been asked to verify access to this resource, then let's do that.
        if(resourceRole.getResource().getIdentifier().equals(claimedResourceId)){
            resourceRole.getRole().acceptVisitor(this);
        }
    }

    @Override
    public void visitResource(Resource resource) {
        //do nothing
    }

    @Override
    public void visitRole(Role role) {
        if (role == null) {
            return;
        }

        if(unverifiedPermissions.isEmpty()){
            access = true;
            return;
        }

        Set<Entitlement> entitlements = role.getEntitlements();

        for (Entitlement entitlement : entitlements) {
            entitlement.acceptVisitor(this);

            //if this entitlement has satisfied all the required access, then we're done verifying the access.
            if (access){
                break;
            }
        }
    }

    @Override
    public void visitAccessToken(AccessToken accessToken) {
        //do nothing.
    }

    @Override
    public void visitPermission(Permission permission) {
        if (permission == null) {
            return;
        }

        //Note that this visitor visits a ResourceRole if and only if resource associated with that ResourceRole is claimedResource.
        //Therefore, this visitor only visits a permission if and only if that permission is associated with that ResourceRole is claimedResource.
        unverifiedPermissions.remove(permission.getIdentifier());

        if(unverifiedPermissions.isEmpty()){
            access = true;
        }
    }

    public boolean hasAccess(){
        return access;
    }

    public Set<String> getUnverifiedPermissions() {
        return unverifiedPermissions;
    }
}
