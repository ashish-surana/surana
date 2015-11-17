package cscie97.asn4.housemate.entitlement.visitor.impl;

import cscie97.asn4.housemate.entitlement.*;
import cscie97.asn4.housemate.entitlement.credential.PasswordCredential;
import cscie97.asn4.housemate.entitlement.visitor.EntitlementVisitor;

/**
 * This class visits each entitlement object, and prints its configuration.
 */
public class PrintConfigurationVisitor implements EntitlementVisitor {


    private String currentUserId;
    private String currentRoleId;

    @Override
    public void visitUser(User user) {
        System.out.println("**********************************************************************");
        System.out.printf("Showing user configuration for user id: '%s', user name: '%s' \n", user.getIdentifier(), user.getName());
        System.out.println("**********************************************************************");

        currentUserId = user.getIdentifier();

        if (user.getAccessToken() == null) {
            System.out.println("User has no associated access token.");
            System.out.println();
        }else{
            user.getAccessToken().acceptVisitor(this);
        }

        if (user.getVoicePrint() == null) {
            System.out.println("No voice_print has been set for this user.");
            System.out.println();
        }else{
            user.getVoicePrint().acceptVisitor(this);
        }

        if (user.getPassword() == null) {
            System.out.println("No password has been set for this user.");
            System.out.println();
        }else{
            user.getPassword().acceptVisitor(this);
        }

        if (user.getResourceRoles().isEmpty()){
            System.out.println("User has no associated roles or resource roles.");
            System.out.println();
        }else {
            user.getResourceRoles().forEach((resourceRoleId, resourceRole) ->{
                resourceRole.acceptVisitor(this);
            });
        }

        System.out.println("**********************************************************************");
        System.out.println("End user configuration.");
        System.out.println("**********************************************************************");
        System.out.println();
    }

    @Override
    public void visitPermission(Permission permission) {
        System.out.printf("%s has %s permission associated with it. \n", currentRoleId, permission.getIdentifier());
        System.out.printf("%s has name: '%s' and description: '%s'. \n",
                permission.getIdentifier(), permission.getName(), permission.getDescription());
        System.out.println();
    }

    @Override
    public void visitResourceRole(ResourceRole resourceRole) {
        Resource resource = resourceRole.getResource();

        if(!Resource.ALL_RESOURCE_ID.equals(resource.getIdentifier())){
            System.out.printf("'%s' has '%s' resource role associated with it for resource '%s' and role '%s'. \n",
                    currentUserId, resourceRole.getIdentifier(),resource, resourceRole.getRole());
            resource.acceptVisitor(this);
            System.out.println();
        }

        resourceRole.getRole().acceptVisitor(this);
    }

    @Override
    public void visitResource(Resource resource) {
        System.out.printf("%s resource has following description: '%s'. \n",
                resource.getIdentifier(), resource.getDescription());
        System.out.println();
    }

    @Override
    public void visitRole(Role role) {
        currentRoleId = role.getIdentifier();
        System.out.printf("User %s has role: '%s'. \n", currentUserId, role.getIdentifier());
        System.out.printf("%s role has name: '%s' and description '%s'. \n",
                currentRoleId, role.getName(), role.getDescription());
        System.out.println();

        if (role.getEntitlements().isEmpty()){
            System.out.printf("%s has no other roles or permissions associated with it.", currentRoleId);
        }

        role.getEntitlements().forEach(entitlement -> {
            currentRoleId = role.getIdentifier();
            entitlement.acceptVisitor(this);
        });
    }

    @Override
    public void visitAccessToken(AccessToken accessToken) {
        System.out.printf("Access token identifier is: '%s'.\n", accessToken.getIdentifier());
        System.out.printf("Access token expiry timestamps is: '%s'.\n", accessToken.getExpiryTime());
        System.out.printf("Access token was last accessed at: '%s'.\n", accessToken.getLastAccessed());

        String status = accessToken.isExpired()? "expired" : "active";
        System.out.printf("Access token identifier is: '%s'.\n", status);
        System.out.println();
    }

    @Override
    public void visitCredential(Credential credential) {
        String credentialType = "voice_print";
        if(credential instanceof PasswordCredential){
            credentialType = "password";
        }

        System.out.printf("Credential type is: '%s' \n", credentialType);
        System.out.printf("Credential identifier is: '%s' \n", credential.getIdentifier());
        System.out.println();
    }
}
