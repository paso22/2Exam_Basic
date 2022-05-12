package ge.paso.Exam.verification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VerificationServiceImpl implements VerificationService{

    private final VerificationCodesRepository verificationCodesRepository;

    @Override
    public void addCode(VerificationCode verificationCode) {
        verificationCodesRepository.save(verificationCode);
    }

    @Override
    public void setCodeDetails(VerificationCode vc) {
        VerificationCode verificationCode = verificationCodesRepository.findByCode(vc.getCode())
                .orElse(null);
        if(verificationCode != null) {
            verificationCode.setConfirmedAt(LocalDateTime.now());
            verificationCodesRepository.save(verificationCode);
        }
    }
}
