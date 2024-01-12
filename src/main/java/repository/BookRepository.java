package repository;

import base.repository.BaseRepository;
import entity.Book;

import java.util.Collection;
import java.util.Optional;

public interface BookRepository extends BaseRepository<Long, Book> {
    Optional<Book> findBookByTitle(String title);
    Collection<Book> getAllBookByAuthorId(Long id);
}