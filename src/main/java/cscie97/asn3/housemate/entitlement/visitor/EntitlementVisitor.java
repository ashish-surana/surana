package cscie97.asn3.housemate.entitlement.visitor;

import cscie97.asn3.housemate.entitlement.*;

/**
 * This interface follows visitor pattern. Its methods are designed to facilitate implementing classes in running their algorithm
 * on all entitlement domain objects. Entitlement domain objects are user, resource, role etc.
 */
public interface EntitlementVisitor {

    public void visitUser(User user);

    public void visitPermission(Permission permission);

    public void visitResourceRole(ResourceRole resourceRole);

    public void visitRole(Role role);

    public void visitAccessToken(AccessToken accessToken);

    public void visitResource(Resource resource);
}
