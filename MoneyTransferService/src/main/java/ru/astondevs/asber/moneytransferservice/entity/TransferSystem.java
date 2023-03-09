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

/**
 * Entity that stores information about transfer system.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TransferSystem.TABLE_NAME)
public class TransferSystem {

    public static final String TABLE_NAME = "transfer_system";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_system_id")
    private Long transferSystemId;

    @NotNull
    @Column(name = "transfer_system_name")
    private String transferSystemName;
}
