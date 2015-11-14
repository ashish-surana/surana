package cscie97.asn3.housemate.entitlement;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * Created by assurana on 11/3/2015.
 */
public class AccessToken extends Entity{

    private static final int INACTIVE_TIME_OUT_MILLIS = 60 * 60* 1000;

    private Date lastAccessed;

    private final long expiryTime;

    public AccessToken(String userId){
        super(UUID.randomUUID().toString());

        assert userId!= null && !"".equals(userId) : "User identifier cannot be null or empty string";

        setLastAccessed();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, INACTIVE_TIME_OUT_MILLIS);
        expiryTime = calendar.getTimeInMillis();
    }

    @Override
    public String getIdentifier() {
        setLastAccessed();
        return super.getIdentifier();
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
