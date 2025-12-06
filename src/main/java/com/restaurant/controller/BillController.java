package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.restaurant.model.Bill;
import com.restaurant.model.Order;
import com.restaurant.repository.Billrepository;
import com.restaurant.service.BillService;
import com.restaurant.service.OrderService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private Billrepository billrepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/bill/view/{oid}")
    public String viewBill(@PathVariable long oid, Model model) {
        Bill bill = billrepository.findByOid(oid);
        if (bill == null) {
            model.addAttribute("message", "No bill found for Booking Id: " + oid);
            return "bill-not-found";
        }
        model.addAttribute("bill", bill);
        return "bill";
    }

    private void addRow(PdfPTable table, String label, String value) {
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell cell1 = new PdfPCell(new Phrase(label, boldFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value));

        cell1.setPadding(8);
        cell2.setPadding(8);

        table.addCell(cell1);
        table.addCell(cell2);
    }

    @GetMapping("/bill/download/{oid}")
    public void downloadBillPdf(@PathVariable long oid, HttpServletResponse response) throws Exception {
        Order order = orderService.getOrderById(oid);
        Bill b = billrepository.findByOid(oid);

        if (order == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            return;
        }
        if (b == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=bill_" + oid + ".pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        try {
            Image logo = Image.getInstance("src/main/resources/static/assests/Chinese House logo.png");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Image.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
            System.out.println("Logo not found: " + e.getMessage());
        }

        Font addressFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        Paragraph address = new Paragraph("5th Floor, Dilsukhnagar, Hyderabad - 500060", addressFont);
        address.setAlignment(Element.ALIGN_CENTER);
        address.setSpacingAfter(20);
        document.add(address);

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Food Order Invoice", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        table.setWidths(new float[]{3, 7});

        addRow(table, "Invoice Number:", String.valueOf(b.getInvoiceNumber()));
        addRow(table, "Order ID:", String.valueOf(b.getOid()));
        addRow(table, "Customer Name:",b.getCustomerName());
        addRow(table, "Item:", b.getItemName());
        addRow(table, "Items:", String.valueOf(b.getItemName()));
        addRow(table, "Rate per item:", "₹" + b.getRatePerItem());
        addRow(table, "Total Amount:", "₹" + b.getTotalAmount());

        document.add(table);

        Paragraph thankYou = new Paragraph("Thank you for Ordering Our Food.", FontFactory.getFont(FontFactory.HELVETICA));
        thankYou.setSpacingBefore(30);
        thankYou.setAlignment(Element.ALIGN_CENTER);
        document.add(thankYou);

        document.close();
    }
}
