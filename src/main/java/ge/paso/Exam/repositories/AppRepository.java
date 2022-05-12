package ge.paso.Exam.repositories;

import ge.paso.Exam.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface AppRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findByPassword(String password);
    @Query(value = "SELECT u.password FROM users u",
            nativeQuery = true)
    ArrayList<String> getAllPassword();
}
