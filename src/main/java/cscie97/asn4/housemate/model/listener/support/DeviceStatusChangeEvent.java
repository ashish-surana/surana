package cscie97.asn4.housemate.model.listener.support;

/**
 * This class is used as POJO to contain all the details about a device's status change.
 * It contains following information about the device's status change:
 * <ul>
 *     <li>deviceId</li>
 *     <li>deviceType</li>
 *     <li>houseId of this device</li>
 *     <li>roomId of this device</li>
 *     <li>status key, that has change</li>
 *     <li>old status value</li>
 *     <li>new status value.</li>
 * </ul>
 */
public class DeviceStatusChangeEvent {

    public DeviceStatusChangeEvent(String deviceId, String deviceType, String houseId,
                                   String roomId, String statusKey, String oldStatusValue,
                                   String newStatusValue) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.houseId = houseId;
        this.newStatusValue = newStatusValue;
        this.oldStatusValue = oldStatusValue;
        this.roomId = roomId;
        this.statusKey = statusKey;
    }

    private final String deviceId;

    private final String deviceType;

    private final String houseId;

    private final String roomId;

    private final String statusKey;

    private final String oldStatusValue;

    private final String newStatusValue;

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getHouseId() {
        return houseId;
    }

    public String getNewStatusValue() {
        return newStatusValue;
    }

    public String getOldStatusValue() {
        return oldStatusValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getStatusKey() {
        return statusKey;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Device status changed(");
        resultBuilder.append("deviceId:'"+ deviceId + "',");
        resultBuilder.append("statusKey:'"+ statusKey + "',");
        resultBuilder.append("newStatusValue:'" + newStatusValue + "',");
        resultBuilder.append("oldStatusValue:'"+ oldStatusValue + "',");
        resultBuilder.append("deviceType:'"+ deviceType + "',");
        resultBuilder.append("houseId:'" + houseId + "',");
        resultBuilder.append("roomId:'"+ roomId + "'");
        resultBuilder.append(")");
        return resultBuilder.toString();
    }
}
