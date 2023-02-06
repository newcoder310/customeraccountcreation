package com.api.transactions.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION")
@Data
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "column_generator")
    @SequenceGenerator(
            name = "column_generator",
            sequenceName = "column_seq",
            allocationSize = 50
    )
    private Long transactionId;

    private long customerId;

    private long accountId;

    private BigDecimal amount;

    private LocalDateTime date;
}
