//package com.restaurant.repository;
//
//import com.restaurant.model.Reservation;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Repository
//public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//    
//    // Find reservations by email
//    List<Reservation> findByEmail(String email);
//    
//    // Find reservations by status
//    List<Reservation> findByStatus(String status);
//    
//    // Find reservations by date range
//    List<Reservation> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
//    
//    // Count reservations for a specific date and time
//    Long countByDateTime(LocalDateTime dateTime);
//}


