package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Book;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.BookRepository;

import java.util.Collection;
import java.util.Optional;

public class BookRepositoryImpl extends BaseRepositoryImpl<Long, Book> implements BookRepository {
    protected final Session session;

    public BookRepositoryImpl(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public Class<Book> getEntityClass() {
        return Book.class;
    }

    @Override
    public Optional<Book> findBookByTitle(String title) {
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Book> criteriaQuery = builder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);

        criteriaQuery.where(builder.equal(bookRoot.get("title"), title));

        TypedQuery<Book> bookTypedQuery = session.createQuery(criteriaQuery);

        return Optional.ofNullable(bookTypedQuery.getSingleResult());
    }

    @Override
    public Collection<Book> getAllBookByAuthorId(Long id) {
        String hql = "from Book b where b.author.id =: id";
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}