package repository;

import model.Role;
import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String userId);

    void deleteById(String userId);

    List<User> findAll();

    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);

    boolean existsByUsername(String username);

    long countUsers();

    // --- (Consider for future: Password Management - requires passwordHash in User entity) ---
    // void updatePassword(String userId, String newPasswordHash);
}
