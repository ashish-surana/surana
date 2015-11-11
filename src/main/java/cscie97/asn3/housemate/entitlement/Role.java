package cscie97.asn3.housemate.entitlement;

/**
 * Created by assurana on 11/3/2015.
 */
public class Role extends Entitlement{

    private final String name;

    private final String description;

    public Role(String identifier, String name, String description) {
        super(identifier);

        assert name!= null && !"".equals(name) : "Permission name cannot be null or empty string";
        assert description!= null && !"".equals(description) : "Permission description cannot be null or empty string";

        this.description = description;
        this.name = name;
    }
}
