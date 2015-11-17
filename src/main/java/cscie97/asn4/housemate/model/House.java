package cscie97.asn4.housemate.model;

import cscie97.asn4.housemate.model.sensor.factory.DeviceFactory;
import cscie97.asn4.housemate.model.service.exception.InvalidEntityTypeException;
import cscie97.asn4.housemate.model.support.*;
import cscie97.asn4.housemate.model.service.exception.EntityExistsException;
import cscie97.asn4.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn4.housemate.model.service.exception.InvalidStatusException;

import java.util.*;

/**
 * This class represents a house in the HouseMate model service.
 * This class has a composition relationship with rooms, and devices.
 * It also contains a map of various occupants in the house.
 */
public class House extends Entity{

    private final Map<String, Room> rooms;

    //This provides us an easy way to show configuration of the rooms, floor by floor.
    private final Map<Integer, Set<String>> floorToRoomMap;

    private final Map<String, HouseOccupant> houseOccupants;

    private final Map<String, Device> devices;

    public House(String identifier) {
        super(identifier);

        this.floorToRoomMap = new TreeMap<Integer, Set<String>>();
        this.houseOccupants = new HashMap<String, HouseOccupant>();
        this.rooms = new HashMap<String, Room>();
        this.devices = new HashMap<>();
    }


    public void showConfiguration() {
        System.out.println("------------------------------");
        System.out.println("------------------------------");
        System.out.println("House: " + getIdentifier());
        System.out.println("------------------------------");

        floorToRoomMap.forEach((floorNum, roomIdentifiers) ->
                        showFloorConfiguration(floorNum, roomIdentifiers)
        );

        if(houseOccupants.isEmpty()){
            System.out.println("------------------------------");
            System.out.println("No occupants in the house.");
            System.out.println("------------------------------");
        }

        houseOccupants.forEach((occupantId, houseOccupant) ->
                        houseOccupant.showConfiguration()
        );

        if(devices.isEmpty()){
            System.out.println("------------------------------");
            System.out.println("No devices in the house.");
            System.out.println("------------------------------");
        }

        devices.forEach((deviceId, device) ->
                        device.showStatus()
        );

        System.out.println("------------------------------");
        System.out.println("------------------------------");
    }

    private void showFloorConfiguration(Integer floorNum, Set<String> roomIdentifiers) {
        System.out.println("Floor number: " + floorNum);
        System.out.println("------------------------------");
        roomIdentifiers.forEach((roomId) ->
            rooms.get(roomId).showConfiguration(getIdentifier())
        );
        System.out.println("------------------------------");

    }

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public Room defineRoom(Integer floorNum, String roomId, RoomType roomType) throws EntityExistsException {
        if(getRoom(roomId) != null){
            throw new EntityExistsException(getRoom(roomId));
        }

        Room room = new Room(roomId, floorNum, roomType);
        rooms.put(roomId, room);

        //get all the rooms on given floor
        Set<String> floorRooms = floorToRoomMap.get(floorNum);
        if(floorRooms == null){
            floorRooms = new HashSet<>();
            floorToRoomMap.put(floorNum, floorRooms);
        }
        floorRooms.add(roomId);

        return room;
    }

    public HouseOccupant addOccupant(Occupant occupant) {
        assert occupant!= null : "Occupant cannot be null";

        if(houseOccupants.containsKey(occupant.getIdentifier())){
            return houseOccupants.get(occupant.getIdentifier());
        }

        HouseOccupant houseOccupant = new HouseOccupant(occupant, OccupancyStatus.Resident);
        houseOccupants.put(occupant.getIdentifier(), houseOccupant);

        return houseOccupant;
    }

    public void defineSensor(String roomId, String sensorType, String sensorId) throws EntityNotFoundException, EntityExistsException, InvalidEntityTypeException {
        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert sensorType != null && !"".equals(sensorType) :
                "Sensor type cannot be null or empty string";

        assert sensorId != null && !"".equals(sensorId) :
                "Sensor identifier cannot be null or empty string";

        Room room = rooms.get(roomId);
        if(room == null){
            throw new EntityNotFoundException(roomId);
        }

        Device device = devices.get(sensorId);
        if(device != null){
            throw new EntityExistsException(device);
        }

        device = DeviceFactory.newSensor(sensorId, sensorType, room);
        devices.put(sensorId, device);
    }

    public void defineAppliance(String roomId, String applianceType, String applianceId) throws EntityNotFoundException, EntityExistsException, InvalidEntityTypeException {
        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert applianceType != null :
                "Appliance type cannot be null";

        assert applianceId != null && !"".equals(applianceId) :
                "Appliance identifier cannot be null or empty string";

        Room room = rooms.get(roomId);
        if(room == null){
            throw new EntityNotFoundException(roomId);
        }

        Device device = devices.get(applianceId);
        if(device != null){
            throw new EntityExistsException(device);
        }

        device = DeviceFactory.newAppliance(applianceId, applianceType, room);
        devices.put(applianceId, device);
    }

    public void setDeviceStatus(String deviceId, String statusKey, String statusValue) throws EntityNotFoundException, InvalidStatusException {
        assert deviceId != null && !"".equals(deviceId) :
                "Device identifier cannot be null or empty string";

        assert statusKey != null && !"".equals(statusKey) :
                "Status key cannot be null or empty string";

        assert statusValue != null && !"".equals(statusValue) :
                "Status value cannot be null or empty string";

        Device device = devices.get(deviceId);
        if(device == null){
            throw new EntityNotFoundException(deviceId);
        }

        device.setStatus(statusKey, statusValue);
    }

    public Device getDevice(String deviceId) throws EntityNotFoundException {
        assert deviceId != null && !"".equals(deviceId) : "Device id cannot be null or empty string";

        Device device = devices.get(deviceId);

        if(device == null){
            throw new EntityNotFoundException(deviceId);
        }

        return device;
    }

    public Set<String> getDeviceByRoomAndType(String roomId, String deviceType) throws EntityNotFoundException {
        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert deviceType != null && !"".equals(deviceType) : "Device type cannot be null or empty string";

        Room room = rooms.get(roomId);
        if(room == null){
            throw new EntityNotFoundException(roomId);
        }

        Set<String> deviceIds = new HashSet<>();

        devices.forEach((deviceId, device) -> {
            if(deviceType.equals(device.getType()) && room.equals(device.getLocation())){
                deviceIds.add(deviceId);
            }
        });

        return deviceIds;
    }

    public Set<String> getDeviceByType(String deviceType) {
        assert deviceType != null && !"".equals(deviceType) : "Device type cannot be null or empty string";
        Set<String> deviceIds = new HashSet<>();

        devices.forEach((deviceId, device) -> {
            if(deviceType.equals(device.getType())){
                deviceIds.add(deviceId);
            }
        });

        return deviceIds;
    }

    public void removeOccupant(Occupant occupant) {
        assert occupant!= null : "Occupant cannot be null";
        houseOccupants.remove(occupant.getIdentifier());
    }

    public Occupant getOccupant(String occupantId) {
        HouseOccupant houseOccupant = houseOccupants.get(occupantId);

        if(houseOccupant == null){
            return null;
        }

        return houseOccupant.getOccupant();
    }
}
