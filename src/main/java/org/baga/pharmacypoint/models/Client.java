package org.baga.pharmacypoint.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "Фамилия не может быть пустой")
    @Size(max = 50, message = "Фамилия не может быть длиннее 50 символов")
    private String clientSurname;

    @Column(name = "Имя_клиента")
    @NotEmpty(message = "Имя не может быть пустым")
    @Size(max = 50, message = "Имя не может быть длиннее 50 символов")
    private String clientName;

    @Column(name = "Отчество_клиента")
    @NotEmpty(message = "Отчество не может быть пустым")
    @Size(max = 50, message = "Отчество не может быть длиннее 50 символов")
    private String clientPatronymic;

    @Column(name = "Номер_телефона_клиента")
    @NotEmpty(message = "Номер телефона не может быть пустым")
    @Size(max = 12, message = "Номер телефона не может быть длиннее 15 символов")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Номер телефона должен быть в формате +7XXXXXXXXXX")
    private String clientPhone;

    @Column(name = "Адрес_электронной_почты_клиента")
    @NotEmpty(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Некорректный адрес электронной почты")
    private String clientEmail;

    @Column(name = "Предпочтения")
    @Size(max = 255, message = "Предпочтения не могут быть длиннее 255 символов")
    private String clientPreferences;

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Order> orders;
}
