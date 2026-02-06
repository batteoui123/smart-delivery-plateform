package labs.pm.userservice.repo;

import labs.pm.userservice.entities.DriverAvailability;
import labs.pm.userservice.entities.Role;
import labs.pm.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepositoty extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("select u from User u where u.role=:role and u.driverAvailability=:availability")
    List<User> findAvailableDrivers(@Param("role") Role role, @Param("availability") DriverAvailability driverAvailability);
}
