package base.service;

import base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface BaseService<ID extends Serializable, Entity extends BaseEntity<ID>> {
    void save(Entity entity);

    void update(Entity entity);

    void remove(Entity entity);

    Optional<Entity> findById(ID id);

    Collection<Entity> findAll();
}
