//package com.restaurant.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.restaurant.dto.ReservationDTO;
//import com.restaurant.model.Reservation;
//import com.restaurant.service.ReservationService;
//
//@Controller
//public class ReservationController {
//    
//    @Autowired
//    private ReservationService reservationService;
//    
//    @PostMapping("/reservation")
//    public String createReservation(@ModelAttribute ReservationDTO reservationDTO, 
//                                    RedirectAttributes redirectAttributes) {
//        try {
//            System.out.println("=== Reservation Controller ===");
//            System.out.println("Received DTO: " + reservationDTO);
//            
//            Reservation reservation = reservationService.createReservation(reservationDTO);
//            
//            redirectAttributes.addFlashAttribute("success", 
//                "Thank you! Your reservation has been received. Confirmation ID: #" + reservation.getId());
//            
//            System.out.println("✅ Reservation created successfully!");
//            
//        } catch (Exception e) {
//            System.err.println("❌ Error in controller: " + e.getMessage());
//            e.printStackTrace();
//            
//            redirectAttributes.addFlashAttribute("error", 
//                "Error: " + e.getMessage());
//        }
//        
//        return "redirect:/index#reservation";
//    }
//    @GetMapping("/test-reservation")
//    @ResponseBody
//    public String testReservation() {
//        try {
//            ReservationDTO dto = new ReservationDTO();
//            dto.setName("Test User");
//            dto.setEmail("test@example.com");
//            dto.setDateTime("2025-12-25T18:30");
//            dto.setNumberOfPeople(4);
//            dto.setSpecialRequest("Window seat please");
//            
//            Reservation saved = reservationService.createReservation(dto);
//            return "✅ Test reservation created with ID: " + saved.getId();
//        } catch (Exception e) {
//            return "❌ Error: " + e.getMessage();
//        }
//    }
//}


