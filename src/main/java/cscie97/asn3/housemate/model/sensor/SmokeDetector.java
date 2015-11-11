package cscie97.asn3.housemate.model.sensor;

import cscie97.asn3.housemate.model.Room;
import cscie97.asn3.housemate.model.Sensor;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

/**
 * This class is used to model a smoke detector in the HouseMate automation system.
 */
public class SmokeDetector extends Sensor{

    public static final String DEVICE_TYPE = "smoke_detector";

    public static final String MODE = "MODE";

    public static final String FIRE = "FIRE";

    public static final String OK = "OK";

    public SmokeDetector(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

        if(MODE.equalsIgnoreCase(statusKey) &&
                (FIRE.equalsIgnoreCase(statusValue) || OK.equalsIgnoreCase(statusValue))){
            return;
        }

        throw new InvalidStatusException(this, statusKey, statusValue);
    }
}
