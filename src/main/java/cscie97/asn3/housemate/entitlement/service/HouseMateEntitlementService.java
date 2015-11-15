package cscie97.asn3.housemate.entitlement.service;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.credential.PasswordCredential;
import cscie97.asn3.housemate.entitlement.credential.VoicePrintCredential;
import cscie97.asn3.housemate.entitlement.exception.AuthenticationException;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.EntityExistsException;
import cscie97.asn3.housemate.entitlement.exception.EntityNotFoundException;

/**
 *
 */
public interface HouseMateEntitlementService {

    public AccessToken login(VoicePrintCredential voiceCredential) throws EntitlementServiceException, EntityNotFoundException, AuthenticationException;

    public AccessToken login(PasswordCredential passwordCredential) throws EntitlementServiceException, EntityNotFoundException, AuthenticationException;

    public AccessToken getAdminAccessToken();

    public void createPermission(String permissionId, String permissionName, String permissionDescription) throws EntityExistsException;

    public void createRole(String identifier, String name, String description) throws EntityExistsException;

    public void addEntitlementToRole(String roleId, String entitlementId) throws EntitlementServiceException;

    public void createUser(String identifier, String name) throws EntitlementServiceException;
}
