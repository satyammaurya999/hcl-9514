package org.example.ConstructorInjection;
import org.springframework.context.ApplicationContext;

public class Address {

    private String city;
    private String state;

    public Address(String city, String state) {
        this.city = city;
        this.state = state;
    }

    public void displayAddress() {
        System.out.println("City: " + city);
        System.out.println("State: " + state);
    }
}