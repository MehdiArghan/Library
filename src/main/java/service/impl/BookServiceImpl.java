package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Book;
import org.hibernate.Session;
import repository.BookRepository;
import service.BookService;

import java.util.Collection;
import java.util.Optional;

public class BookServiceImpl extends BaseServiceImpl<Long, Book, BookRepository> implements BookService {
    protected final Session session;

    public BookServiceImpl(Session session, BookRepository repository) {
        super(session, repository);
        this.session = session;
    }

    @Override
    public Optional<Book> findBookByTitle(String title) {
        return repository.findBookByTitle(title);
    }

    @Override
    public Collection<Book> getAllBookByAuthorId(Long id) {
        return repository.getAllBookByAuthorId(id);
    }
}
