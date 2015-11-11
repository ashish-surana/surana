package cscie97.asn3.housemate.core.init;

import cscie97.asn3.housemate.controller.service.HouseMateControllerService;
import cscie97.asn3.housemate.controller.service.factory.HouseMateControllerServiceFactory;
import cscie97.asn3.housemate.model.service.HouseMateModelService;
import cscie97.asn3.housemate.model.service.factory.HouseMateModelServiceFactory;

/**
 * This class is used to start up the HouseMate automation system.
 * Failure to call this class's init() method during system start up, will result in unpredictable results.
 */
public class StartUpService {

    /**
     * This method registers HouseMateControllerService instance to HouseMateModelService.
     * for listening to device status changes.
     */
    public static void init(){
        String authToken = null;

        HouseMateModelServiceFactory modelServiceFactory = new HouseMateModelServiceFactory();
        HouseMateModelService modelService = modelServiceFactory.getService();

        HouseMateControllerServiceFactory controllerServiceFactory = new HouseMateControllerServiceFactory();
        HouseMateControllerService controllerService = controllerServiceFactory.getService();
        modelService.registerListener(authToken, controllerService);
    }
}
