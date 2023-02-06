package com.api.accounts.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "ACCOUNT")
@Data
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(
            name = "account_generator",
            sequenceName = "account_seq",
            allocationSize = 50
    )
    private Long accountId;

    private Long customerId;

    private BigDecimal balance;


}
