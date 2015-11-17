package cscie97.asn4.housemate.model.service.exception;

import cscie97.asn4.housemate.model.Entity;

/**
 * This exception is thrown to indicate that the given status key, or value
 * are invalid for the underlying entity. This exception class provides access
 * to the status key and value that were attempted to be set, as well as access
 * to the underlying entity.
 */
public class InvalidStatusException extends EntityException {

    private final String statusKey;
    private final String statusValue;

    public InvalidStatusException(Entity entity, String statusKey, String statusValue) {
        super("Given status is invalid for the underlying entity", entity);
        this.statusKey = statusKey;
        this.statusValue = statusValue;
    }

    public String getStatusKey() {
        return statusKey;
    }

    public String getStatusValue() {
        return statusValue;
    }

    @Override
    public Entity getEntity() {
        return (Entity) super.getEntity();
    }
}
