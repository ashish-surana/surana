package cscie97.asn3.housemate.model.support;

/**
 * An enumeration of various types of occupants that can be defined in the HouseMate model service.
 */
public enum OccupantType {
    Adult, Child, Pet, Unknown;

    public static OccupantType getEnum(String occupantTypeString) {
        assert occupantTypeString != null && !"".equals(occupantTypeString) : "Occupant type cannot be null or empty string";

        for (OccupantType occupantType : OccupantType.values()) {
            if(occupantType.name().equalsIgnoreCase(occupantTypeString)){
                return occupantType;
            }
        }

        return null;
    }
}
