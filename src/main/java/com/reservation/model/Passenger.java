package com.reservation.model;

public class Passenger {
    private int id;
    private String name;
    private int age;
    private String email;
    private String phone; // optional for OTP

    // Existing constructors...

    // Add this constructor for ReservationService
    public Passenger(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = ""; // default empty
    }

    // Full constructor with phone
    public Passenger(int id, String name, int age, String email, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    // Constructor without id (for registration before DB assigns ID)
    public Passenger(String name, int age, String email, String phone) {
        this(0, name, age, email, phone);
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Passenger [id=" + id + ", name=" + name + ", age=" + age +
               ", email=" + email + ", phone=" + phone + "]";
    }
}
