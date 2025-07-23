package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Role;
import model.User;
import util.JpaUtility;

import java.util.List;
import java.util.Optional;

public class JpaUserRepository implements UserRepository{
    @Override
    public User save(User user) {
        EntityManager em = JpaUtility.getEntityManager();
        EntityTransaction tm = em.getTransaction();
        try{
            tm.begin();
            if(em.find(User.class, user.getUserId())==null){
                em.persist(user);
            }else{
                em.merge(user);
            }
            tm.commit();
            return user;
        }catch (Exception e){
            if(tm.isActive()){
                tm.rollback();
            }
        }finally {
            em.close();
        }
        return null;
    }

    @Override
    public Optional<User> findById(String userId) {
        EntityManager em = JpaUtility.getEntityManager();
        try(em){
            User user = em.find(User.class, userId);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public void deleteById(String userId) {
        EntityManager em = JpaUtility.getEntityManager();
        EntityTransaction tm = em.getTransaction();
        try{
            tm.begin();
            User user = em.find(User.class, userId);
            if(user!=null){
                em.remove(user);
            }
            tm.commit();
        }catch (Exception e) {
            if(tm.isActive()){
                tm.rollback();
            }
        }finally{
            em.close();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username",User.class);
            return Optional.ofNullable(query.getSingleResult());
        }finally{
            em.close();
        }
    }

    @Override
    public List<User> findByRole(Role role) {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.role = :role",User.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            User user = em.find(User.class, username);
            if(user!=null){
                return true;
            }
        }finally{
            em.close();
        }
        return false;
    }

    @Override
    public long countUsers() {
        EntityManager em = JpaUtility.getEntityManager();
        try{
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u", Long.class);
            return query.getSingleResult();
        }finally{
            em.close();
        }
    }
}
