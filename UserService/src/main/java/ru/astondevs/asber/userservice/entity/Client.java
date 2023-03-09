package ru.astondevs.asber.userservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import ru.astondevs.asber.userservice.entity.enums.ClientStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity that stores information about client.
 * Binds with {@link Contacts}, {@link UserProfile}, {@link Fingerprint}, {@link ClientStatus}.
 */
@Table(name = Client.TABLE_NAME)
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    public static final String TABLE_NAME = "db_client";

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Contacts contacts;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Fingerprint fingerprint;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "sur_name")
    private String surName;

    @Column(name = "country_of_residence", nullable = false)
    private String countryOfResidence;

    @UpdateTimestamp
    @Column(name = "date_accession")
    private LocalDateTime dateAccession;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_status")
    private ClientStatus clientStatus;

    /**
     * Method keeps association between client and contacts in sync state
     *
     * @param contacts {@link Contacts}
     */
    public void setContacts(Contacts contacts) {
        contacts.setClient(this);
        this.contacts = contacts;
    }

    /**
     * Method keeps association between client and userProfile in sync state
     *
     * @param userProfile {@link UserProfile}
     */
    public void setUserProfile(UserProfile userProfile) {
        userProfile.setClient(this);
        this.userProfile = userProfile;
    }

    /**
     * Method keeps association between client and fingerprint in sync state
     *
     * @param fingerprint {@link Fingerprint}
     */
    public void setFingerprint(Fingerprint fingerprint) {
        fingerprint.setClient(this);
        this.fingerprint = fingerprint;
    }
}
