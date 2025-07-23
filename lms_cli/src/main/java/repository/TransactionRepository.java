package repository;

import model.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(String transactionId);

    void deleteById(String transactionId);

    List<Transaction> findAll();

    List<Transaction> findByUserId(String userId);

    List<Transaction> findByBookId(String bookId);

    List<Transaction> findActiveTransactionsByUserId(String userId);

    List<Transaction> findActiveTransactionsByBookId(String bookId);

    List<Transaction> findOverdueTransactions(Date currentDate);

    Optional<Transaction> findActiveTransactionByUserIdAndBookId(String userId, String bookId);

    long countActiveTransactionsByUserId(String userId);

    long countTransactionsByBookId(String bookId);
}
