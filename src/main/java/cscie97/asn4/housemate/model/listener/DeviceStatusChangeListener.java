package cscie97.asn4.housemate.model.listener;

import cscie97.asn4.housemate.model.listener.support.DeviceStatusChangeEvent;

/**
 * This interface should be used for listening to status changes of a device.
 */
public interface DeviceStatusChangeListener {

    /**
     * This method should be invoked to indicate change in status of a device.
     */
    public void statusChanged(DeviceStatusChangeEvent event);
}
