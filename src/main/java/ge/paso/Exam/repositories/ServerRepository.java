package ge.paso.Exam.repositories;

import ge.paso.Exam.entities.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Integer> {

    Optional<Server> findByName(String name);
    Optional<List<Server>> findByStatus(String status);
    Optional<List<Server>> findByUserId(int userId);


}
