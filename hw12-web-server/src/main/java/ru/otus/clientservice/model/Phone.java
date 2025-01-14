package ru.otus.clientservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phone")
public class Phone implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" + "id=" + id + ", number='" + number + '\'' + '}';
    }

    @SuppressWarnings({"java:S2975"})
    @Override
    public Phone clone() {
        final Phone phone;
        try {
            phone = (Phone) super.clone();
            phone.setId(this.id);
            phone.setNumber(this.number);
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        return phone;
    }
}
