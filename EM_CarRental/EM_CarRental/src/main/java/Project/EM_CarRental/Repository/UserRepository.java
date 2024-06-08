package Project.EM_CarRental.Repository;

import Project.EM_CarRental.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);


    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsernameOrEmail(String username, String email);

}
