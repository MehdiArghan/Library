package view;

import base.repository.util.HibernateUtil;
import entity.Address;
import entity.Author;
import entity.Book;
import entity.Gender;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.impl.AuthorRepositoryImpl;
import repository.impl.BookRepositoryImpl;
import service.AuthorService;
import service.BookService;
import service.impl.AuthorServiceImpl;
import service.impl.BookServiceImpl;

import java.time.LocalDate;
import java.util.*;

public class View {
    private final Session session = HibernateUtil.getSessionFactory().openSession();

    private final AuthorRepository authorRepository = new AuthorRepositoryImpl(session);

    private final BookRepository bookRepository = new BookRepositoryImpl(session);

    private final AuthorService authorService = new AuthorServiceImpl(session, authorRepository);
    private final BookService bookService = new BookServiceImpl(session, bookRepository);
    private final Scanner scanner = new Scanner(System.in);
    private static Book book = new Book();
    private static Author author = new Author();


    public void mainMenu() {
        System.out.println("1-Sign Up");
        System.out.println("2-Login");
        System.out.println("3-Exit");

        try {
            System.out.println("please choose your option:");
            String input = scanner.next();
            if (!input.trim().isEmpty()) {
                switch (Integer.parseInt(input)) {
                    case 1 -> signUp();
                    case 2 -> login();
                    case 3 -> System.exit(0);
                    default -> {
                        System.out.println("invalid option");
                        mainMenu();
                    }
                }
            } else {
                System.out.println("input is empty");
                mainMenu();
            }
        } catch (NumberFormatException e) {
            System.err.println("Please enter the correct operation number");
            mainMenu();
        }
    }


    private void signUp() {
        try {
            System.out.println("FirstName:");
            String firstName = scanner.next();

            System.out.println("LastName:");
            String lastName = scanner.next();

            System.out.println("UserName:");
            String userName = scanner.next();

            System.out.println("password:");
            String password = scanner.next();

            System.out.println("Email:");
            String email = scanner.next();

            System.out.println("BirthDate:");
            String birthDate = scanner.next();

            System.out.println("Gender:");
            String gender = scanner.next();

            System.out.println("country:");
            String country = scanner.next();

            System.out.println("city:");
            String city = scanner.next();

            Author newAuthor = new Author(
                    firstName,
                    lastName,
                    userName,
                    password,
                    email,
                    LocalDate.parse(birthDate),
                    Gender.valueOf(gender),
                    new Address(city, country));

            authorService.save(newAuthor);
            author = newAuthor;
            mainMenu();
        } catch (Exception e) {
            System.out.println("The input value is not valid");
            signUp();
        }
    }

    private void login() {
        System.out.println("please enter your userName: ");
        String userName = scanner.next();
        System.out.println("please enter your password: ");
        String password = scanner.next();

        try {
            if (authorService.getAuthorByUserNameAndPassword(userName, password).isPresent()) {
                Optional<Author> authorOptional = authorService.getAuthorByUserNameAndPassword(userName, password);
                authorOptional.ifPresent(existingAuthor -> author = existingAuthor);
                home();
            }
        } catch (NoResultException e) {
            System.out.println("Author does not exist!");
            mainMenu();
        }
    }


    public void home() {
        try {
            System.out.println("Welcome  " + author.getUserName());
            System.out.println("1-insert book");
            System.out.println("2-remove book");
            System.out.println("3-update book");
            System.out.println("4-remove user");
            System.out.println("5-edit user");
            System.out.println("6-log out");
            System.out.println("please enter option:");
            String input = scanner.next();
            if (!input.trim().isEmpty()) {
                switch (Integer.parseInt(input)) {
                    case 1:
                        insertBook();
                        break;
                    case 2:
                        removeBook();
                        break;
                    case 3:
                        updateBook();
                        break;
                    case 4:
                        removeUser();
                        break;
                    case 5:
                        editUser();
                        break;
                    case 6:
                        author = null;
                        mainMenu();
                    default:
                        System.out.println("option not found");
                        home();
                        break;
                }
            } else {
                System.out.println("input invalid");
                home();
            }
        } catch (NumberFormatException e) {
            System.err.println("Please enter the correct operation number");
            home();
        }
    }

    private void insertBook() {
        System.out.println("title: ");
        String title = scanner.next();

        System.out.println("isbn: ");
        String isbn = scanner.next();

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setIsbn(isbn);
        newBook.setAuthor(author);
        author.getBooks().add(newBook);

        bookService.save(newBook);
        book = newBook;
        home();
    }

    private void removeBook() {
        Collection<Book> allBook = bookService.getAllBookByAuthorId(author.getId());
        allBook.forEach(books -> System.out.println(books.getId() + "  " + books.getTitle() + "  " + books.getIsbn()));

        System.out.println("Enter book title to remove : ");
        String existingTitle = scanner.next();

        try {
            if (bookService.findBookByTitle(existingTitle).isPresent()) {
                List<Book> bookList = new ArrayList<>(allBook);
                if (!bookList.isEmpty()) {
                    for (Book currentBook : bookList) {
                        if (currentBook.getTitle().equals(existingTitle)) {
                            author.getBooks().remove(currentBook);
                            bookService.remove(currentBook);
                            System.out.println("book removed successfully");
                            home();
                        }
                    }
                } else {
                    System.out.println("bookList is Empty");
                }
            }
        } catch (NoResultException e) {
            System.out.println("book not found");
            home();
        }
    }

    private void updateBook() {
        Collection<Book> allBook = bookService.getAllBookByAuthorId(author.getId());
        allBook.forEach(books -> System.out.println(books.getId() + "  " + books.getTitle() + "  " + books.getIsbn()));

        System.out.println("please enter your book for edit");
        String currentBook = scanner.next();

        try {
            if (bookService.findBookByTitle(currentBook).isPresent()) {
                List<Book> bookList = new ArrayList<>(allBook);
                if (!bookList.isEmpty()) {
                    for (Book existingBook : bookList) {
                        if (existingBook.getTitle().equals(currentBook)) {
                            System.out.println("title: ");
                            existingBook.setTitle(scanner.next());

                            System.out.println("isbn: ");
                            existingBook.setIsbn(scanner.next());

                            bookService.update(existingBook);
                            System.out.println("book edited successfully");
                            home();
                        }
                    }
                } else {
                    System.out.println("bookList is Empty");
                }
            }
        } catch (NoResultException e) {
            System.out.println("book not found");
            home();
        }
    }

    private void removeUser() {
        System.err.println("Are you sure you want to delete your account?");
        System.err.println("1-yes");
        System.err.println("2-NO");
        String input = scanner.next();
        try {
            if (!input.trim().isEmpty()) {
                switch (Integer.parseInt(input)) {
                    case 1 -> {
                        Collection<Book> allBook = bookService.getAllBookByAuthorId(author.getId());
                        List<Book> bookList = new ArrayList<>(allBook);
                        for (Book currentBook : bookList) {
                            author.getBooks().remove(currentBook);
                            bookService.remove(currentBook);
                        }
                        authorService.remove(author);
                        author = null;
                        book = null;
                        System.err.println("user deleted successfully");
                        mainMenu();
                    }
                    case 2 -> home();
                    default -> {
                        System.err.println("Please enter the correct operation number");
                        removeUser();
                    }
                }
            } else {
                System.err.println("Please enter the correct operation number");
                removeUser();
            }
        } catch (NumberFormatException e) {
            System.err.println("Please enter the correct operation number");
            removeUser();
        }

    }

    private void editUser() {
        System.err.println("Do you want to update the user?");
        System.err.println("1-yes");
        System.err.println("2-NO");
        String input = scanner.next();

        try {
            if (!input.trim().isEmpty()) {
                switch (Integer.parseInt(input)) {
                    case 1 -> informationUser();
                    case 2 -> home();
                    default -> {
                        System.err.println("Please enter the correct operation number");
                        editUser();
                    }
                }
            } else {
                System.err.println("Please enter the correct operation number");
                editUser();
            }
        } catch (NumberFormatException e) {
            System.err.println("please enter the correct operation number");
            editUser();
        }
    }

    private void informationUser() {
        try {
            System.out.println("FirstName:");
            author.setFirstName(scanner.next());

            System.out.println("LastName:");
            author.setLastName(scanner.next());

            System.out.println("UserName:");
            author.setUserName(scanner.next());

            System.out.println("password:");
            author.setPassword(scanner.next());

            System.out.println("Email:");
            author.setEmail(scanner.next());

            System.out.println("BirthDate:");
            author.setBrithDate(LocalDate.parse(scanner.next()));

            System.out.println("Gender:");
            author.setGender(Gender.valueOf(scanner.next()));

            System.out.println("country:");
            String country = scanner.next();

            System.out.println("city:");
            String city = scanner.next();
            author.setAddress(new Address(country, city));

            authorService.update(author);
            System.out.println("updated author successfully");
            home();
        } catch (Exception e) {
            System.out.println("The input value is not valid");
            informationUser();
        }
    }
}