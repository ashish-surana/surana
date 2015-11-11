package cscie97.asn3.housemate.model.appliance;

import cscie97.asn3.housemate.model.Appliance;
import cscie97.asn3.housemate.model.Room;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

/**
 * This class is used to represent Light device in the HouseMate automation system.
 */
public class Light extends Appliance{

    public static final String DEVICE_TYPE = "light";

    public static final String MODE = "MODE";

    public static final String ON = "ON";

    public static final String OFF = "OFF";

    public static final String INTENSITY = "INTENSITY";

    public static final Integer LOW_INTENSITY = 20;

    public Light(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

        if(INTENSITY.equalsIgnoreCase(statusKey)){
            try {
                int intensity = Integer.parseInt(statusValue);
                if(intensity < 0 || intensity > 100){
                    throw new InvalidStatusException(this, statusKey, statusValue);
                }
                return;
            }catch (NumberFormatException e){
                throw new InvalidStatusException(this, statusKey, statusValue);
            }
        }

        if(MODE.equalsIgnoreCase(statusKey) &&
                (ON.equalsIgnoreCase(statusValue) || OFF.equalsIgnoreCase(statusValue))){
            return;
        }

        throw new InvalidStatusException(this, statusKey, statusValue);
    }
}
