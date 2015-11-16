package cscie97.asn3.housemate.model.service.impl;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.Resource;
import cscie97.asn3.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn3.housemate.entitlement.service.HouseMateEntitlementService;
import cscie97.asn3.housemate.entitlement.service.factory.HouseMateEntitlementServiceFactory;
import cscie97.asn3.housemate.model.Device;
import cscie97.asn3.housemate.model.House;
import cscie97.asn3.housemate.model.Occupant;
import cscie97.asn3.housemate.model.Room;
import cscie97.asn3.housemate.model.appliance.Ava;
import cscie97.asn3.housemate.model.listener.DeviceStatusChangeListener;
import cscie97.asn3.housemate.model.listener.support.DeviceStatusChangeEvent;
import cscie97.asn3.housemate.model.service.HouseMateModelService;
import cscie97.asn3.housemate.model.service.exception.*;
import cscie97.asn3.housemate.model.support.OccupantActivityStatus;
import cscie97.asn3.housemate.model.support.OccupantType;
import cscie97.asn3.housemate.model.support.RoomType;
import cscie97.asn3.knowledge.engine.KnowledgeGraph;
import cscie97.asn3.knowledge.engine.domain.Node;
import cscie97.asn3.knowledge.engine.domain.Predicate;
import cscie97.asn3.knowledge.engine.domain.Triple;

import java.util.*;

/**
 * This class provides a concrete implementation of HouseMateModelService.
 */
public class HouseMateModelServiceImpl implements HouseMateModelService {

    private static final HouseMateModelServiceImpl INSTANCE = new HouseMateModelServiceImpl();
    
    private static final String HOUSE_PREDICATE = "IN_HOUSE";

    private static final String ROOM_PREDICATE = "IN_ROOM";

    private static final String ACTIVITY_PREDICATE = "IS";

    private static final String HOUSE_CRUD_ACCESS_PERMISSION = "HOUSE_CRUD_ACCESS";
    private static final String OCCUPANT_CRUD_ACCESS_PERMISSION = "OCCUPANT_CRUD_ACCESS";
    private static final String _CRUD_ACCESS_PERMISSION = "_CRUD_ACCESS";
    private static final String LISTENER_CRUD_ACCESS_PERMISSION = "DEVICE_STATUS_CHANGE_LISTENER_CRUD_ACCESS";

    private final Map<String, House> houses;

    private final Map<String, Occupant> occupants;

    private final Set<DeviceStatusChangeListener> deviceStatusChangeListeners;

    private final HouseMateEntitlementService entitlementService;

    /**
     * Returns a singleton instance of this implementation.
     */
    public static HouseMateModelServiceImpl getInstance() {
        return INSTANCE;
    }

    private HouseMateModelServiceImpl() {
        this.houses = new HashMap<>();
        this.occupants = new HashMap<>();
        this.deviceStatusChangeListeners = new HashSet<>();
        entitlementService = new HouseMateEntitlementServiceFactory().getService();
    }


    @Override
    public void defineHouse(AccessToken accessToken, String houseIdentifier) throws EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        assert houseIdentifier != null && !"".equals(houseIdentifier) : "House identifier cannot be null or empty string.";

        assert !houseIdentifier.contains(" ") : "House identifier cannot contain whitespace";

        entitlementService.checkAccess(accessToken, Resource.ALL_RESOURCE_ID, HOUSE_CRUD_ACCESS_PERMISSION);

        House house = houses.get(houseIdentifier);

        if(house != null){
            throw new EntityExistsException(house);
        }

        house = new House(houseIdentifier);

        try {
            entitlementService.createResource(houseIdentifier, houseIdentifier);
            houses.put(houseIdentifier, house);
        } catch (cscie97.asn3.housemate.entitlement.exception.EntityExistsException e) {
            throw new EntityExistsException(e);
        }
    }

    @Override
    public void defineRoom(AccessToken accessToken, String houseId, String roomId, RoomType roomType, Integer floorNumber) throws EntityNotFoundException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        entitlementService.checkAccess(accessToken, houseId, HOUSE_CRUD_ACCESS_PERMISSION);

        House house = getExistingHouse(houseId);
        Room room = house.defineRoom(floorNumber, roomId, roomType);
    }

    @Override
    public void defineOccupant(AccessToken accessToken, String occupantId, OccupantType occupantType) throws EntityExistsException, EntitlementServiceException {
        assert occupantId != null && !"".equals(occupantId) : "Occupant id cannot be null or empty string";

        entitlementService.checkAccess(accessToken, Resource.ALL_RESOURCE_ID, OCCUPANT_CRUD_ACCESS_PERMISSION);

        Occupant occupant = occupants.get(occupantId);
        if(occupant != null){
            throw new EntityExistsException(occupant);
        }

        assert occupantType != null : "Occupant type cannot be null";

        occupant = new Occupant(occupantId, occupantType);
        entitlementService.createUser(occupantId, occupantId);
        occupants.put(occupantId, occupant);
    }

    @Override
    public void addOccupant(AccessToken accessToken, String occupantId, String houseId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert occupantId != null && !"".equals(occupantId) : "Insufficient arguments";
        entitlementService.checkAccess(accessToken, houseId, OCCUPANT_CRUD_ACCESS_PERMISSION, HOUSE_CRUD_ACCESS_PERMISSION);

        Occupant occupant = occupants.get(occupantId);
        if(occupant == null){
            throw new EntityNotFoundException(occupantId);
        }

        assert houseId != null && !"".equals(houseId) : "House identifier cannot be null or empty string" ;
        House house = houses.get(houseId);

        if(occupant == null){
            throw new EntityNotFoundException(houseId);
        }

        house.addOccupant(occupant);
    }


    @Override
    public void showConfiguration(AccessToken accessToken) throws AccessDeniedException, InvalidAccessTokenException {
        entitlementService.checkAccess(accessToken, Resource.ALL_RESOURCE_ID, OCCUPANT_CRUD_ACCESS_PERMISSION, HOUSE_CRUD_ACCESS_PERMISSION);
        System.out.println("Listing all the houses:");
        houses.forEach((houseId, house) ->
            house.showConfiguration()
        );

        if(houses.isEmpty()){
            System.out.println("------------------------------");
            System.out.println("------------------------------");
            System.out.println("No houses defined.");
            System.out.println("------------------------------");
            System.out.println("------------------------------");
        }

        System.out.println("End of house listing.");
        System.out.println("Listing all the occupants:");
        occupants.forEach((occupantId, occupant) ->
                        occupant.showConfiguration()
        );

        if(occupants.isEmpty()){
            System.out.println("------------------------------");
            System.out.println("------------------------------");
            System.out.println("No occupants defined.");
            System.out.println("------------------------------");
            System.out.println("------------------------------");
        }
        System.out.println("End of occupants listing.");
}

    @Override
    public void showHouseConfiguration(AccessToken accessToken, String houseId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) :
                "House identifier cannot be null or empty string";

        entitlementService.checkAccess(accessToken, houseId, OCCUPANT_CRUD_ACCESS_PERMISSION, HOUSE_CRUD_ACCESS_PERMISSION);

        House house = houses.get(houseId);

        if(house == null){
            throw new EntityNotFoundException(houseId);
        }

        house.showConfiguration();
    }

    @Override
    public void showRoomConfiguration(AccessToken accessToken, String houseId, String roomId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) :
                "House identifier cannot be null or empty string";

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        entitlementService.checkAccess(accessToken, houseId, OCCUPANT_CRUD_ACCESS_PERMISSION, HOUSE_CRUD_ACCESS_PERMISSION);

        System.out.println("*** Start of room configuration ***");
        House house = getExistingHouse(houseId);
        Room room = getExistingRoom(house, roomId);

        room.showConfiguration(houseId);
        showRoomOccupants(houseId, roomId);

        System.out.println("*** End of room configuration ***");
        System.out.println("");
    }

    @Override
    public void defineSensor(AccessToken accessToken, String houseId, String roomId, String sensorType, String sensorId) throws EntityNotFoundException, EntityExistsException, InvalidEntityTypeException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) :
                "House identifier cannot be null or empty string";

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert sensorType != null && !"".equals(sensorType) :
                "Sensor type cannot be null or empty string";

        assert sensorId != null && !"".equals(sensorId) :
                "Sensor identifier cannot be null or empty string";

        String deviceTypePermission = sensorType.toUpperCase() + _CRUD_ACCESS_PERMISSION;
        entitlementService.checkAccess(accessToken, houseId, deviceTypePermission, HOUSE_CRUD_ACCESS_PERMISSION);

        House house = getExistingHouse(houseId);
        house.defineSensor(roomId, sensorType, sensorId);
    }

    @Override
    public void defineAppliance(AccessToken accessToken, String houseId, String roomId, String applianceType, String applianceId) throws EntityNotFoundException, EntityExistsException, InvalidEntityTypeException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) :
                "House identifier cannot be null or empty string";

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert applianceType != null :
                "Appliance type cannot be null";

        assert applianceId != null && !"".equals(applianceId) :
                "Appliance identifier cannot be null or empty string";

        String deviceTypePermission = applianceType.toUpperCase() + _CRUD_ACCESS_PERMISSION;
        entitlementService.checkAccess(accessToken, houseId, deviceTypePermission, HOUSE_CRUD_ACCESS_PERMISSION);

        House house = getExistingHouse(houseId);

        house.defineAppliance(roomId, applianceType, applianceId);
    }

    @Override
    public void setDeviceStatus(AccessToken accessToken, String houseId, String roomId, String deviceId, String statusKey, String newStatusValue) throws EntityNotFoundException, InvalidStatusException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) :
                "House identifier cannot be null or empty string";

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert deviceId != null && !"".equals(deviceId) :
                "Device identifier cannot be null or empty string";

        assert statusKey != null && !"".equals(statusKey) :
                "Status key cannot be null";

        assert newStatusValue != null && !"".equals(newStatusValue) :
                "Status value cannot be null";

        House house = getExistingHouse(houseId);

        //Let's validate that given room exists
        Room room = getExistingRoom(house, roomId);

        Device device = house.getDevice(deviceId);

        String deviceTypePermission = device.getType().toUpperCase() + _CRUD_ACCESS_PERMISSION;
        entitlementService.checkAccess(accessToken, houseId, deviceTypePermission, HOUSE_CRUD_ACCESS_PERMISSION);

        String oldStatusValue = device.getStatus(statusKey);

        if(!newStatusValue.equals(oldStatusValue) || Ava.DEVICE_TYPE.equalsIgnoreCase(device.getType())){
            device.setStatus(statusKey, newStatusValue);
            notifyDeviceStatusChange(houseId, roomId, device, statusKey, oldStatusValue);
        }
    }

    private void notifyDeviceStatusChange(String houseId, String roomId, Device device, String statusKey, String oldStatusValue) {
        DeviceStatusChangeEvent event = new DeviceStatusChangeEvent(device.getIdentifier(), device.getType(), houseId, roomId,
                statusKey, oldStatusValue, device.getStatus(statusKey));

        deviceStatusChangeListeners.forEach(listener -> {
            listener.statusChanged(event);
        });
    }

    @Override
    public void showDeviceStatus(AccessToken accessToken, String houseId, String roomId, String deviceId, String statusKey) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) :
                "House identifier cannot be null or empty string";

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert deviceId != null && !"".equals(deviceId) :
                "Device identifier cannot be null or empty string";

        assert statusKey != null && !"".equals(statusKey) :
                "Status key cannot be null";

        House house = getExistingHouse(houseId);

        //Let's validate that given room exists
        Room room = getExistingRoom(house, roomId);

        Device device = house.getDevice(deviceId);
        if(device == null){
            throw new EntityNotFoundException(deviceId);
        }

        String deviceTypePermission = device.getType().toUpperCase() + _CRUD_ACCESS_PERMISSION;
        entitlementService.checkAccess(accessToken, houseId, deviceTypePermission, HOUSE_CRUD_ACCESS_PERMISSION);

        device.showStatus(statusKey);
    }

    @Override
    public String getDeviceStatus(AccessToken accessToken, String houseId, String roomId, String deviceId, String statusKey) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) :
                "House identifier cannot be null or empty string";

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert deviceId != null && !"".equals(deviceId) :
                "Device identifier cannot be null or empty string";

        assert statusKey != null && !"".equals(statusKey) :
                "Status key cannot be null";

        House house = getExistingHouse(houseId);

        //Let's validate that given room exists
        Room room = getExistingRoom(house, roomId);
        Device device = house.getDevice(deviceId);

        if(device == null){
            throw new EntityNotFoundException(deviceId);
        }

        String deviceTypePermission = device.getType().toUpperCase() + _CRUD_ACCESS_PERMISSION;
        entitlementService.checkAccess(accessToken, houseId, deviceTypePermission, HOUSE_CRUD_ACCESS_PERMISSION);

        return  device.getStatus(statusKey);
    }

    @Override
    public void showDeviceStatus(AccessToken accessToken, String houseId, String roomId, String deviceId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) :
                "House identifier cannot be null or empty string";

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert deviceId != null && !"".equals(deviceId) :
                "Device identifier cannot be null or empty string";

        House house = getExistingHouse(houseId);

        //Let's validate that given room exists
        Room room = getExistingRoom(house, roomId);
        Device device = house.getDevice(deviceId);

        String deviceTypePermission = device.getType().toUpperCase() + _CRUD_ACCESS_PERMISSION;
        entitlementService.checkAccess(accessToken, houseId, deviceTypePermission, HOUSE_CRUD_ACCESS_PERMISSION);

        device.showStatus();
    }

    @Override
    public void registerListener(AccessToken accessToken, DeviceStatusChangeListener listener) throws AccessDeniedException, InvalidAccessTokenException {
        if(listener == null){
            throw new IllegalArgumentException("DeviceStatusChangeListener cannot be null");
        }

        entitlementService.checkAccess(accessToken, Resource.ALL_RESOURCE_ID, LISTENER_CRUD_ACCESS_PERMISSION);
        deviceStatusChangeListeners.add(listener);
    }


    @Override
    public Set<String> getDeviceIds(AccessToken accessToken, String houseId, String deviceType) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) : "House identifier cannot be null or empty string" ;

        assert deviceType != null && !"".equals(deviceType) :
                "Device type cannot be null or empty string";

        String deviceTypePermission = deviceType.toUpperCase() + _CRUD_ACCESS_PERMISSION;
        entitlementService.checkAccess(accessToken, houseId, deviceTypePermission, HOUSE_CRUD_ACCESS_PERMISSION);

        House house = getExistingHouse(houseId);

        Set<String> deviceIds = house.getDeviceByType(deviceType);
        return deviceIds;
    }

    @Override
    public Set<String> getDeviceIds(AccessToken accessToken, String houseId, String roomId, String deviceType) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) : "House identifier cannot be null or empty string" ;

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        assert deviceType != null && !"".equals(deviceType) :
                "Device type cannot be null or empty string";

        String deviceTypePermission = deviceType.toUpperCase() + _CRUD_ACCESS_PERMISSION;
        entitlementService.checkAccess(accessToken, houseId, deviceTypePermission, HOUSE_CRUD_ACCESS_PERMISSION);

        House house = getExistingHouse(houseId);

        Set<String> deviceIds = house.getDeviceByRoomAndType(roomId, deviceType);
        return deviceIds;
    }

    @Override
    public void setOccupantLocation(AccessToken accessToken, String occupantId, String houseId, String roomId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert occupantId != null && !"".equals(occupantId) : "Occupant id cannot be null or empty string";

        Occupant occupant = occupants.get(occupantId);
        if(occupant == null){
            throw new EntityNotFoundException(occupantId);
        }

        assert houseId != null && !"".equals(houseId) : "House identifier cannot be null or empty string" ;

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        entitlementService.checkAccess(accessToken, houseId, OCCUPANT_CRUD_ACCESS_PERMISSION, HOUSE_CRUD_ACCESS_PERMISSION);

        updateOccupantLocation(accessToken, occupant, houseId, roomId);
    }

    @Override
    public void unSetOccupantLocation(AccessToken accessToken, String occupantId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert occupantId != null && !"".equals(occupantId) : "Occupant id cannot be null or empty string";

        Occupant occupant = occupants.get(occupantId);
        if(occupant == null){
            throw new EntityNotFoundException(occupantId);
        }

        entitlementService.checkAccess(accessToken, Resource.ALL_RESOURCE_ID, OCCUPANT_CRUD_ACCESS_PERMISSION);

        KnowledgeGraph graph = KnowledgeGraph.getInstance();
        Node occupantNode = graph.getNode(occupant.getIdentifier());
        Predicate housePredicate = graph.getPredicate(HOUSE_PREDICATE);
        Predicate roomPredicate = graph.getPredicate(ROOM_PREDICATE);
        Node unknownLocationNode = graph.getNode("?");

        //Find current house location of this occupant,
        // and remove corresponding triples from the KnowledgeGraph
        Set<Triple> houseTriples = graph.executeQuery(occupantNode, housePredicate, unknownLocationNode);
        graph.removeTriples(houseTriples);
        houseTriples.forEach(houseTriple -> {
            houses.get(houseTriple.getIdentifier()).removeOccupant(occupant);
        });

        //Find current room location of this occupant,
        // and remove corresponding triples from the KnowledgeGraph
        Set<Triple> roomTriples = graph.executeQuery(occupantNode, roomPredicate, unknownLocationNode);
        graph.removeTriples(roomTriples);

        occupant.setCurrentLocation(null);
    }

    @Override
    public Set<String> getOccupantIds(AccessToken accessToken, String houseId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) : "House identifier cannot be null or empty string" ;

        entitlementService.checkAccess(accessToken, houseId, OCCUPANT_CRUD_ACCESS_PERMISSION, HOUSE_CRUD_ACCESS_PERMISSION);

        //verify that house and room identifiers are valid
        House house = getExistingHouse(houseId);

        KnowledgeGraph graph = KnowledgeGraph.getInstance();

        Node unknownOccupantsNode = graph.getNode("?");
        Predicate housePredicate = graph.getPredicate(HOUSE_PREDICATE);
        Node houseNode = graph.getNode(houseId);

        Set<Triple> triples = graph.executeQuery(unknownOccupantsNode, housePredicate, houseNode);

        Set<String> occupantIds = new HashSet<>();

        triples.forEach(triple -> {
            occupantIds.add(triple.getSubject().getIdentifier());
        });

        return occupantIds;
    }

    @Override
    public Set<String> getOccupantIds(AccessToken accessToken, String houseId, String roomId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) : "House identifier cannot be null or empty string" ;

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        entitlementService.checkAccess(accessToken, houseId, OCCUPANT_CRUD_ACCESS_PERMISSION, HOUSE_CRUD_ACCESS_PERMISSION);

        //verify that house and room identifiers are valid
        House house = getExistingHouse(houseId);
        Room room = getExistingRoom(house, roomId);

        KnowledgeGraph graph = KnowledgeGraph.getInstance();

        Node unknownOccupantsNode = graph.getNode("?");
        Predicate roomPredicate = graph.getPredicate(ROOM_PREDICATE);
        Node locationNode = graph.getNode(houseId + ":" + roomId);

        Set<Triple> triples = graph.executeQuery(unknownOccupantsNode, roomPredicate, locationNode);

        Set<String> occupantIds = new HashSet<>();

        triples.forEach(triple -> {
            occupantIds.add(triple.getSubject().getIdentifier());
        });

        return occupantIds;
    }

    @Override
    public void setOccupantActivityStatus(AccessToken accessToken, String occupantId, OccupantActivityStatus activityStatus) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert occupantId != null && !"".equals(occupantId) : "Occupant id cannot be null or empty string";

        Occupant occupant = occupants.get(occupantId);
        if(occupant == null){
            throw new EntityNotFoundException(occupantId);
        }

        assert activityStatus!=null : "Occupant activity status cannot be null";

        entitlementService.checkAccess(accessToken, Resource.ALL_RESOURCE_ID, OCCUPANT_CRUD_ACCESS_PERMISSION);

        KnowledgeGraph graph = KnowledgeGraph.getInstance();
        Node occupantNode = graph.getNode(occupant.getIdentifier());
        Predicate activityPredicate = graph.getPredicate(ACTIVITY_PREDICATE);
        Node unknownActivityNode = graph.getNode("?");

        //Find current status of this occupant,
        // and remove corresponding triples from the KnowledgeGraph
        Set<Triple> triples = graph.executeQuery(occupantNode, activityPredicate, unknownActivityNode);
        graph.removeTriples(triples);

        Node currentActivityNode = graph.getNode(activityStatus.toString());
        Triple triple = graph.getTriple(occupantNode, activityPredicate, currentActivityNode);
        graph.importTriples(Collections.singletonList(triple));

        occupant.setStatus(activityStatus);
    }

    @Override
    public String getOccupantLocation(AccessToken accessToken, String occupantId, String houseId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert occupantId != null && !"".equals(occupantId) : "Occupant identifier cannot be null or empty string";
        assert houseId != null && !"".equals(houseId) : "House identifier cannot be null or empty string" ;

        entitlementService.checkAccess(accessToken, houseId, OCCUPANT_CRUD_ACCESS_PERMISSION, HOUSE_CRUD_ACCESS_PERMISSION);

        //validate that house exists.
        House house = getExistingHouse(houseId);

        //Let's make sure that given occupant exists in this house.
        Occupant occupant = house.getOccupant(occupantId);
        if(occupant == null){
            return null;
        }

        //Let's get current location of this occupant.
        KnowledgeGraph graph = KnowledgeGraph.getInstance();
        Node occupantNode = graph.getNode(occupantId);
        Predicate roomPredicate = graph.getPredicate(ROOM_PREDICATE);
        Node unknownLocationNode = graph.getNode("?");

        Set<Triple> occupantLocationTriples = graph.executeQuery(occupantNode, roomPredicate, unknownLocationNode);
        if(occupantLocationTriples.isEmpty()){
            return null;
        }

        if (occupantLocationTriples.size() > 1){
            //We don't expect an occupant to be in multiple locations at a given point in time.
            //So, let's return null to indicate that this occupant's location is unknown.
            return null;
        }

        //Now, we have made sure that occupantLocationTriples has only one triple.
        //Let's retrieve occupant's location from this triple.
        String occupantLocation = null;
        for (Triple occupantLocationTriple : occupantLocationTriples) {
            occupantLocation = occupantLocationTriple.getObject().getIdentifier();
        }

        //occupantLocation is in format houseId:roomId. Let's remove the houseId: prefix.
        occupantLocation = occupantLocation.replace(houseId+":", "");
        return occupantLocation;
    }

    private void updateOccupantLocation(AccessToken accessToken, Occupant occupant, String houseId, String roomId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        entitlementService.checkAccess(accessToken, houseId, OCCUPANT_CRUD_ACCESS_PERMISSION, HOUSE_CRUD_ACCESS_PERMISSION);

        unSetOccupantLocation(accessToken, occupant.getIdentifier());

        KnowledgeGraph graph = KnowledgeGraph.getInstance();
        Node occupantNode = graph.getNode(occupant.getIdentifier());
        Predicate housePredicate = graph.getPredicate(HOUSE_PREDICATE);

        House house = getExistingHouse(houseId);
        Room room = house.getRoom(roomId);

        Node newHouseLocationNode = graph.getNode(houseId);
        Triple newHouseLocationTriple = graph.getTriple(occupantNode, housePredicate, newHouseLocationNode);
        graph.importTriples(Collections.singletonList(newHouseLocationTriple));

        Predicate roomPredicate = graph.getPredicate(ROOM_PREDICATE);
        Node newRoomLocationNode = graph.getNode(houseId + ":" + roomId);
        Triple newRoomLocationTriple = graph.getTriple(occupantNode, roomPredicate, newRoomLocationNode);
        graph.importTriples(Collections.singletonList(newRoomLocationTriple));

        occupant.setCurrentLocation(room);
        house.addOccupant(occupant);
    }

    private void showRoomOccupants(String houseId, String roomId) {
        KnowledgeGraph graph = KnowledgeGraph.getInstance();
        Node unknownOccupantsNode = graph.getNode("?");
        Predicate roomPredicate = graph.getPredicate(ROOM_PREDICATE);
        Node locationNode = graph.getNode(houseId + ":" + roomId);

        Set<Triple> triples = graph.executeQuery(unknownOccupantsNode,roomPredicate, locationNode);

        if(triples.isEmpty()){
            System.out.println("No occupants in house: '" + houseId + "', room: '" + roomId + "'");
        }else{
            System.out.println("Listing all the occupants in house: '" + houseId + "', room: '" + roomId + "'");

            triples.forEach((triple) -> {
                String occupantId = triple.getSubject().getIdentifier();
                occupants.get(occupantId).showConfiguration();
            });

            System.out.println("End of occupants listing.");
        }
    }

    @Override
    public int getFloorNumber(AccessToken accessToken, String houseId, String roomId) throws EntityNotFoundException, AccessDeniedException, InvalidAccessTokenException {
        assert houseId != null && !"".equals(houseId) : "House identifier cannot be null or empty string" ;

        assert roomId != null && !"".equals(roomId) :
                "Room identifier cannot be null or empty string";

        entitlementService.checkAccess(accessToken, houseId, HOUSE_CRUD_ACCESS_PERMISSION);

        //verify that house and room identifiers are valid
        House house = getExistingHouse(houseId);
        Room room = getExistingRoom(house, roomId);

        return room.getFloorNumber();
    }

    private House getExistingHouse(String houseId) throws EntityNotFoundException {
        House house = houses.get(houseId);

        if(house == null){
            throw new EntityNotFoundException(houseId);
        }

        return house;
    }

    private Room getExistingRoom(House house, String roomId) throws EntityNotFoundException {
        Room room = house.getRoom(roomId);
        if(room == null){
            throw new EntityNotFoundException(roomId);
        }
        return room;
    }
}
