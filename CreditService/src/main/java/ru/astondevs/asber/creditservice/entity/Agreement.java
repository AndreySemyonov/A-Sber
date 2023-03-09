package ru.astondevs.asber.creditservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

/**
 * Entity that stores information about agreement for credit. Binds with {@link Credit}.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Agreement.TABLE_NAME)
public class Agreement {

    public static final String TABLE_NAME = "agreement";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "credit_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_agreement_on_credit"))
    private Credit credit;

    @NotNull
    @Column(name = "agreement_number", length = 20)
    private String agreementNumber;

    @NotNull
    @Column(name = "agreement_date")
    private LocalDate agreementDate;

    @NotNull
    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @NotNull
    @Column(name = "responsible_specialist", length = 20)
    private String responsibleSpecialist;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;
}
