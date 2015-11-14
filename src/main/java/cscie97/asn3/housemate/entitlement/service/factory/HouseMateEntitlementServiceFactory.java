package cscie97.asn3.housemate.entitlement.service.factory;

import cscie97.asn3.housemate.entitlement.service.HouseMateEntitlementService;
import cscie97.asn3.housemate.entitlement.service.impl.HouseMateEntitlementServiceImpl;

/**
 * This class can be used to create an instance of type HouseMateEntitlementService.
 */
public class HouseMateEntitlementServiceFactory {

    /**
     * This methods returns an instance of type HouseMateEntitlementService.
     */
    public HouseMateEntitlementService getService(){
        return HouseMateEntitlementServiceImpl.getInstance();
    }
}
