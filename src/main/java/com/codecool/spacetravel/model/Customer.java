package com.codecool.spacetravel.model;


import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "Customer.getAllCustomers",
                query = "SELECT c FROM Customer c"
        ),
        @NamedQuery(
                name="Customer.getCustomerByEmail",
                query = "SELECT c FROM Customer c WHERE c.email = :email"
        ),
        @NamedQuery(
                name = "Customer.getCustomerById",
                query = "SELECT c FROM Customer c WHERE c.id = :id"
        )
})
@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String country;
    private String city;
    private String postalCode;
    private String address;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private CustomerLegitimacy legitimacy;

    @OneToMany(mappedBy = "customer")
    private List<RoomReservation> roomReservation;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String country, String city, String postalCode, String address, String password, CustomerLegitimacy legitimacy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
        this.password = password;
        this.legitimacy = legitimacy;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CustomerLegitimacy getLegitimacy() { return legitimacy; }

    public void setLegitimacy(CustomerLegitimacy legitimacy) { this.legitimacy = legitimacy; }

    public List<RoomReservation> getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(List<RoomReservation> roomReservation) {
        this.roomReservation = roomReservation;
    }
}

