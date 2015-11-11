package cscie97.asn3.housemate.model;

import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * This abstract class provides the basic implementation of an IoT (Internet of Things) device
 * in the HouseMate model service.
 */
public abstract class Device extends Entity{

    private final Room location;

    private final Map<String, String> statusMap;

    private final String type;

    public Device(String identifier, String type, Room location) {
        super(identifier);

        assert type != null && !"".equals(type) : "Device type cannot be null or empty string";

        assert location!= null : "Device location cannot be null";

        this.type = type;
        this.location = location;
        this.statusMap = new HashMap<String, String>();
    }

    protected abstract void validateStatus(String statusKey, String statusValue) throws InvalidStatusException;

    public void setStatus(String statusKey, String statusValue) throws InvalidStatusException {
        assert statusKey != null && !"".equals(statusKey) :
                "Status key cannot be null or empty string";

        assert statusValue != null && !"".equals(statusValue) :
                "Status value cannot be null or empty string";

        statusKey = statusKey.toUpperCase();

        validateStatus(statusKey, statusValue);
        this.statusMap.put(statusKey, statusValue);
    }

    public String getStatus(String statusKey){
        assert statusKey!= null;
        assert !statusKey.equals("");

        statusKey = statusKey.toUpperCase();

        return this.statusMap.get(statusKey);
    }

    public Room getLocation() {
        return location;
    }

    public final void showStatus() {
        System.out.println("Showing status(key value) for device with id: '" + getIdentifier()
                + "', located in room: '"+ location.getIdentifier()+ "'");
        statusMap.forEach((statusKey, statusValue) ->
            System.out.println(statusKey + " " + statusValue)
        );

        showAdditionalDetails();
        System.out.println("End of status for device with id: '"+ getIdentifier() + "'");
    }

    public final void showStatus(String statusKey) {
        System.out.println("Showing '"+statusKey+"' status for device with id: '" + getIdentifier()
                + "', located in room: '"+ location.getIdentifier()+ "'");
        System.out.println(statusKey + " " + getStatus(statusKey));
        System.out.println("End of status for device with id: '"+ getIdentifier() + "'");
    }

    protected void showAdditionalDetails(){

    }

    public String getType() {
        return type;
    }
}
