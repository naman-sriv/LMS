package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Transaction;
import util.JpaUtility;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JpaTransactionRepository implements TransactionRepository{
    @Override
    public Transaction save(Transaction transaction) {
        EntityManager em = JpaUtility.getEntityManager();
        EntityTransaction tm = em.getTransaction();
        try{
            tm.begin();
            if(em.find(Transaction.class, transaction.getTransactionId())==null){
                em.persist(transaction);
                tm.commit();
                return transaction;
            }else{
                em.merge(transaction);
            }
        }catch (Exception e){
            if(tm.isActive()){
                tm.rollback();
            }
        }finally{
            em.close();
        }
        return null;
    }

    @Override
    public Optional<Transaction> findById(String transactionId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(String transactionId) {

    }

    @Override
    public List<Transaction> findAll() {
        return List.of();
    }

    @Override
    public List<Transaction> findByUserId(String userId) {
        return List.of();
    }

    @Override
    public List<Transaction> findByBookId(String bookId) {
        return List.of();
    }

    @Override
    public List<Transaction> findActiveTransactionsByUserId(String userId) {
        return List.of();
    }

    @Override
    public List<Transaction> findActiveTransactionsByBookId(String bookId) {
        return List.of();
    }

    @Override
    public List<Transaction> findOverdueTransactions(Date currentDate) {
        return List.of();
    }

    @Override
    public Optional<Transaction> findActiveTransactionByUserIdAndBookId(String userId, String bookId) {
        return Optional.empty();
    }

    @Override
    public long countActiveTransactionsByUserId(String userId) {
        return 0;
    }

    @Override
    public long countTransactionsByBookId(String bookId) {
        return 0;
    }
}
