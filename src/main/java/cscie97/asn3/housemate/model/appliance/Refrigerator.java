package cscie97.asn3.housemate.model.appliance;

import cscie97.asn3.housemate.model.Appliance;
import cscie97.asn3.housemate.model.Room;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

/**
 * This class represents a refrigerator in the HouseMate automation system.
 */
public class Refrigerator extends Appliance{

    public static final String DEVICE_TYPE = "refrigerator";

    public static final String BEER_COUNT = "BEER-COUNT";

    public static final String TEMPERATURE = "TEMPERATURE";

    public Refrigerator(String identifier, Room location) {
        super(identifier, DEVICE_TYPE, location);
    }

    @Override
    protected void validateStatus(String statusKey, String statusValue) throws InvalidStatusException {
        super.validateStatus(statusKey, statusValue);

        if(TEMPERATURE.equalsIgnoreCase(statusKey)){
            try {
                Integer.parseInt(statusValue);
                return;
            }catch (NumberFormatException e){
                throw new InvalidStatusException(this, statusKey, statusValue);
            }
        }

        if(BEER_COUNT.equalsIgnoreCase(statusKey)){
            try {
                Integer.parseInt(statusValue);
                return;
            }catch (NumberFormatException e){
                throw new InvalidStatusException(this, statusKey, statusValue);
            }
        }

        throw new InvalidStatusException(this, statusKey, statusValue);
    }
}
