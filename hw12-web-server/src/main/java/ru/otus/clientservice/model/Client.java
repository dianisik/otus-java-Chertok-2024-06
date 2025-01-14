package ru.otus.clientservice.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public final class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "client")
    private Address address;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "client")
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = null;
        this.phones = null;

        if (address != null) {
            Address addressCopy = new Address(address.getId(), address.getStreet());
            addressCopy.setClient(this);
            this.address = addressCopy;
        }
        if (phones != null) {
            List<Phone> phonesCopy = new ArrayList<>();
            phones.forEach(phone -> {
                Phone phoneCopy = new Phone(phone.getId(), phone.getNumber());
                phoneCopy.setClient(this);
                phonesCopy.add(phoneCopy);
            });
            this.phones = phonesCopy;
        }
    }

    @Override
    @SuppressWarnings({"java:S2975"})
    public Client clone() {
        final Client clonedClient;
        try {
            clonedClient = (Client) super.clone();
            clonedClient.setId(id);
            clonedClient.setName(name);

            List<Phone> clonedPhones = new ArrayList<>();
            if (phones != null) {
                phones.forEach(phone -> {
                    Phone clonedPhone = phone.clone();
                    clonedPhone.setClient(clonedClient);
                    clonedPhones.add(clonedPhone);
                });
                clonedClient.setPhones(clonedPhones);
            }

            if (address != null) {
                Address clonedAddress = address.clone();
                clonedAddress.setClient(clonedClient);
                clonedClient.setAddress(clonedAddress);
            }
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
        return clonedClient;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
