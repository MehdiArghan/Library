package base.repository.impl;

import base.entity.BaseEntity;
import base.repository.BaseRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public abstract class BaseRepositoryImpl<ID extends Serializable, Entity extends BaseEntity<ID>>
        implements BaseRepository<ID, Entity> {
    protected final Session session;

    public abstract Class<Entity> getEntityClass();

    @Override
    public void save(Entity entity) {
        session.persist(entity);
    }

    @Override
    public void update(Entity entity) {
        session.merge(entity);
    }

    @Override
    public void remove(Entity entity) {
        session.remove(entity);
    }

    @Override
    public Optional<Entity> findById(ID id) {
        return Optional.ofNullable(session.find(getEntityClass(), id));
    }

    @Override
    public Collection<Entity> findAll() {
        return session.createQuery("from " + getEntityClass().getSimpleName(), getEntityClass())
                .getResultList();
    }
}