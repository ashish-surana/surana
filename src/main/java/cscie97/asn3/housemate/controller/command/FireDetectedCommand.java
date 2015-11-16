package cscie97.asn3.housemate.controller.command;

import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.exception.AccessDeniedException;
import cscie97.asn3.housemate.entitlement.exception.InvalidAccessTokenException;
import cscie97.asn3.housemate.model.appliance.Ava;
import cscie97.asn3.housemate.model.appliance.Light;
import cscie97.asn3.housemate.model.appliance.Window;
import cscie97.asn3.housemate.model.service.exception.EntityExistsException;
import cscie97.asn3.housemate.model.service.exception.EntityNotFoundException;
import cscie97.asn3.housemate.model.service.exception.InvalidStatusException;

import java.util.Set;

/**
 * This command should be used when SmokeDetector detects fire.
 */
public class FireDetectedCommand extends ControllerCommand {

    private final String roomId;

    private final String houseId;

    public FireDetectedCommand(AccessToken accessToken, String houseId, String roomId) {
        super(accessToken);
        
        assert houseId!=null && !"".equals(houseId) : "House id cannot be null or empty string";
        assert roomId!=null && !"".equals(roomId) : "Room id cannot be null or empty string";

        this.houseId = houseId;
        this.roomId = roomId;
    }

    /**
     * This method notifies all the Ava's in the house about location of the fire.
     * If there are any occupants in the house, then it turns on all the lights.
     * If the fire is on ground floor then it recommends occupants to leave via window, and it opens
     * all the windows in this room. It calls 911 and report fire location, occupant identities.
     */
    @Override
    public void execute() throws EntityNotFoundException, InvalidStatusException, EntityExistsException, AccessDeniedException, InvalidAccessTokenException {
        Set<String> avaIds = modelService.getDeviceIds(accessToken, houseId, Ava.DEVICE_TYPE);
        String avaFireMessage = "Fire in '"+roomId +"', please leave '"+houseId+"' immediately!";
        for (String avaId : avaIds) {
            modelService.setDeviceStatus(accessToken, houseId, roomId, avaId, Ava.SPEAKING, avaFireMessage);
        }

        Set<String> occupantIds = modelService.getOccupantIds(accessToken, houseId);
        if(!occupantIds.isEmpty()){
            Set<String> lightIds = modelService.getDeviceIds(accessToken, houseId, Light.DEVICE_TYPE);
            for (String lightId : lightIds) {
                modelService.setDeviceStatus(accessToken, houseId, roomId, lightId, Light.MODE, Light.ON);
            }

            int floorNum = modelService.getFloorNumber(accessToken, houseId, roomId);
            if(floorNum == 1){
                Set<String> windowIds = modelService.getDeviceIds(accessToken, houseId, roomId, Window.DEVICE_TYPE);

                if(!windowIds.isEmpty()) {
                    String avaWindowExitMessage = "Opening all windows in '" + roomId + "', exit through windows!";
                    for (String avaId : avaIds) {
                        modelService.setDeviceStatus(accessToken, houseId, roomId, avaId, Ava.SPEAKING, avaWindowExitMessage);
                    }

                    for (String windowId : windowIds) {
                        modelService.setDeviceStatus(accessToken, houseId, roomId, windowId, Window.MODE, Window.OPEN);
                    }
                }
            }
        }

        call911(occupantIds);
    }

    private void call911(Set<String> occupantIds) {
        System.out.print("");
        System.out.print("Hhh hello, 911? Reporting fire in house: '"
                + houseId + "', room: '" + roomId + "'. ");
        if(occupantIds.isEmpty()){
            System.out.println("This house has no occupants.");
        }else{
            System.out.println(occupantIds.size() + " occupants are in danger! Occupants are: ");
        }

        occupantIds.forEach(occupantId -> {
            System.out.println(occupantId);
        });
        System.out.print("");
    }
}
