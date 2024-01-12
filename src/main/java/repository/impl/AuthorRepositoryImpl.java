package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Author;
import org.hibernate.Session;
import repository.AuthorRepository;

import java.util.Optional;

public class AuthorRepositoryImpl extends BaseRepositoryImpl<Long, Author> implements AuthorRepository {
    protected final Session session;

    public AuthorRepositoryImpl(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public Class<Author> getEntityClass() {
        return Author.class;
    }

    @Override
    public Optional<Author> getAuthorByUserNameAndPassword(String userName, String password) {
        return Optional.ofNullable(session.createQuery("from Author where userName=:user and password=:password", Author.class)
                .setParameter("user", userName)
                .setParameter("password", password)
                .getSingleResult());
    }
}