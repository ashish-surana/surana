package cscie97.asn3.housemate.controller.command;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.model.service.exception.InvalidCommandException;

/**
 * A command simply ignores the change in a device's status.
 * This command is useful when no action is required in response to a device status change.
 */
public class NoOpCommand extends ControllerCommand{

    private final String deviceId;

    private final String deviceType;

    private final String statusKey;

    private final String oldStatusValue;

    private final String newStatusValue;

    public NoOpCommand(AccessToken accessToken, String deviceId, String deviceType, String statusKey, String oldStatusValue, String newStatusValue) {
        super(accessToken);

        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.statusKey = statusKey;
        this.oldStatusValue = oldStatusValue;
        this.newStatusValue = newStatusValue;
    }

    @Override
    public void execute() {
        //Just ignore the device status change.
    }
}
