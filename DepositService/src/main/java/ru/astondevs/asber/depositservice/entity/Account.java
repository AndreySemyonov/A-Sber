package ru.astondevs.asber.depositservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.astondevs.asber.depositservice.entity.enums.CurrencyCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entity that stores information about client accounts.
 * Binds with set of {@link Agreement} and set of {@link Card}.
 */
@Table(name = Account.TABLE_NAME)
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    public static final String TABLE_NAME = "account";

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;

    @Size(max = 30)
    @NotNull
    @Column(name = "account_number")
    private String accountNumber;

    @NotNull
    @Column(name = "client_id")
    private UUID clientId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code")
    private CurrencyCode currencyCode;

    @NotNull
    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @NotNull
    @Column(name = "open_date")
    private LocalDate openDate;

    @NotNull
    @Column(name = "close_date")
    private LocalDate closeDate;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @Size(max = 30)
    @Column(name = "salary_project")
    private String salaryProject;

    @Column(name = "blocked_sum")
    private BigDecimal blockedSum;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "accountId", cascade = CascadeType.ALL)
    private  Set<Agreement> agreements = new HashSet<>();

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "accountId", cascade = CascadeType.ALL)
    private Set<Card> cards = new HashSet<>();

    /**
     * Methods that synchronizes both ends bidirectional association whenever  agreement is added.
     *
     * @param agreement bidirectional association
     */
    public void addAgreement(Agreement agreement) {
        agreements.add(agreement);
        agreement.setAccountId(this);
    }

    /**
     * Methods that synchronizes both ends bidirectional association whenever agreement is removed.
     *
     * @param agreement bidirectional association
     */
    public void removeAgreement(Agreement agreement) {
        agreements.remove(agreement);
        agreement.setAccountId(null);
    }

    /**
     * Methods that synchronizes both ends bidirectional association whenever card is added.
     *
     * @param card bidirectional association
     */
    public void addCard(Card card) {
        cards.add(card);
        card.setAccountId(this);
    }

    /**
     * Methods that synchronizes both ends bidirectional association whenever card is removed.
     *
     * @param card bidirectional association
     */
    public void removeCard(Card card) {
        cards.remove(card);
        card.setAccountId(null);
    }
}
