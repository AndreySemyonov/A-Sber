package ru.astondevs.asber.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Entity that stores information about client.
 * Binds with {@link Contacts}.
 */
@Table(name = Verification.TABLE_NAME)
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Verification {

    public static final String TABLE_NAME = "db_verification";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contact", referencedColumnName = "id")
    private Contacts contacts;

    @Column(name = "sms_verification_code_6")
    private String smsVerificationCode;

    @Column(name = "sms_code_expiration")
    private LocalDateTime smsCodeExpiration;

    @Column(name = "block_expiration")
    private LocalDateTime blockExpiration;

    @Column(name = "verification_attempts")
    private int verificationAttempts;

    /**
     * Method increments verification attempts.
     */
    public void incrementAttempts() {
        verificationAttempts++;
    }

    /**
     * Method gets remaining block time in seconds
     */
    public long getRemainingBlockSeconds() {
        return blockExpiration.toEpochSecond(ZoneOffset.UTC)
                - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }
}
