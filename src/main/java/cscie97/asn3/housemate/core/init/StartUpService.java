package cscie97.asn3.housemate.core.init;

import cscie97.asn3.housemate.controller.service.HouseMateControllerService;
import cscie97.asn3.housemate.controller.service.factory.HouseMateControllerServiceFactory;
import cscie97.asn3.housemate.core.init.exception.InitializationException;
import cscie97.asn3.housemate.entitlement.AccessToken;
import cscie97.asn3.housemate.entitlement.credential.PasswordCredential;
import cscie97.asn3.housemate.entitlement.exception.EntitlementServiceException;
import cscie97.asn3.housemate.entitlement.exception.EntityExistsException;
import cscie97.asn3.housemate.entitlement.service.HouseMateEntitlementService;
import cscie97.asn3.housemate.entitlement.service.factory.HouseMateEntitlementServiceFactory;
import cscie97.asn3.housemate.model.service.HouseMateModelService;
import cscie97.asn3.housemate.model.service.factory.HouseMateModelServiceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

/**
 * This class is used to start up the HouseMate automation system.
 * Failure to call this class's init() method during system start up, will result in unpredictable results.
 */
public class StartUpService {


    private static final String INITIAL_PERMISSIONS = "init/initial_permissions.properties";

    private static final String ADMIN_ROLE_ID = "admin_role";
    private static final String ADMIN_ROLE_NAME = "Admin Role";
    private static final String ADMIN_ROLE_DESCRIPTION = "Admin Role";
    private static final String ADMIN_USER_ID = "admin";
    private static final String ADMIN_USER_NAME = "HouseMate Automation System Administrator";

    private static PasswordCredential adminPassword;


    /**
     * This method registers HouseMateControllerService instance to HouseMateModelService.
     * for listening to device status changes.
     */
    public static void init() throws InitializationException {
        try {
            HouseMateEntitlementServiceFactory entitlementServiceFactory = new HouseMateEntitlementServiceFactory();
            HouseMateEntitlementService entitlementService = entitlementServiceFactory.getService();

            initializeEntitlements(entitlementService);

            HouseMateModelServiceFactory modelServiceFactory = new HouseMateModelServiceFactory();
            HouseMateModelService modelService = modelServiceFactory.getService();

            HouseMateControllerServiceFactory controllerServiceFactory = new HouseMateControllerServiceFactory();
            HouseMateControllerService controllerService = controllerServiceFactory.getService();

            AccessToken accessToken = entitlementService.login(adminPassword);
            modelService.registerListener(accessToken, controllerService);
        } catch (EntitlementServiceException e) {
            throw new InitializationException(e);
        }
    }

    private static void initializeEntitlements(HouseMateEntitlementService entitlementService) throws InitializationException {
        Properties properties = readProperties(INITIAL_PERMISSIONS);
        Set<String> permissionIds = properties.stringPropertyNames();
        try {
            //create admin user
            entitlementService.createUser(ADMIN_USER_ID, ADMIN_USER_NAME);

            //generate a random password for admin user
            String plainTextPassword = UUID.randomUUID().toString();
            adminPassword = new PasswordCredential(ADMIN_USER_ID, plainTextPassword);

            //Now, let's generate another password credential object for passing to entitlement service.
            PasswordCredential credential = new PasswordCredential(ADMIN_USER_ID, plainTextPassword);
            //set the password for admin user
            entitlementService.setUserCredential(ADMIN_USER_ID, credential);

            //create admin role and required permissions. Add all permissions to admin role.
            entitlementService.createRole(ADMIN_ROLE_ID, ADMIN_ROLE_NAME, ADMIN_ROLE_DESCRIPTION);

            for(String permissionId : permissionIds){
                String permissionDescription = properties.getProperty(permissionId);
                entitlementService.createPermission(permissionId, permissionId, permissionDescription);

                entitlementService.addEntitlementToRole(ADMIN_ROLE_ID, permissionId);
            }

            //Add admin_role to admin user
            entitlementService.addRoleToUser(ADMIN_USER_ID, ADMIN_ROLE_ID);
        } catch (EntityExistsException e) {
            throw new InitializationException(e);
        } catch (EntitlementServiceException e) {
            throw new InitializationException(e);
        }

    }

    private static Properties readProperties(String propertyFile) throws InitializationException {
        InputStream inputStream = null;
        Properties props = new Properties();
        try {
            inputStream = StartUpService.class.getClassLoader().getResourceAsStream(propertyFile);

            if(inputStream == null){
                throw new InitializationException("Cannot find file: '"+ propertyFile + "'");
            }

            props.load(inputStream);

        } catch (IOException e) {
            throw new InitializationException(e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new InitializationException(e);
                }
            }
        }
        return props;
    }

    public static PasswordCredential getAdminPassword() {
        return adminPassword;
    }
}
