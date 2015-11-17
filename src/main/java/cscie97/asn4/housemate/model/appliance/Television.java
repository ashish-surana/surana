package cscie97.asn4.housemate.model.appliance;

import cscie97.asn4.housemate.model.Appliance;
import cscie97.asn4.housemate.model.Room;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;

/**
 * This class is used to represent Television device in the HouseMate automation system.
 */
public class Television extends Appliance{

    public static final String DEVICE_TYPE = "tv";

    public Television(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

        if("channel".equalsIgnoreCase(statusKey)){
            return;
        }

        if("power".equalsIgnoreCase(statusKey) &&
                ("ON".equalsIgnoreCase(statusValue) || "OFF".equalsIgnoreCase(statusValue))){
            return;
        }

        if("volume".equalsIgnoreCase(statusKey) &&
                ("LOWER".equalsIgnoreCase(statusValue) || "HIGHER".equalsIgnoreCase(statusValue)
                        || "MUTE".equalsIgnoreCase(statusValue))){
            return;
        }

        throw new InvalidStatusException(this, statusKey, statusValue);
    }
}
