package cscie97.asn3.housemate.model.service;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.listener.DeviceStatusChangeListener;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidEntityTypeException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;
import cscie97.asn3.housemate.model.support.OccupantActivityStatus;
import cscie97.asn3.housemate.model.support.OccupantType;
import cscie97.asn3.housemate.model.support.RoomType;

import java.util.Set;

/**
 *
 */
public interface HouseMateModelService {

    /**
     * Defines a house using given id.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS permission.
     * @param houseIdentifier
     * @throws EntityExistsException if the house with given id already exists.
     */
    public void defineHouse(AccessToken accessToken, String houseIdentifier) throws EntityExistsException;

    /**
     * Defines a room with given id, in the given house.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS permission.
     * @param houseId
     * @param roomId
     * @param roomType
     * @param floorNumber
     * @throws EntityNotFoundException if house with given id does not exists.
     * @throws EntityExistsException if room with given id already exists.
     */
    public void defineRoom(AccessToken accessToken, String houseId, String roomId, RoomType roomType, Integer floorNumber) throws EntityNotFoundException, EntityExistsException;

    /**
     * Defines an occupant with given input parameters.
     * @param accessToken AccessToken must be active, and corresponding user must have OCCUPANT_CRUD_ACCESS permission.
     * @param occupantId
     * @param occupantType
     * @throws EntityExistsException if occupant with given id already exists.
     */
    public void defineOccupant(AccessToken accessToken, String occupantId, OccupantType occupantType) throws EntityExistsException;

    /**
     * Adds given occupant to the given house.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and OCCUPANT_CRUD_ACCESS permission.
     * @param occupantId
     * @param houseId
     * @throws EntityNotFoundException If the house or occupant does not exists.
     */
    public void addOccupant(AccessToken accessToken, String occupantId, String houseId) throws EntityNotFoundException;

    /**
     * Shows configuration of all the entities in the HouseMate model service.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and OCCUPANT_CRUD_ACCESS permission.
     */
    public void showConfiguration(AccessToken accessToken);

    /**
     * Shows configuration of the house with given id.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and OCCUPANT_CRUD_ACCESS permission.
     * @param houseId
     * @throws EntityNotFoundException if house with given id does not exists.
     */
    public void showHouseConfiguration(AccessToken accessToken, String houseId) throws EntityNotFoundException;

    /**
     * Shows configuration of the room with given id, in the given house.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and OCCUPANT_CRUD_ACCESS permission.
     * @param houseId
     * @param roomId
     * @throws EntityNotFoundException if the given room or house does not exists.
     */
    public void showRoomConfiguration(AccessToken accessToken, String houseId, String roomId) throws EntityNotFoundException;

    /**
     * Defines a sensor of given type in the given house and room.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and ${sensorType}_CRUD_ACCESS permissions,
     *                    where ${sensorType} is the input sensor type.
     * @param houseId
     * @param roomId
     * @param sensorType
     * @param sensorId
     * @throws EntityNotFoundException if the given house or room does not exists.
     * @throws EntityExistsException if a device with given id already exists in the HouseMate model service.
     */
    public void defineSensor(AccessToken accessToken, String houseId, String roomId, String sensorType, String sensorId) throws EntityNotFoundException, EntityExistsException, InvalidEntityTypeException;

    /**
     * Defines an appliance of given type in the given house and room.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and ${applianceType}_CRUD_ACCESS permissions,
     *                    where ${applianceType} is the input appliance type.
     * @param houseId
     * @param roomId
     * @param applianceType
     * @param applianceId
     * @throws EntityNotFoundException if the given house or room does not exists.
     * @throws EntityExistsException if a device with given id already exists in the HouseMate model service.
     */
    public void defineAppliance(AccessToken accessToken, String houseId, String roomId, String applianceType, String applianceId) throws EntityNotFoundException, EntityExistsException, InvalidEntityTypeException;

    /**
     * Sets status of the device(sensor or appliance) with given id, in the given house and room.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and ${deviceType}_CRUD_ACCESS permissions,
     *                    where ${deviceType} is the device type.
     * @param houseId
     * @param roomId
     * @param deviceId
     * @param statusKey
     * @param statusValue
     * @throws EntityNotFoundException If the given house, room or device do not exist.
     * @throws InvalidStatusException If the given status key or value are not valid for the given device.
     */
    public void setDeviceStatus(AccessToken accessToken, String houseId, String roomId, String deviceId, String statusKey, String statusValue) throws EntityNotFoundException, InvalidStatusException;

    /**
     * Shows complete status of the given device(appliance or sensor)
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and ${deviceType}_CRUD_ACCESS permissions,
     *                    where ${deviceType} is the device type.
     * @param houseId
     * @param roomId
     * @param deviceId
     * @throws EntityNotFoundException If the given house, room or device do not exists.
     */
    public void showDeviceStatus(AccessToken accessToken, String houseId, String roomId, String deviceId) throws EntityNotFoundException;

    /**
     * Shows current value of the given device's requests status key. If no value is set, then an empty string is displayed.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and ${deviceType}_CRUD_ACCESS permissions,
     *                    where ${deviceType} is the device type of given device id.
     * @param houseId
     * @param roomId
     * @param deviceId
     * @param statusKey
     * @throws EntityNotFoundException If the given house, room, or device do not exists.
     */
    public void showDeviceStatus(AccessToken accessToken, String houseId, String roomId, String deviceId, String statusKey) throws EntityNotFoundException;

    /**
     * This method should be used to register a listener for listening to device's status change.
     * Currently, there is no way to de-register a listener.
     * @param accessToken AccessToken must be active, and corresponding user must have DEVICE_STATUS_CHANGE_LISTENER_CRUD_ACCESS.
     * @param listener
     */
    public void registerListener(AccessToken accessToken, DeviceStatusChangeListener listener);

    /**
     * This method returns a set of ids for all devices of given type in given location.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and ${deviceType}_CRUD_ACCESS permissions,
     *                    where ${deviceType} is the input device type.
     * @param houseId
     * @param roomId
     * @param deviceType
     * @return
     * @throws EntityNotFoundException if given house or room do not exist.
     */
    public Set<String> getDeviceIds(AccessToken accessToken, String houseId, String roomId, String deviceType) throws EntityNotFoundException;

    /**
     * This method sets the location of given occupant using given house and room.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and OCCUPANT_CRUD_ACCESS permissions,
     *                    for given house and occupant.
     * @param occupantId
     * @param houseId
     * @param roomId
     * @throws EntityExistsException
     * @throws EntityNotFoundException if given occupant, house, or room do not exists.
     */
    public void setOccupantLocation(AccessToken accessToken, String occupantId, String houseId, String roomId) throws EntityNotFoundException;

    /**
     * This method is used to set current location of given occupants as 'Unknown'.
     * @param accessToken AccessToken must be active, and corresponding user must have OCCUPANT_CRUD_ACCESS permission,
     *                    for given occupant.
     * @param occupantId
     * @throws EntityNotFoundException if occupant with given id does not exists.
     */
    public void unSetOccupantLocation(AccessToken accessToken, String occupantId) throws EntityNotFoundException;

    /**
     * This method returns a set of ids of all occupants in the given location.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and OCCUPANT_CRUD_ACCESS permission,
     *                    for given house and its occupants.
     * @param houseId
     * @param roomId
     * @return
     * @throws EntityNotFoundException if given house, room do not exists.
     */
    public Set<String> getOccupantIds(AccessToken accessToken, String houseId, String roomId) throws EntityNotFoundException;

    /**
     * This method returns a set of ids of all occupants in the given location.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and OCCUPANT_CRUD_ACCESS permission,
     *                    for given house and its occupants.
     * @param houseId
     * @return
     * @throws EntityNotFoundException if given house does not exists.
     */
    public Set<String> getOccupantIds(AccessToken accessToken, String houseId) throws EntityNotFoundException;

    /**
     * This method sets given occupant's status using the given input.
     * @param accessToken AccessToken must be active, and corresponding user must have OCCUPANT_CRUD_ACCESS permission,
     *                    for given occupant.
     * @param occupantId
     * @param activityStatus
     * @throws EntityNotFoundException if the given occupant does not exists.
     */
    public void setOccupantActivityStatus(AccessToken accessToken, String occupantId, OccupantActivityStatus activityStatus) throws EntityNotFoundException;

    /**
     * This method returns floor number of given room. Floor number 1 indicates ground floor.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS permission,
     *                    for given house.
     * @param houseId
     * @param roomId
     * @return
     * @throws EntityNotFoundException if given house or room do not exist.
     */
    public int getFloorNumber(AccessToken accessToken, String houseId, String roomId) throws EntityNotFoundException;

    /**
     * This method returns all devices of given type in the given house.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and ${deviceType}_CRUD_ACCESS permissions,
     *                    where ${deviceType} is the input device type.
     * @param houseId
     * @param deviceType
     * @return
     * @throws EntityNotFoundException if given house does not exists.
     */
    public Set<String> getDeviceIds(AccessToken accessToken, String houseId, String deviceType) throws EntityNotFoundException;

    /**
     * This method returns status of the given device in the given location.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and ${deviceType}_CRUD_ACCESS permissions,
     *                    where ${deviceType} is the device type of given device id.
     * @param houseId
     * @param roomId
     * @param deviceId
     * @param statusKey
     * @return
     * @throws EntityNotFoundException if given house, room or device does not exists.
     */
    public String getDeviceStatus(AccessToken accessToken, String houseId, String roomId, String deviceId, String statusKey) throws EntityNotFoundException;

    /**
     * This method returns room location of the given occupant, if they are in the given house.
     * Otherwise, it returns <code>null</code>.
     * @param accessToken AccessToken must be active, and corresponding user must have HOUSE_CRUD_ACCESS and OCCUPANT_CRUD_ACCESS permission,
     *                    for given house and occupant.
     * @param occupantId
     * @param houseId
     * @return
     * @throws EntityNotFoundException if given occupant or house do not exist.
     */
    public String getOccupantLocation(AccessToken accessToken, String occupantId, String houseId) throws EntityNotFoundException;
}
