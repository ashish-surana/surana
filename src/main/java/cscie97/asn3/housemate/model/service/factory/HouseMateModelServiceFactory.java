package cscie97.asn3.housemate.model.service.factory;

import cscie97.asn3.housemate.model.service.HouseMateModelService;
import cscie97.asn3.housemate.model.service.impl.HouseMateModelServiceImpl;

/**
 * A factory for getting an instance of HouseMateModelService.
 */
public class HouseMateModelServiceFactory {


    public HouseMateModelService getService() {
        return HouseMateModelServiceImpl.getInstance();
    }
}
