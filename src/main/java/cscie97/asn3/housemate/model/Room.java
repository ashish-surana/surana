package cscie97.asn3.housemate.model;

import cscie97.asn3.housemate.model.support.RoomType;

/**
 * This class models a room in the HouseMate model service.
 */
public class Room extends Entity{

    private final RoomType type;
    private final Integer floorNumber;

    public Room(String identifier, Integer floorNum, RoomType type) {
        super(identifier);

        assert floorNum != null : "Floor number cannot be null";
        assert type != null : "Room type cannot be null";

        this.floorNumber = floorNum;
        this.type = type;
    }

    public void showConfiguration(String houseId) {
        System.out.println(
                "House: '" + houseId + "', room: " + getIdentifier() +
                        ", type: " + type.name().toLowerCase().toString());
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }
}
