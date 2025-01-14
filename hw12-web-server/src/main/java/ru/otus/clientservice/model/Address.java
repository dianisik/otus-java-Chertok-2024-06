package ru.otus.clientservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @OneToOne
    private Client client;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" + "id=" + id + ", street='" + street + '\'' + '}';
    }

    @SuppressWarnings({"java:S2975"})
    @Override
    public Address clone() {
        final Address address;
        try {
            address = (Address) super.clone();
            address.setId(id);
            address.setStreet(street);
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        return address;
    }
}
