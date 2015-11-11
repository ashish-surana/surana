package cscie97.asn3.housemate.model.sensor;

import cscie97.asn3.housemate.model.Room;
import cscie97.asn3.housemate.model.Sensor;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

/**
 * This class is used to model a camera in the HouseMate automation system.
 */
public class Camera extends Sensor{

    public static final String DEVICE_TYPE = "camera";

    public static final String OCCUPANT_DETECTED = "OCCUPANT_DETECTED";

    public static final String OCCUPANT_LEAVING = "OCCUPANT_LEAVING";

    public static final String OCCUPANT_RESTING = "OCCUPANT_RESTING";

    public static final String OCCUPANT_ACTIVE = "OCCUPANT_ACTIVE";


    public Camera(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

        if(OCCUPANT_DETECTED.equalsIgnoreCase(statusKey) ||
                OCCUPANT_LEAVING.equalsIgnoreCase(statusKey) ||
                OCCUPANT_ACTIVE.equalsIgnoreCase(statusKey) ||
                OCCUPANT_RESTING.equalsIgnoreCase(statusKey)){
            return;
        }

        throw new InvalidStatusException(this, statusKey, statusValue);
    }

}
