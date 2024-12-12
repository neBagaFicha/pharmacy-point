package org.baga.pharmacypoint.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "Скидки")
@Data
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Код_скидки")
    private Integer id;

    @Column(name = "Название")
    private String discountName;

    @Column(name = "Условия_применения")
    private String discountTermsOfUse;

    @Column(name = "Дата_начала")
    private LocalDate discountStartDate;

    @Column(name = "Процент_скидки")
    private Integer discountPercent;
}
