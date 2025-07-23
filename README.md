# LMS (Library Management System)

## Overview

LMS is a highly concurrent, command-line application for managing library operations. It is designed to be robust, performant, and secure, leveraging the latest Java features and best practices. The project demonstrates a modular, repository-driven architecture and focuses on both functional and non-functional requirements required for a production-grade library management system.

---

## Rationale

The motivation behind LMS is to build a practical CLI library management system while exploring contemporary Java concepts, including concurrency, JPA (Java Persistence API), modularity, and role-based security.

---

## Objective

- Provide a fully functional CLI-based library management solution
- Support high concurrency and data integrity for multiple users and transactions
- Ensure security and performance at scale

---

## Key Features and Functions

- **Cataloguing:** Manage book entries, view details, search, and maintain inventory
- **Circulation:** Issue, re-issue, and return books, enforcing business rules for borrowing
- **Member Management:** Register, update, and delete users, including role assignments
- **Inventory Management:** Track available and total copies, update inventory in real-time
- **Authentication & Authorization:** Role-based access (Admin vs Member), secure login

---

## Functional Requirements

### User Management

- Register new users
- User login
- View and update user details
- Delete users
- Role assignment (Admin, Member)

### Book Management

- Add new books to catalog
- View book details
- Search books by name or author
- List all books
- Update book information
- Delete books

### Transaction Management

- Issue, re-issue, and return books
- Decrement/increment available book copies on issue/return
- Record all transactions
- Prevent issuing if no copies are available
- Enforce maximum borrow limits for members
- Update transaction records on return
- Admins can view all active transactions

### Access Control

- Members: Search, borrow, return, and view their borrowed books
- Admins: All librarian actions plus user management

---

## Non-Functional Requirements

### Performance

- All search and lookup operations complete within 2 seconds
- System supports up to 10,000 books and 1,000 users without degradation
- Robust concurrency support for multiple users and transactions

### Security

- Passwords are stored securely (hashed and salted)
- Strict enforcement of role-based access control
- Input validation to prevent common vulnerabilities (e.g., SQL injection)
- Sensitive user data protected from unauthorized access

---

## Architecture & Concepts Used

- **Java Concurrency:** Ensures thread-safe operations for simultaneous users
- **JPA (Java Persistence API):** Data persistence for books, users, and transactions
- **Repository Pattern:** Decouples business logic from data access, enabling flexibility and testability
  - `UserRepository` / `JpaUserRepository`
  - `BookRepository` / `JpaBookRepository`
  - `TransactionRepository` / `JpaTransactionRepository`
- **Role-Based Security:** User roles (Admin, Member) enforced throughout
- **Error Handling:** Robust transaction management and rollback on failures
- **Modular Codebase:** Organized into clear packages (model, repository, util)
- **Input Validation:** All user inputs are validated
- **Logging & Debugging:** Error logging for critical actions and failures

---

## Data Model

The system is built around three core entities:

- **User** — Contains user details and role information.
- **Book** — Catalog information, inventory counts.
- **Transaction** — Issuing/returning records, user-book relationships.

### ER Diagram

![ER DIAGRAM](https://github.com/naman-sriv/LMS/blob/d37c615f96121ae3299886e047ec7c70169528d7/images/ER%20Diagram.png)

---

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/naman-sriv/LMS.git
   ```

2. **Build the project:**
   ```bash
   cd LMS
   ./gradlew build
   ```

3. **Run the CLI app:**
   ```bash
   java -jar build/libs/lms_cli.jar
   ```

---

## Contributing

Contributions are welcome! Please open issues or pull requests for bug fixes, enhancements, or new features.

---

## License

MIT License. See [LICENSE](LICENSE) for details.

---

## Contact

For any queries or support, please contact [naman-sriv](https://github.com/naman-sriv).
