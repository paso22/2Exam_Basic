package ge.paso.Exam.verification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodesRepository extends JpaRepository<VerificationCode, Integer> {

    Optional<VerificationCode>  findByCode(String code);

}
