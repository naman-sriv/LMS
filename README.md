# LMS

Rationale : I wanted to build a library management System CLI app to get hands on with latest java features

Objective : is to build a highly concurrent CLI app 

## Key Features and Functions :
 - Catalogging
 - Circulation
 - Memeber Management
 - Inventory Management

## Functional Requirements :
- User Management :
    - Register user
    - User Login
    - View user Details
    - Update user details
    - delete User
- Book Management :
  - add new book
  - view book details
  - search books
  - list all books
  - update book
  - delete book
- Issue, Re-issue and Returning
  - system shall decrement available copies of issued book
  - system shall record the issuing transaction
  - system shall prevent issuing is no copies are available
  - shall enforce maximum number of books a member can issue
  - on return system shall increment available copies of the book
  - the system shall update the return transaction
  - system shall allow admin user to view all active transactions
- Authentication and Authorization :
  - members can search, borrow, return, view their own borrowed books
  - Admin can perform all librarian actions + manage user accounts.

## Non-Functional Requirements :
- Performance : 
  - All search and lookup operations shall complete within 2 seconds
  - system shall be able to handle up to 10,000 book entries and 1000 users without performance degradation
  - system should ideally support multiple users without data corruption.
- Security : 
  - user passwords shall be stored securely (hashed + salted)
  - system shall strictly enforce role based access control, preventing unauthorized actions
  - system shall validate all user inputs to prevent common security vulnerabilities.
  - sensitive user data should be protected from unauthorized access



