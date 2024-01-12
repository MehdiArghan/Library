package repository;

import base.repository.BaseRepository;
import entity.Author;

import java.util.Optional;

public interface AuthorRepository extends BaseRepository<Long, Author> {
    Optional<Author> getAuthorByUserNameAndPassword(String userName, String password);
}