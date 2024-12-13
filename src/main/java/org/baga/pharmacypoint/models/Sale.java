package org.baga.pharmacypoint.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Продажи")
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Код_продажи")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Код_продукта", referencedColumnName = "Код_продукта")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "Номер_заказа", referencedColumnName = "Номер_заказа")
    private Order order;

    @Column(name = "Количество_проданного_продукта")
    private Integer quantitySold;
}
