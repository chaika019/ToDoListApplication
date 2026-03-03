package by.chaika19.repository;

import by.chaika19.entity.User;
import by.chaika19.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailIgnoreCase(String email);
    List<User> findAllByRoleInOrderById(Iterable<UserRole> roles);

    @Modifying
    @Query("UPDATE User SET role = :role WHERE id = :id")
    void updateRoll(int id, @Param("role") UserRole newRole);
}
