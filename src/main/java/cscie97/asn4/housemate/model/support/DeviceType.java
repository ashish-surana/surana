package cscie97.asn4.housemate.model.support;

import cscie97.asn4.housemate.model.Appliance;
import cscie97.asn4.housemate.model.Sensor;
import cscie97.asn4.housemate.model.appliance.*;
import cscie97.asn4.housemate.model.sensor.Camera;
import cscie97.asn4.housemate.model.sensor.SmokeDetector;
import cscie97.asn4.housemate.model.service.exception.InvalidEntityTypeException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class maintains registry of various types of sensors, and devices supported in the HouseMate model service.
 * This registry can be primarily used to verify whether a given device type is a valid sensor or appliance.
 * This registry can also be used to retrieve Class for given sensor or appliance type.
 */
public class DeviceType {

    private static final Map<String, Class<? extends Sensor>> sensorTypes = new HashMap<>();

    private static final Map<String, Class<? extends Appliance>> applianceTypes = new HashMap<>();

    /**
     * Denotes whether the sensorTypes, applianceTypes maps have been initialized.
     */
    private static boolean isInitialized = false;

    /**
     * Returns true if and only if, the given string represents a valid sensor type.
     */
    public static boolean isValidSensorType(String sensorTypeString){
        assert sensorTypeString != null && !"".equalsIgnoreCase(sensorTypeString) : "Sensor type cannot be null or empty string";

        if(!isInitialized){
            initialize();
        }

        return sensorTypes.containsKey(sensorTypeString);
    }


    /**
     * Returns true if and only if, the given string represents a valid appliance type.
     */
    public static boolean isValidApplianceType(String applianceTypeString){
        assert applianceTypeString != null && !"".equalsIgnoreCase(applianceTypeString) :
                "Appliance type cannot be null or empty string";

        if(!isInitialized){
            initialize();
        }

        return applianceTypes.containsKey(applianceTypeString);
    }


    /**
     * This method initializes the sensor and appliance types recognized in the HouseMate
     * automation system.
     */
    private static void initialize() {
        sensorTypes.put(Camera.DEVICE_TYPE, Camera.class);
        sensorTypes.put(SmokeDetector.DEVICE_TYPE, SmokeDetector.class);

        applianceTypes.put(Light.DEVICE_TYPE, Light.class);
        applianceTypes.put(Television.DEVICE_TYPE, Television.class);
        applianceTypes.put(Pandora.DEVICE_TYPE, Pandora.class);
        applianceTypes.put(Refrigerator.DEVICE_TYPE, Refrigerator.class);
        applianceTypes.put(Door.DEVICE_TYPE, Door.class);
        applianceTypes.put(Window.DEVICE_TYPE, Window.class);
        applianceTypes.put(Thermostat.DEVICE_TYPE, Thermostat.class);
        applianceTypes.put(Ava.DEVICE_TYPE, Ava.class);
        applianceTypes.put(Oven.DEVICE_TYPE, Oven.class);

        isInitialized = true;
    }

    /**
     * This method returns the Class for given sensor type.
     * @param sensorTypeString
     * @return
     * @throws InvalidEntityTypeException if given sensorType is not recognized.
     */
    public static Class getSensorClass(String sensorTypeString) throws InvalidEntityTypeException {
        if(!isValidSensorType(sensorTypeString)){
            throw new InvalidEntityTypeException("Unrecognized sensor type: '"+ sensorTypeString + "'", sensorTypeString);
        }

        return sensorTypes.get(sensorTypeString);
    }

    /**
     * This method returns the Class for given appliance type.
     * @param applianceTypeString
     * @return
     * @throws InvalidEntityTypeException if given applianceType is not recognized.
     */
    public static Class getApplianceClass(String applianceTypeString) throws InvalidEntityTypeException {
        if(!isValidApplianceType(applianceTypeString)){
            throw new InvalidEntityTypeException("Unrecognized appliance type: '"+ applianceTypeString + "'", applianceTypeString);
        }

        return applianceTypes.get(applianceTypeString);
    }

}
