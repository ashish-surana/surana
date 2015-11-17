package cscie97.asn4.housemate.model.appliance;

import cscie97.asn4.housemate.model.Appliance;
import cscie97.asn4.housemate.model.Room;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;

/**
 * This class represents a window in HouseMate automation system.
 */
public class Window extends Appliance{

    public static final String DEVICE_TYPE = "window";

    public static final String MODE = "MODE";

    public static final String OPEN = "OPEN";

    public static final String CLOSED = "CLOSED";

    public Window(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

        if(MODE.equalsIgnoreCase(statusKey) &&
                (OPEN.equalsIgnoreCase(statusValue) || CLOSED.equalsIgnoreCase(statusValue))){
            return;
        }

        throw new InvalidStatusException(this, statusKey, statusValue);
    }
}
