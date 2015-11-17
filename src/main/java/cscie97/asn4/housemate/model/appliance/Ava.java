package cscie97.asn4.housemate.model.appliance;

import cscie97.asn4.housemate.model.Appliance;
import cscie97.asn4.housemate.model.Room;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;

/**
 * This class is used to represent Ava device in the HouseMate automation system.
 */
public class Ava extends Appliance{

    public static final String DEVICE_TYPE = "ava";

    public static final String LISTENING = "LISTENING";

    public static final String SPEAKING = "SPEAKING";

    public Ava(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

        if (LISTENING.equalsIgnoreCase(statusKey) || SPEAKING.equalsIgnoreCase(statusKey)) {
            return;
        }

        throw new InvalidStatusException(this, statusKey, statusValue);
    }
}
