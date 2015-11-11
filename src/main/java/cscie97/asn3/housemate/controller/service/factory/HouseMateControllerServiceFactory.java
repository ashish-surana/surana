package cscie97.asn3.housemate.controller.service.factory;

import cscie97.asn3.housemate.controller.service.HouseMateControllerService;
import cscie97.asn3.housemate.controller.service.impl.HouseMateControllerServiceImpl;

/**
 * This class is used as a factory for creating HouseMateControllerService objects.
 */
public class HouseMateControllerServiceFactory {

    /**
     * This method returns an object of type HouseMateControllerService.
     */
    public HouseMateControllerService getService() {
        return HouseMateControllerServiceImpl.getInstance();
    }
}
