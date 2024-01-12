package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Author;
import org.hibernate.Session;
import repository.AuthorRepository;
import service.AuthorService;

import java.util.Optional;

public class AuthorServiceImpl extends BaseServiceImpl<Long, Author, AuthorRepository> implements AuthorService {
    protected final Session session;

    public AuthorServiceImpl(Session session, AuthorRepository repository) {
        super(session, repository);
        this.session = session;
    }

    @Override
    public Optional<Author>  getAuthorByUserNameAndPassword(String userName, String password) {
        return repository.getAuthorByUserNameAndPassword(userName, password);
    }
}