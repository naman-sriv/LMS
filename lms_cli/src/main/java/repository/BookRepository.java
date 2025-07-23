package repository;

import model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(String bookId);

    void deleteById(String bookId);

    List<Book> findAll();

    List<Book> findByBookName(String bookName); // Changed from getBookByName to align with common JPA naming

    List<Book> findByAuthor(String author); // Changed from getBooksByAuthor

    List<Book> searchByBookNameOrAuthor(String searchTerm);

    List<Book> findAvailableBooks();

    boolean decrementAvailableCopies(String bookId);

    boolean incrementAvailableCopies(String bookId);

    boolean isBookAvailable(String bookId);

    long countBooks();

    long countTotalAvailableCopies();

}
