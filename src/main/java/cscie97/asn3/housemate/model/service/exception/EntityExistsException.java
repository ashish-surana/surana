package cscie97.asn3.housemate.model.service.exception;

import cscie97.asn3.housemate.model.Entity;

/**
 * This exception is thrown to indicate that an entity cannot be created,
 * since this operation will result in duplicate entities with same id.
 */
public class EntityExistsException extends EntityException{

    public EntityExistsException(Entity entity){
        super("An entity with given identifier already exists", entity);
    }

    @Override
    public Entity getEntity() {
        return (Entity) super.getEntity();
    }

}
