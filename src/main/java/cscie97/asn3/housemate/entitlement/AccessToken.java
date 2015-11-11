package cscie97.asn3.housemate.entitlement;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by assurana on 11/3/2015.
 */
public class AccessToken {

    private static final int INACTIVE_TIME_OUT_MILLIS = 60 * 1000;

    private Date lastAccessed;

    private final long expiryTime;

    private final String identifier;

    public AccessToken(String identifier){
        assert identifier!= null && !"".equals(identifier) : "Access token identifier cannot be null or empty string";

        this.identifier = identifier;
        setLastAccessed();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, INACTIVE_TIME_OUT_MILLIS);
        expiryTime = calendar.getTimeInMillis();
    }

    public String getIdentifier() {
        setLastAccessed();
        return identifier;
    }

    private void setLastAccessed() {
        Calendar now = Calendar.getInstance();
        this.lastAccessed = now.getTime();
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public Date getExpiryTime() {
        setLastAccessed();
        return new Date(expiryTime);
    }

    public boolean isExpired() {
        setLastAccessed();
        Calendar now = Calendar.getInstance();
        return now.getTimeInMillis() >= expiryTime;
    }
}
