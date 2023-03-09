package ru.astondevs.asber.moneytransferservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entity that stores information about phone code.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = PhoneCode.TABLE_NAME)
public class PhoneCode {

    public static final String TABLE_NAME = "phone_code";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_code")
    private Integer phoneCode;

    @NotNull
    @Column(name = "country_name")
    private String countryName;

    @NotNull
    @Column(name = "available")
    private Boolean available;

    @NotNull
    @Column(name = "old_code")
    private Integer oldCode;

    @NotNull
    @Column(name = "update_at")
    private LocalDate updateAt;
}
