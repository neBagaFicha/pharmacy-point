package org.baga.pharmacypoint.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "Продукты")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Код_продукта")
    private Integer id;

    @Column(name = "Название")
    private String productName;

    @Column(name = "Цена")
    private BigDecimal productPrice;

    @Column(name = "Остаток")
    private Integer productReminder;

    @Column(name = "Описание")
    private String productDescription;

    @Column(name = "Категория")
    private String productCategory;
}
