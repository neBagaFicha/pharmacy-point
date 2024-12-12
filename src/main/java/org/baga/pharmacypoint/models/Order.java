package org.baga.pharmacypoint.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Заказы")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Номер_заказа")
    private Integer id;

    @Column(name = "Дата_заказа")
    private LocalDateTime orderDate;

    @Column(name = "Статус_заказа")
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "Код_клиента", referencedColumnName = "Код_клиента")
    private Client client;
}
