package com.lucasduarte.cupon_api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Cupom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 6)
    private String code;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @DecimalMin(value = "0.5", message = "O valor mínimo do desconto é 0.5")
    @Column(nullable = false)
    private Double discountValue;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private boolean published = false;

    @Column(nullable = false)
    private boolean redeemed = false;

    @Column(nullable = false)
    private boolean deleted = false;

}
