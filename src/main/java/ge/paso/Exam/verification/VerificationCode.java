package ge.paso.Exam.verification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_codes")
@Getter
@Setter
public class VerificationCode {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "verification_codes_seq", sequenceName = "verification_codes_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "verification_codes_seq")
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "user_id")
    private int userId;

    public VerificationCode(String code, LocalDateTime issuedAt, LocalDateTime expiresAt, LocalDateTime confirmedAt, int userId) {
        this.code = code;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.confirmedAt = confirmedAt;
        this.userId = userId;
    }

    public VerificationCode() {

    }
}
