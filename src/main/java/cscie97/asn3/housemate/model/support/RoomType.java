package cscie97.asn3.housemate.model.support;

/**
 * An enumeration of various types of rooms supported in the HouseMate model service.
 */
public enum RoomType {
    Kitchen("kitchen"), Closet("closet"), LivingRoom("living_room"),
    DiningRoom("dining_room"), Hallway("hallway"), BedRoom("bedroom"),
    FamilyRoom("family_room"), Garage("garage"), BathRoom("bathroom");

    public static RoomType getEnum(String roomTypeString){
        assert roomTypeString != null && !"".equals(roomTypeString) : "Room type cannot be null or empty string";

        for (RoomType roomType : RoomType.values()) {
            if(roomType.type.equalsIgnoreCase(roomTypeString)){
                return roomType;
            }
        }

        return null;
    }

    private String type;

    RoomType(String type){
        this.type = type;
    }
}
