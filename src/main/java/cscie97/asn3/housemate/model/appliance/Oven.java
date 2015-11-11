package cscie97.asn3.housemate.model.appliance;

import cscie97.asn3.housemate.model.Appliance;
import cscie97.asn3.housemate.model.Room;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

/**
 * This class is used to represent Oven device in the HouseMate automation system.
 */
public class Oven extends Appliance{

    public static final String DEVICE_TYPE = "oven";

    public static final String OFF = "OFF";

    public static final String ON = "ON";

    public static final String POWER = "POWER";

    public static final String TEMPERATURE = "TEMPERATURE";

    public static final String TIME_TO_COOK = "TIME-TO-COOK";

    public Oven(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

        if(POWER.equalsIgnoreCase(statusKey) &&
                (ON.equalsIgnoreCase(statusValue) || OFF.equalsIgnoreCase(statusValue))){
            return;
        }

        if(TEMPERATURE.equalsIgnoreCase(statusKey)){
            try {
                int temperature = Integer.parseInt(statusValue);
                if(temperature < 0){
                    throw new InvalidStatusException(this, statusKey, statusValue);
                }
                return;
            }catch (NumberFormatException e){
                throw new InvalidStatusException(this, statusKey, statusValue);
            }
        }

        if(TIME_TO_COOK.equalsIgnoreCase(statusKey)){
            try {
                int timeToCook = Integer.parseInt(statusValue);
                if(timeToCook < 0){
                    throw new InvalidStatusException(this, statusKey, statusValue);
                }
                return;
            }catch (NumberFormatException e){
                throw new InvalidStatusException(this, statusKey, statusValue);
            }
        }

        throw new InvalidStatusException(this, statusKey, statusValue);
    }
}
