package cscie97.asn3.housemate.model.sensor.factory;

import cscie97.asn3.housemate.model.Appliance;
import cscie97.asn3.housemate.model.Room;
import cscie97.asn3.housemate.model.Sensor;
import cscie97.asn3.housemate.model.service.exception.InvalidEntityTypeException;
import cscie97.asn3.housemate.model.support.DeviceType;

import java.lang.reflect.Constructor;

/**
 * A factory for creating sensors and appliances of various types.
 */
public class DeviceFactory {

    /**
     * This method creates a new Sensor device using the given input parameters.
     */
    public static Sensor newSensor(String sensorId, String sensorType, Room room) throws InvalidEntityTypeException {
        try {
            Constructor<Sensor> constructor = DeviceType.getSensorClass(sensorType).getConstructor(String.class, Room.class);
            Sensor sensor = constructor.newInstance(sensorId, room);
            return sensor;
        } catch (ReflectiveOperationException e) {
            throw new InvalidEntityTypeException("Unrecognized sensor type: '"+ sensorType + "'", sensorType);
        }
    }

    /**
     * This method creates a new Appliance device using the given input parameters.
     */
    public static Appliance newAppliance(String applianceId, String applianceType, Room room) throws InvalidEntityTypeException {
        try {
            Constructor<Appliance> constructor = DeviceType.getApplianceClass(applianceType).getConstructor(String.class, Room.class);
            Appliance appliance = constructor.newInstance(applianceId, room);
            return appliance;
        } catch (ReflectiveOperationException e) {
            throw new InvalidEntityTypeException("Unrecognized appliance type: '"+ applianceType + "'", applianceType);
        }
    }

}
