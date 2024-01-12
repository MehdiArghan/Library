package service;

import base.service.BaseService;
import entity.Author;

import java.util.Optional;

public interface AuthorService extends BaseService<Long, Author> {
    Optional<Author> getAuthorByUserNameAndPassword(String userName, String password);
}