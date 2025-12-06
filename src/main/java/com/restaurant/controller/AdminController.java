package com.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.model.Bill;
import com.restaurant.model.Order;

import com.restaurant.service.BillService;
import com.restaurant.service.ItemService;
import com.restaurant.service.OrderService;
import com.restaurant.service.UserService;


@Controller
public class AdminController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private BillService billService;
	
//	@Autowired
//	private ReservationService reservationService;
//	
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('ADMIN')")
	public String getItemList(Model model) {
		// Existing code
		model.addAttribute("item", itemService.getAllItems());
		model.addAttribute("user", userservice.getAllUsers());
		model.addAttribute("order", orderService.getAllOrders());
		
		List<Order> orders = orderService.getAllOrders();
		model.addAttribute("order", orders);
		
		// Order statistics
		long acceptedCount = orders.stream()
			.filter(o -> "ACCEPTED".equalsIgnoreCase(o.getStatus()))
			.count();
		long pendingCount = orders.stream()
			.filter(o -> "PENDING".equalsIgnoreCase(o.getStatus()))
			.count();
		long rejectedCount = orders.stream()
			.filter(o -> "REJECTED".equalsIgnoreCase(o.getStatus()))
			.count();
		long completedCount = orders.stream()
			.filter(o -> "COMPLETED".equalsIgnoreCase(o.getStatus()))
			.count();
		
		// Total orders count
		long totalOrders = orders.size();
		
		// Total revenue — sum of totalprice of ACCEPTED or COMPLETED orders
		double totalRevenue = orders.stream()
			.filter(o -> "ACCEPTED".equalsIgnoreCase(o.getStatus()) || "COMPLETED".equalsIgnoreCase(o.getStatus()))
			.mapToDouble(Order::getTotalprice)
			.sum();
		
		model.addAttribute("acceptedCount", acceptedCount);
		model.addAttribute("pendingCount", pendingCount);
		model.addAttribute("rejectedCount", rejectedCount);
		model.addAttribute("completedCount", completedCount);
		model.addAttribute("totalOrders", totalOrders);
		model.addAttribute("totalRevenue", totalRevenue);
		return "redirect:/dashboard";
	}
		
//		// ====== NEW: Reservation Data ======
//		List<Reservation> reservations = reservationService.getAllReservations();
//		model.addAttribute("reservations", reservations);
//		
//		// Reservation statistics
//		long pendingReservations = reservations.stream()
//			.filter(r -> "PENDING".equalsIgnoreCase(r.getStatus()))
//			.count();
//		long confirmedReservations = reservations.stream()
//			.filter(r -> "CONFIRMED".equalsIgnoreCase(r.getStatus()))
//			.count();
//		long cancelledReservations = reservations.stream()
//			.filter(r -> "CANCELLED".equalsIgnoreCase(r.getStatus()))
//			.count();
//		
//		model.addAttribute("totalReservations", reservations.size());
//		model.addAttribute("pendingReservations", pendingReservations);
//		model.addAttribute("confirmedReservations", confirmedReservations);
//		model.addAttribute("cancelledReservations", cancelledReservations);
//		
//		return "dashboard";
//	}
//	
//	// ====== ORDER MANAGEMENT ======
	
	@PostMapping("/order/{id}/accept")
	@PreAuthorize("hasRole('ADMIN')")
	public String acceptBookingRequest(@PathVariable Long id) {
		Order order = orderService.getOrderById(id);
		if (order != null && order.getStatus().equals("PENDING")) {
			order.setStatus("ACCEPTED");
			orderService.saveOrder(order);
			
			Bill bill = new Bill();
			bill.setOid(order.getId());
			String invoiceNum = billService.generateInvoiceNumber();
			bill.setInvoiceNumber(invoiceNum);
			bill.setItemName(order.getItem().getDishname());
			bill.setOrderDate((order.getOrderdate()));
			String fullName = order.getRegister().getLastname() + " " + order.getRegister().getFirstname();
			bill.setCustomerName(fullName);
			bill.setRatePerItem((order.getPrice())); 
			bill.setTotalAmount(order.getTotalprice());
			billService.saveBill(bill);
			
			System.out.println(invoiceNum);
			System.out.println(bill.getInvoiceNumber());
		}
		return "redirect:/dashboard";
	}
	
	@PostMapping("/orders/{id}/reject")
	@PreAuthorize("hasRole('ADMIN')")
	public String rejectorder(@PathVariable Long id) {
		Order order = orderService.getOrderById(id);
		if (order != null && order.getStatus().equals("PENDING")) {
			order.setStatus("REJECTED");
			orderService.saveOrder(order);
		}
		return "redirect:/dashboard";
	}
	
	@PostMapping("/orders/{id}/completed")
	@PreAuthorize("hasRole('ADMIN')")
	public String completedorder(@PathVariable Long id) {
		Order order = orderService.getOrderById(id);
		if (order != null && order.getStatus().equals("ACCEPTED")) {
			order.setStatus("COMPLETED");
			orderService.saveOrder(order);
		}
		return "redirect:/dashboard";
	}
	
	// ====== RESERVATION MANAGEMENT ======
	
//	@PostMapping("/admin/reservation/confirm/{id}")
//	@PreAuthorize("hasRole('ADMIN')")
//	public String confirmReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//		try {
//			reservationService.confirmReservation(id);
//			redirectAttributes.addFlashAttribute("success", "Reservation confirmed successfully!");
//		} catch (Exception e) {
//			redirectAttributes.addFlashAttribute("error", "Error confirming reservation: " + e.getMessage());
//		}
//		return "redirect:/dashboard";
//	}
//	
//	@PostMapping("/admin/reservation/cancel/{id}")
//	@PreAuthorize("hasRole('ADMIN')")
//	public String cancelReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//		try {
//			reservationService.cancelReservation(id);
//			redirectAttributes.addFlashAttribute("success", "Reservation cancelled successfully!");
//		} catch (Exception e) {
//			redirectAttributes.addFlashAttribute("error", "Error cancelling reservation: " + e.getMessage());
//		}
//		return "redirect:/dashboard";
//	}
//	
//	@PostMapping("/admin/reservation/delete/{id}")
//	@PreAuthorize("hasRole('ADMIN')")
//	public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//		try {
//			reservationService.deleteReservation(id);
//			redirectAttributes.addFlashAttribute("success", "Reservation deleted successfully!");
//		} catch (Exception e) {
//			redirectAttributes.addFlashAttribute("error", "Error deleting reservation: " + e.getMessage());
//		}
//		return "redirect:/dashboard";
//	}
}