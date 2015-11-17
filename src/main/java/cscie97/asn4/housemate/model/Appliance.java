package cscie97.asn4.housemate.model;

import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;


/**
 * An appliance is a type of {@link cscie97.asn4.housemate.model.Device}.
 * Each appliance can have various status keys and values, depending upon
 * its type.
 */
public abstract class Appliance extends Device {

    private final String applianceType;

    public Appliance(String identifier, String applianceType, Room location){
        super(identifier, applianceType, location);

        assert applianceType != null : "Appliance type cannot be null";
        this.applianceType = applianceType;
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        assert statusKey != null && !"".equalsIgnoreCase(statusKey) : "Status key for '"+applianceType+"' cannot be null or empty string";

        assert statusValue != null && !"".equalsIgnoreCase(statusValue) : "Status value for '"+applianceType+"' cannot be null or empty string";
    }

    @Override
    protected void showAdditionalDetails() {
        System.out.println("Device type is: '" + applianceType + "'");
    }
}
