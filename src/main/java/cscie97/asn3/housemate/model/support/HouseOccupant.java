package cscie97.asn3.housemate.model.support;

import cscie97.asn3.housemate.model.Entity;
import cscie97.asn3.housemate.model.Occupant;

/**
 * This class represents the association relationship between an Occupant and a House.
 */
public class HouseOccupant extends Entity{

    private OccupancyStatus status;

    private Occupant occupant;

    public HouseOccupant(Occupant occupant, OccupancyStatus status) {
        super(occupant.getIdentifier());
        this.occupant = occupant;
        this.status = status;
    }

    public OccupancyStatus getStatus() {
        return status;
    }

    public void setStatus(OccupancyStatus status) {
        this.status = status;
    }

    public Occupant getOccupant() {
        return occupant;
    }

    public void showConfiguration() {
        System.out.println("Occupant: '" + occupant.getIdentifier() + "' is a '"+ status.name().toLowerCase() +"'.");
    }
}
