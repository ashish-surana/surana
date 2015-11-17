package cscie97.asn4.housemate.model;

import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;

/**
 * Sensor is a type of IoT device in the HouseMate model service.
 * Types of sensors are defined in the {@link cscie97.asn4.housemate.model.support.DeviceType}  enum.
 */
public abstract class Sensor extends Device {

    private final String sensorType;

    public Sensor(String identifier, String sensorType, Room location) {
        super(identifier, sensorType, location);

        assert sensorType != null : "Sensor type cannot be null";
        this.sensorType = sensorType;
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException{
        assert statusKey != null && !"".equalsIgnoreCase(statusKey) : "Status key for '"+sensorType+"' cannot be null or empty string";

        assert statusValue != null && !"".equalsIgnoreCase(statusValue) : "Status value for '"+ sensorType +"' cannot be null or empty string";
    }

    @Override
    protected void showAdditionalDetails() {
        System.out.println("Device type is: '" + sensorType + "'");
    }

}
