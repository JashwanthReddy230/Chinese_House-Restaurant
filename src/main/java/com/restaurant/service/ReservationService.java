//package com.restaurant.service;
//
//import com.restaurant.dto.ReservationDTO;
//import com.restaurant.model.Reservation;
//import com.restaurant.repository.ReservationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ReservationService {
//    
//    @Autowired
//    private ReservationRepository reservationRepository;
//    
//    // Multiple date formatters to handle different input formats
//    private static final DateTimeFormatter[] FORMATTERS = {
//        DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"),
//        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
//        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"),
//        DateTimeFormatter.ISO_LOCAL_DATE_TIME
//    };
//    
//    public Reservation createReservation(ReservationDTO dto) {
//        try {
//            System.out.println("=== Creating Reservation ===");
//            System.out.println("Name: " + dto.getName());
//            System.out.println("Email: " + dto.getEmail());
//            System.out.println("DateTime String: " + dto.getDateTime());
//            System.out.println("Number of People: " + dto.getNumberOfPeople());
//            System.out.println("Special Request: " + dto.getSpecialRequest());
//            
//            // Parse the date-time string
//            LocalDateTime dateTime = parseDateTime(dto.getDateTime());
//            System.out.println("Parsed DateTime: " + dateTime);
//            
//            // Check if the date is in the future
//            if (dateTime.isBefore(LocalDateTime.now())) {
//                throw new IllegalArgumentException("Reservation date must be in the future");
//            }
//            
//            // Create reservation
//            Reservation reservation = new Reservation();
//            reservation.setName(dto.getName());
//            reservation.setEmail(dto.getEmail());
//            reservation.setDateTime(dateTime);
//            reservation.setNumberOfPeople(dto.getNumberOfPeople());
//            reservation.setSpecialRequest(dto.getSpecialRequest());
//            reservation.setStatus("PENDING");
//            reservation.setCreatedAt(LocalDateTime.now());
//            
//            // Save to database
//            Reservation saved = reservationRepository.save(reservation);
//            System.out.println("✅ Reservation saved with ID: " + saved.getId());
//            
//            return saved;
//            
//        } catch (Exception e) {
//            System.err.println("❌ Error creating reservation: " + e.getMessage());
//            e.printStackTrace();
//            throw new RuntimeException("Error creating reservation: " + e.getMessage());
//        }
//    }
//    
//    private LocalDateTime parseDateTime(String dateTimeString) {
//        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
//            throw new IllegalArgumentException("Date and time is required");
//        }
//        
//        // Try each formatter
//        for (DateTimeFormatter formatter : FORMATTERS) {
//            try {
//                return LocalDateTime.parse(dateTimeString.trim(), formatter);
//            } catch (DateTimeParseException e) {
//                // Try next formatter
//                continue;
//            }
//        }
//        
//        throw new IllegalArgumentException("Unable to parse date/time: " + dateTimeString + 
//            ". Expected format: MM/dd/yyyy hh:mm a (e.g., 12/25/2025 06:30 PM)");
//    }
//    
//    public List<Reservation> getAllReservations() {
//        return reservationRepository.findAll();
//    }
//    
//    public Optional<Reservation> getReservationById(Long id) {
//        return reservationRepository.findById(id);
//    }
//    
//    public List<Reservation> getReservationsByEmail(String email) {
//        return reservationRepository.findByEmail(email);
//    }
//    
//    public List<Reservation> getReservationsByStatus(String status) {
//        return reservationRepository.findByStatus(status);
//    }
//    
//    public Reservation updateStatus(Long id, String status) {
//        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
//        
//        if (reservationOpt.isPresent()) {
//            Reservation reservation = reservationOpt.get();
//            reservation.setStatus(status);
//            return reservationRepository.save(reservation);
//        }
//        
//        throw new RuntimeException("Reservation not found with id: " + id);
//    }
//    
//    public void cancelReservation(Long id) {
//        updateStatus(id, "CANCELLED");
//    }
//    
//    public void confirmReservation(Long id) {
//        updateStatus(id, "CONFIRMED");
//    }
//    
//    public void deleteReservation(Long id) {
//        reservationRepository.deleteById(id);
//    }
//}


