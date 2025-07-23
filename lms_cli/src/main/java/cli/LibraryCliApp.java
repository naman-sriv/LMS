package cli;

import model.Book;
import repository.BookRepository;
import repository.JpaBookRepository;
import util.JpaUtility;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class LibraryCliApp {

    private final BookRepository bookRepository;
    private final Scanner scanner;

    public LibraryCliApp() {
        this.bookRepository = new JpaBookRepository();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Library Management System!");

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add New Book");
            System.out.println("2. List All Books");
            System.out.println("3. Search Books");
            System.out.println("4. Check Book Availability");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        addNewBook();
                        break;
                    case "2":
                        listAllBooks();
                        break;
                    case "3":
                        searchBooks();
                        break;
                    case "4":
                        checkBookAvailability();
                        break;
                    case "5":
                        running = false;
                        System.out.println("Exiting Library System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                // For a CLI, you might print more detailed stack trace in development
                // e.printStackTrace();
            }
        }
        scanner.close();
    }

    private void addNewBook() {
        System.out.println("\n--- Add New Book ---");
        String bookId = UUID.randomUUID().toString(); // Generate a unique ID for the book
        System.out.print("Enter Book Name: ");
        String bookName = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Total Copies: ");
        int totalCopies = Integer.parseInt(scanner.nextLine());

        Book newBook = new Book(bookId, bookName, author, totalCopies, totalCopies); // availableCopies = totalCopies initially
        bookRepository.save(newBook);
        System.out.println("Book '" + bookName + "' added successfully with ID: " + bookId);
    }

    private void listAllBooks() {
        System.out.println("\n--- All Books ---");
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        books.forEach(book -> System.out.println(
                "ID: " + book.getBookId() +
                        ", Title: " + book.getBookName() +
                        ", Author: " + book.getAuthor() +
                        ", Available: " + book.getAvailableCopies() + "/" + book.getTotalCopies()
        ));
    }

    private void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.print("Enter search term (Book Name or Author): ");
        String searchTerm = scanner.nextLine();

        List<Book> foundBooks = bookRepository.searchByBookNameOrAuthor(searchTerm);
        if (foundBooks.isEmpty()) {
            System.out.println("No books found matching '" + searchTerm + "'.");
            return;
        }
        System.out.println("Books matching '" + searchTerm + "':");
        foundBooks.forEach(book -> System.out.println(
                "ID: " + book.getBookId() +
                        ", Title: " + book.getBookName() +
                        ", Author: " + book.getAuthor() +
                        ", Available: " + book.getAvailableCopies() + "/" + book.getTotalCopies()
        ));
    }

    private void checkBookAvailability() {
        System.out.println("\n--- Check Book Availability ---");
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();

        bookRepository.findById(bookId).ifPresentOrElse(
                book -> {
                    System.out.println("Book: " + book.getBookName() + " by " + book.getAuthor());
                    System.out.println("Total Copies: " + book.getTotalCopies());
                    System.out.println("Available Copies: " + book.getAvailableCopies());
                    if (book.getAvailableCopies() > 0) {
                        System.out.println("Status: AVAILABLE");
                    } else {
                        System.out.println("Status: NOT AVAILABLE");
                    }
                },
                () -> System.out.println("Book with ID '" + bookId + "' not found.")
        );
    }


    public static void main(String[] args) {
        // 1. Initialize JPA (EntityManagerFactory) at application startup
        JpaUtility.init();

        // 2. Start your CLI application
        LibraryCliApp app = new LibraryCliApp();
        app.start();

        // 3. Shut down JPA (EntityManagerFactory) cleanly when the application exits
        JpaUtility.shutdown();
    }
}
