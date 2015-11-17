package cscie97.asn4.housemate.model.appliance;

import cscie97.asn4.housemate.model.Appliance;
import cscie97.asn4.housemate.model.Room;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;

/**
 * This class is used to represent Thermostat device in the HouseMate automation system.
 */
public class Thermostat extends Appliance{

    public static final String DEVICE_TYPE = "thermostat";

    public static final String TEMPERATURE = "TEMPERATURE";

    public static final Integer OPTIMUM_TEMPERATURE = 65;

    public static final Integer STANDBY_TEMPERATURE = 45;

    public Thermostat(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

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

        throw new InvalidStatusException(this, statusKey, statusValue);
    }
}
