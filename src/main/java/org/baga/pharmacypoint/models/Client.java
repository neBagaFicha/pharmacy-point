package org.baga.pharmacypoint.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "Клиенты")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Код_клиента")
    private Integer id;

    @Column(name = "Фамилия_клиента")
    private String clientSurname;

    @Column(name = "Имя_клиента")
    private String clientName;

    @Column(name = "Отчество_клиента")
    private String clientPatronymic;

    @Column(name = "Номер_телефона_клиента")
    private String clientPhone;

    @Column(name = "Адрес_электронной_почты_клиента")
    private String clientEmail;

    @Column(name = "Предпочтения")
    private String clientPreferences;

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Order> orders;
}
