package cscie97.asn4.housemate.model;

import cscie97.asn4.housemate.model.support.OccupantActivityStatus;
import cscie97.asn4.housemate.model.support.OccupantType;

/**
 * This class represents an occupant in the HouseMate model service. An occupant can
 * have multiple {@link cscie97.asn4.housemate.model.support.OccupantType} in various houses.
 */
public class Occupant extends Entity{

    private OccupantType type;

    private Room currentLocation;

    private OccupantActivityStatus status;

    public Occupant(String occupantId, OccupantType occupantType) {
        super(occupantId);

        this.type = occupantType;
    }

    public Room getCurrentLocation() {
        return currentLocation;
    }

    public OccupantActivityStatus getStatus() {
        return status;
    }

    public OccupantType getType() {
        return type;
    }

    public void setCurrentLocation(Room room){
        this.currentLocation = room;
    }

    public void showConfiguration() {
        System.out.print("Occupant name: '" + getIdentifier() + "', type: '" + type.name().toLowerCase() + "', ");

        String status = getStatus() == null ? "unknown" : getStatus().toString();
        System.out.print("status: '" + status + "', ");

        String currentLocation = getCurrentLocation() == null ? "unknown" : getCurrentLocation().getIdentifier();
        System.out.println("current location: '" + currentLocation + "'");
    }

    public void setStatus(OccupantActivityStatus status) {
        this.status = status;
    }
}
