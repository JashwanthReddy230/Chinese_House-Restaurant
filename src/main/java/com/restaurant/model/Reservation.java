//package com.restaurant.model;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "reservations")
//public class Reservation {
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    
//    @Column(nullable = false)
//    private String name;
//    
//    @Column(nullable = false)
//    private String email;
//    
//    @Column(nullable = false)
//    private LocalDateTime dateTime;
//    
//    @Column(nullable = false)
//    private Integer numberOfPeople;
//    
//    @Column(length = 500)
//    private String specialRequest;
//    
//    @Column(nullable = false)
//    private String status = "PENDING"; // PENDING, CONFIRMED, CANCELLED
//    
//    @Column(nullable = false)
//    private LocalDateTime createdAt;
//    
//    // Constructors
//    public Reservation() {
//        this.createdAt = LocalDateTime.now();
//    }
//    
//    public Reservation(String name, String email, LocalDateTime dateTime, Integer numberOfPeople, String specialRequest) {
//        this.name = name;
//        this.email = email;
//        this.dateTime = dateTime;
//        this.numberOfPeople = numberOfPeople;
//        this.specialRequest = specialRequest;
//        this.createdAt = LocalDateTime.now();
//        this.status = "PENDING";
//    }
//    
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//    
//    public void setId(Long id) {
//        this.id = id;
//    }
//    
//    public String getName() {
//        return name;
//    }
//    
//    public void setName(String name) {
//        this.name = name;
//    }
//    
//    public String getEmail() {
//        return email;
//    }
//    
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    
//    public LocalDateTime getDateTime() {
//        return dateTime;
//    }
//    
//    public void setDateTime(LocalDateTime dateTime) {
//        this.dateTime = dateTime;
//    }
//    
//    public Integer getNumberOfPeople() {
//        return numberOfPeople;
//    }
//    
//    public void setNumberOfPeople(Integer numberOfPeople) {
//        this.numberOfPeople = numberOfPeople;
//    }
//    
//    public String getSpecialRequest() {
//        return specialRequest;
//    }
//    
//    public void setSpecialRequest(String specialRequest) {
//        this.specialRequest = specialRequest;
//    }
//    
//    public String getStatus() {
//        return status;
//    }
//    
//    public void setStatus(String status) {
//        this.status = status;
//    }
//    
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//    
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//}


