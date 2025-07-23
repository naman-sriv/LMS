package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Book;
import util.JpaUtility;

import java.util.List;
import java.util.Optional;

public class JpaBookRepository implements BookRepository {

    @Override
    public Book save(Book book) {
        EntityManager em = JpaUtility.getEntityManager();
        EntityTransaction tm = em.getTransaction();
        try{
            tm.begin();
            Book managedBook = em.merge(book);
            tm.commit();
            return managedBook;
        }catch (Exception e) {
            System.err.println("Error saving book: " + e.getMessage());
            e.printStackTrace(); // <--- CRUCIAL FOR DEBUGGING
            if (tm != null && tm.isActive()) { // Check if tm is not null and active before rollback
                tm.rollback(); // Rollback transaction if it's active
                System.err.println("Transaction rolled back for book: " + book.getBookName());
            }
            // Re-throw as a runtime exception so calling code knows there was an error
            // This prevents silent failures.
            throw new RuntimeException("Failed to save book: " + e.getMessage(), e);
        }finally{
            em.close();
        }
    }

    @Override
    public Optional<Book> findById(String bookId) {
        try (EntityManager em = JpaUtility.getEntityManager()) {
            Book book = em.find(Book.class, bookId);
            return Optional.ofNullable(book);
        }
    }

    @Override
    public void deleteById(String bookId) {
        EntityManager em = JpaUtility.getEntityManager();
        EntityTransaction tm = em.getTransaction();
        try{
            tm.begin();
            Book book = em.find(Book.class, bookId);
            if(book!=null){
                em.remove(book);
            }
            tm.commit();
        }catch (Exception e){
            if(tm.isActive()){
                tm.rollback();
            }
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAll() {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> findByBookName(String bookName) {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.bookName = :bookName", Book.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.author = :author", Book.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> searchByBookNameOrAuthor(String searchTerm) {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            String likeTerm = "%"+searchTerm.toLowerCase()+"%";
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE LOWER(b.bookName) LIKE :searchTerm OR LOWER(b.author) LIKE :searchTerm", Book.class);
            query.setParameter("searchTerm",likeTerm);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAvailableBooks() {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.availableCopies > 0", Book.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public boolean decrementAvailableCopies(String bookId) {
        EntityManager em = JpaUtility.getEntityManager();
        EntityTransaction tm = em.getTransaction();
        try{
            tm.begin();
            Book book = em.find(Book.class, bookId);
            if(book!=null && book.getAvailableCopies()>0){
                book.setAvailableCopies(book.getAvailableCopies()-1);
                em.merge(book);
            }
            tm.commit();
            return true;
        }catch (Exception e){
            if(tm.isActive()){
                tm.rollback();
            }
        }finally{
            em.close();
        }
        return false;
    }

    @Override
    public boolean incrementAvailableCopies(String bookId) {
        EntityManager em = JpaUtility.getEntityManager();
        EntityTransaction tm = em.getTransaction();
        try{
            tm.begin();
            Book book = em.find(Book.class, bookId);
            if(book!=null && book.getAvailableCopies()<book.getTotalCopies()){
                book.setAvailableCopies(book.getAvailableCopies()+1);
                em.merge(book);
                tm.commit();
                return true;
            }
            tm.rollback();
            return false;
        }catch (Exception e){
            if(tm.isActive()){
                tm.rollback();
            }
        }finally{
            em.close();
        }
        return false;
    }

    @Override
    public boolean isBookAvailable(String bookId) {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            Book book = em.find(Book.class, bookId);
            if(book!=null && book.getAvailableCopies()>0){
                return true;
            }
        }finally {
            em.close();
        }
        return false;
    }

    @Override
    public long countBooks() {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Book b", Long.class);
            return query.getSingleResult();
        }finally{
            em.close();
        }
    }

    @Override
    public long countTotalAvailableCopies() {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Book b WHERE b.availableCopies > 0", Long.class);
            return query.getSingleResult();
        }finally{
            em.close();
        }
    }
}
