package cscie97.asn4.housemate.entitlement;

import cscie97.asn4.housemate.entitlement.visitor.EntitlementVisitor;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * This class represents an access token for the associated user.
 * An access token expires in 60 minutes after its creation.
 * Access token identifier is randomly generated.
 */
public class AccessToken extends Entity{

    private static final int INACTIVE_TIME_OUT_MILLIS = 60 * 60* 1000;

    private Date lastAccessed;

    private final long expiryTime;

    private final String userId;

    public AccessToken(String userId){
        super(UUID.randomUUID().toString());

        assert userId!= null && !"".equals(userId) : "User identifier cannot be null or empty string";

        setLastAccessed();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, INACTIVE_TIME_OUT_MILLIS);
        expiryTime = calendar.getTimeInMillis();
        this.userId = userId;
    }

    @Override
    public String getIdentifier() {
        setLastAccessed();
        return super.getIdentifier();
    }

    @Override
    public void acceptVisitor(EntitlementVisitor visitor) {
        visitor.visitAccessToken(this);
    }

    /**
     * This method returns the user id associated with this access token.
     */
    public String getUserId() {
        return userId;
    }

    private void setLastAccessed() {
        Calendar now = Calendar.getInstance();
        this.lastAccessed = now.getTime();
    }

    /**
     * Returns the last time this token was accessed.
     */
    public Date getLastAccessed() {
        return lastAccessed;
    }

    /**
     *
     * @return the date at which this access token expires(d).
     */
    public Date getExpiryTime() {
        setLastAccessed();
        return new Date(expiryTime);
    }

    /**
     * Returns true if and only this token has expired.
     *
     */
    public boolean isExpired() {
        setLastAccessed();
        Calendar now = Calendar.getInstance();
        return now.getTimeInMillis() >= expiryTime;
    }
}
