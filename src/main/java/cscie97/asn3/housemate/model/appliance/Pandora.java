package cscie97.asn3.housemate.model.appliance;

import cscie97.asn3.housemate.model.Appliance;
import cscie97.asn3.housemate.model.Room;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

/**
 * This class is used to represent Pandora device in the HouseMate automation system.
 */
public class Pandora extends Appliance{

    public static final String DEVICE_TYPE = "pandora";

    public Pandora(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

        if("chanel".equalsIgnoreCase(statusKey)){
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
