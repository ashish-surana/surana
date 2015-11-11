package cscie97.asn3.housemate.model.service.impl;

import cscie97.asn3.housemate.model.listener.DeviceStatusChangeListener;
import cscie97.asn3.housemate.model.service.HouseMateModelService;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidEntityTypeException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;
import cscie97.asn3.housemate.model.support.OccupantActivityStatus;
import cscie97.asn3.housemate.model.support.OccupantType;
import cscie97.asn3.housemate.model.support.RoomType;

import java.util.Set;

/**
 * This class adds entitlement specific features to HouseMateModelService implementations.
 * For example, methods of this class authenticate AccessToken before delegating the service calls
 * to underlying HouseMateModelService instance. This class also manages entitlement models
 * such as Resource, User etc when the underlying HouseMateModelService instance creates a new House or
 * a new occupant.
 */
public class HouseMateModelServiceEntitlementProxy {
//    implements
//} HouseMateModelService {
//
//    private static final HouseMateModelService INSTANCE =
//            new HouseMateModelServiceEntitlementProxy(HouseMateModelServiceImpl.getInstance());
//
//    private final HouseMateModelService modelServiceDelegate;
//
//    private HouseMateModelServiceEntitlementProxy(HouseMateModelService modelServiceDelegate) {
//        this.modelServiceDelegate = modelServiceDelegate;
//    }
//
//    /**
//     * This method returns an instance of HouseMateModelServiceEntitlementProxy.
//     * @return
//     */
//    public static HouseMateModelService getInstance() {
//        return INSTANCE;
//    }
//
//    /**
//     * Defines a house using given id.
//     *
//     * @param authToken
//     * @param houseIdentifier
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityExistsException if the house with given id already exists.
//     */
//    @Override
//    public void defineHouse(String authToken, String houseIdentifier) throws EntityExistsException {
//        modelServiceDelegate.defineHouse(authToken, houseIdentifier);
//    }
//
//    /**
//     * Defines a room with given id, in the given house.
//     *
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if house with given id does not exists.
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityExistsException   if room with given id already exists.
//     */
//    @Override
//    public void defineRoom(String authToken, String houseId, String roomId, RoomType roomType, Integer floorNumber) throws EntityNotFoundException, EntityExistsException {
//        modelServiceDelegate.defineRoom(authToken, houseId, roomId, roomType, floorNumber);
//    }
//
//    @Override
//    public void defineOccupant(String authToken, String occupantId, OccupantType occupantType) throws EntityExistsException {
//        modelServiceDelegate.defineOccupant(authToken, occupantId, occupantType);
//    }
//
//    /**
//     * Adds given occupant to the given house.
//     *
//     * @param authToken
//     * @param occupantId
//     * @param houseId
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException If the house or occupant does not exists.
//     */
//    @Override
//    public void addOccupant(String authToken, String occupantId, String houseId) throws EntityNotFoundException {
//        modelServiceDelegate.addOccupant(authToken, occupantId, houseId);
//    }
//
//    /**
//     * Shows configuration of all the entities in the HouseMate model service.
//     *
//     * @param authToken
//     */
//    @Override
//    public void showConfiguration(String authToken) {
//        modelServiceDelegate.showConfiguration(authToken);
//    }
//
//    /**
//     * Shows configuration of the house with given id.
//     *
//     * @param authToken
//     * @param houseId
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if house with given id does not exists.
//     */
//    @Override
//    public void showHouseConfiguration(String authToken, String houseId) throws EntityNotFoundException {
//        modelServiceDelegate.showHouseConfiguration(authToken, houseId);
//    }
//
//    /**
//     * Shows configuration of the room with given id, in the given house.
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if the given room or house does not exists.
//     */
//    @Override
//    public void showRoomConfiguration(String authToken, String houseId, String roomId) throws EntityNotFoundException {
//        modelServiceDelegate.showRoomConfiguration(authToken, houseId, roomId);
//    }
//
//    /**
//     * Defines a sensor of given type in the given house and room.
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @param sensorType
//     * @param sensorId
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if the given house or room does not exists.
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityExistsException   if a device with given id already exists in the HouseMate model service.
//     */
//    @Override
//    public void defineSensor(String authToken, String houseId, String roomId, String sensorType, String sensorId) throws EntityNotFoundException, EntityExistsException, InvalidEntityTypeException {
//        modelServiceDelegate.defineSensor(authToken, houseId, roomId, sensorType, sensorId);
//    }
//
//    /**
//     * Defines an appliance of given type in the given house and room.
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @param applianceType
//     * @param applianceId
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if the given house or room does not exists.
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityExistsException   if a device with given id already exists in the HouseMate model service.
//     */
//    @Override
//    public void defineAppliance(String authToken, String houseId, String roomId, String applianceType, String applianceId) throws EntityNotFoundException, EntityExistsException, InvalidEntityTypeException {
//        modelServiceDelegate.defineAppliance(authToken, houseId, roomId, applianceType, applianceId);
//    }
//
//    /**
//     * Sets status of the device(sensor or appliance) with given id, in the given house and room.
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @param deviceId
//     * @param statusKey
//     * @param statusValue
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException If the given house, room or device do not exist.
//     * @throws cscie97.asn3.housemate.model.service.exception.InvalidStatusException  If the given status key or value are not valid for the given device.
//     */
//    @Override
//    public void setDeviceStatus(String authToken, String houseId, String roomId, String deviceId, String statusKey, String statusValue) throws EntityNotFoundException, InvalidStatusException {
//        modelServiceDelegate.setDeviceStatus(authToken, houseId, roomId, deviceId, statusKey, statusValue);
//    }
//
//    /**
//     * Shows complete status of the given device(appliance or sensor)
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @param deviceId
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException If the given house, room or device do not exists.
//     */
//    @Override
//    public void showDeviceStatus(String authToken, String houseId, String roomId, String deviceId) throws EntityNotFoundException {
//        modelServiceDelegate.showDeviceStatus(authToken, houseId, roomId, deviceId);
//    }
//
//    /**
//     * Shows current value of the given device's requests status key. If no value is set, then an empty string is displayed.
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @param deviceId
//     * @param statusKey
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException If the given house, room, or device do not exists.
//     */
//    @Override
//    public void showDeviceStatus(String authToken, String houseId, String roomId, String deviceId, String statusKey) throws EntityNotFoundException {
//        modelServiceDelegate.showDeviceStatus(authToken, houseId, roomId, deviceId, statusKey);
//    }
//
//    /**
//     * This method should be used to register a listener for listening to device's status change.
//     * Currently, there is no way to de-register a lisener.
//     *
//     * @param authToken
//     * @param listener
//     */
//    @Override
//    public void registerListener(String authToken, DeviceStatusChangeListener listener) {
//        modelServiceDelegate.registerListener(authToken, listener);
//    }
//
//    /**
//     * This method returns a set of ids for all devices of given type in given location.
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @param deviceType
//     * @return
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if given house or room do not exist.
//     */
//    @Override
//    public Set<String> getDeviceIds(String authToken, String houseId, String roomId, String deviceType) throws EntityNotFoundException {
//        return modelServiceDelegate.getDeviceIds(authToken, houseId, roomId, deviceType);
//    }
//
//    /**
//     * This method sets the location of given occupant using given house and room.
//     *
//     * @param authToken
//     * @param occupantId
//     * @param houseId
//     * @param roomId
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityExistsException
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if given occupant, house, or room do not exists.
//     */
//    @Override
//    public void setOccupantLocation(String authToken, String occupantId, String houseId, String roomId) throws EntityNotFoundException {
//        modelServiceDelegate.setOccupantLocation(authToken, occupantId, houseId, roomId);
//    }
//
//    /**
//     * This method is used to set current location of given occupants as 'Unknown'.
//     *
//     * @param authToken
//     * @param occupantId
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if occupant with given id does not exists.
//     */
//    @Override
//    public void unSetOccupantLocation(String authToken, String occupantId) throws EntityNotFoundException {
//        modelServiceDelegate.unSetOccupantLocation(authToken, occupantId);
//    }
//
//    /**
//     * This method returns a set of ids of all occupants in the given location.
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @return
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if given house, room do not exists.
//     */
//    @Override
//    public Set<String> getOccupantIds(String authToken, String houseId, String roomId) throws EntityNotFoundException {
//        return modelServiceDelegate.getOccupantIds(authToken, houseId, roomId);
//    }
//
//    /**
//     * This method sets given occupant's status using the given input.
//     *
//     * @param authToken
//     * @param occupantId
//     * @param activityStatus
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if the given occupant does not exists.
//     */
//    @Override
//    public void setOccupantActivityStatus(String authToken, String occupantId, OccupantActivityStatus activityStatus) throws EntityNotFoundException {
//        modelServiceDelegate.setOccupantActivityStatus(authToken, occupantId, activityStatus);
//    }
//
//    /**
//     * This method returns floor number of given room. Floor number 1 indicates ground floor.
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @return
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if given house or room do not exist.
//     */
//    @Override
//    public int getFloorNumber(String authToken, String houseId, String roomId) throws EntityNotFoundException {
//        return modelServiceDelegate.getFloorNumber(authToken, houseId, roomId);
//    }
//
//    /**
//     * This method returns a set of ids of all occupants in the given location.
//     *
//     * @param authToken
//     * @param houseId
//     * @return
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if given house does not exists.
//     */
//    @Override
//    public Set<String> getOccupantIds(String authToken, String houseId) throws EntityNotFoundException {
//        return modelServiceDelegate.getOccupantIds(authToken, houseId);
//    }
//
//    /**
//     * This method returns all devices of given type in the given house.
//     *
//     * @param authToken
//     * @param houseId
//     * @param deviceType
//     * @return
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if given house does not exists.
//     */
//    @Override
//    public Set<String> getDeviceIds(String authToken, String houseId, String deviceType) throws EntityNotFoundException {
//        return getDeviceIds(authToken, houseId, deviceType);
//    }
//
//    /**
//     * This method returns status of the given device in the given location.
//     *
//     * @param authToken
//     * @param houseId
//     * @param roomId
//     * @param deviceId
//     * @param statusKey
//     * @return
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if given house, room or device does not exists.
//     */
//    @Override
//    public String getDeviceStatus(String authToken, String houseId, String roomId, String deviceId, String statusKey) throws EntityNotFoundException {
//        return modelServiceDelegate.getDeviceStatus(authToken, houseId, roomId, deviceId, statusKey);
//    }
//
//    /**
//     * This method returns room location of the given occupant, if they are in the given house.
//     * Otherwise, it returns <code>null</code>.
//     *
//     * @param authToken
//     * @param occupantId
//     * @param houseId
//     * @return
//     * @throws cscie97.asn3.housemate.model.service.exception.EntityNotFoundException if given occupant or house do not exist.
//     */
//    @Override
//    public String getOccupantLocation(String authToken, String occupantId, String houseId) throws EntityNotFoundException {
//        return modelServiceDelegate.getOccupantLocation(authToken, occupantId, houseId);
//    }
}
