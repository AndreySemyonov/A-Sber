package ru.astondevs.asber.userservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

/**
 * Entity that stores information about client.
 * Binds with {@link Client}, {@link Verification}.
 */
@Table(name = Contacts.TABLE_NAME)
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {

    public static final String TABLE_NAME = "db_contacts";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @OneToOne(mappedBy = "contacts",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Verification verification;

    @ColumnDefault("true")
    @Column(name = "sms_notification_enabled")
    private boolean smsNotificationEnabled;

    @ColumnDefault("true")
    @Column(name = "push_notification_enabled")
    private boolean pushNotificationEnabled;

    @Email
    @Column(name = "email")
    private String email;

    @ColumnDefault("false")
    @Column(name = "email_subscription_enabled")
    private boolean emailSubscriptionEnabled;

    @Column(name = "mobile_phone", nullable = false)
    private String mobilePhone;

    /**
     * Method for getting verification
     *
     * @return {@link Verification}
     */
    public Verification getVerification() {
        if (verification == null) {
            verification = Verification.builder().contacts(this).build();
        }
        return verification;
    }

    /**
     * Method keeps association between client and userProfile in sync state
     *
     * @param verification {@link Verification}
     */
    public void setVerification(Verification verification) {
        if (this.verification != null) {
            this.verification.setContacts(null);
        } else {
            this.verification = verification;
        }
    }
}
