package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private Date issuedDate;

    private Date dueDate;

    private Date returnedDate;

    private Status status;

    public Transaction() {}

    public Transaction(String transactionId, Book book, User user, Date issuedDate, Date dueDate, Date returnedDate, Status status) {
        this.transactionId = transactionId;
        this.book = book;
        this.user = user;
        this.issuedDate = issuedDate;
        this.returnedDate = returnedDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", bookId='" + book + '\'' +
                ", userId='" + user + '\'' +
                ", issuedDate=" + issuedDate +
                ", dueDate=" + dueDate +
                ", returnedDate=" + returnedDate +
                ", status=" + status +
                '}';
    }
}
