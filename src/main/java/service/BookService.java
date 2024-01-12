package service;

import base.service.BaseService;
import entity.Book;

import java.util.Collection;
import java.util.Optional;

public interface BookService extends BaseService<Long, Book> {
    Optional<Book> findBookByTitle(String title);
    Collection<Book> getAllBookByAuthorId(Long id);
}