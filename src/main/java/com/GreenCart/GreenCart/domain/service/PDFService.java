package com.GreenCart.GreenCart.domain.service;

import com.GreenCart.GreenCart.domain.Order;
import com.GreenCart.GreenCart.domain.OrderItem;
import com.GreenCart.GreenCart.domain.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFService {

    public static byte[] generateOrderPDF(Order order, User buyer) {
        try {
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();

            Font infoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);

            // ===================== HEADER =====================
            PdfPTable headerTable = new PdfPTable(3);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{2, 4, 2}); // Ajusta proporcionalmente

            // Columna izquierda: Logo
            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(Rectangle.NO_BORDER);
            try {
                URL logoUrl = PDFService.class.getResource("/static/img.png");
                if (logoUrl != null) {
                    Image logo = Image.getInstance(logoUrl);
                    logo.scaleToFit(100, 100);
                    logoCell.addElement(logo);
                } else {
                    System.out.println("Logo no encontrado en /static/img.png");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            headerTable.addCell(logoCell);

            // Columna central: Título
            PdfPCell titleCell = new PdfPCell(new Phrase("🛒 GreenCart - Detalle del Pedido", titleFont));
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(titleCell);

            // Columna derecha: Fecha
            PdfPCell dateCell = new PdfPCell(new Phrase(
                    "Fecha Y Hora: " + order.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), infoFont));
            dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            dateCell.setVerticalAlignment(Element.ALIGN_TOP);
            dateCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(dateCell);

            document.add(headerTable);
            document.add(new Paragraph("\n"));

            // ===================== CLIENTE =====================
            if (buyer != null) {
                PdfPTable clientTable = new PdfPTable(2);
                clientTable.setWidthPercentage(100);
                clientTable.setWidths(new float[]{3, 3});

                // Columna izquierda: Nombre y Correo
                PdfPCell leftCell = new PdfPCell();
                leftCell.setBorder(Rectangle.NO_BORDER);
                leftCell.addElement(new Paragraph("Cliente: " + buyer.getFirstName() + " " + buyer.getLastName(), infoFont));
                leftCell.addElement(new Paragraph("Correo: " + buyer.getEmail(), infoFont));
                clientTable.addCell(leftCell);

                // Columna derecha: Teléfono y Dirección
                PdfPCell rightCell = new PdfPCell();
                rightCell.setBorder(Rectangle.NO_BORDER);
                rightCell.addElement(new Paragraph("Teléfono: " + buyer.getPhone(), infoFont));
                rightCell.addElement(new Paragraph("Dirección: " + buyer.getAddress(), infoFont));
                clientTable.addCell(rightCell);

                document.add(clientTable);
                document.add(new Paragraph("\n"));
            }

            // Estado del pedido
            document.add(new Paragraph("Estado: " + order.getStatus(), infoFont));
            document.add(new Paragraph("\n"));

            // ===================== TABLA DE PRODUCTOS =====================
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 1, 2, 2});

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            table.addCell(new PdfPCell(new Phrase("Producto", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Cant.", headerFont)));
            table.addCell(new PdfPCell(new Phrase("P. Unitario", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Total", headerFont)));

            Font cellFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
            List<OrderItem> items = order.getItems();
            if (items != null) {
                for (OrderItem item : items) {
                    table.addCell(new PdfPCell(new Phrase("Código " + item.getProductId(), cellFont)));
                    table.addCell(new PdfPCell(new Phrase(item.getQuantity().toString(), cellFont)));
                    table.addCell(new PdfPCell(new Phrase("S/ " + String.format("%.2f", item.getUnitPrice()), cellFont)));
                    table.addCell(new PdfPCell(new Phrase("S/ " + String.format("%.2f", item.getTotal()), cellFont)));
                }
            }

            document.add(table);
            document.add(new Paragraph("\n"));

            // ===================== TOTAL =====================
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
            Paragraph total = new Paragraph("TOTAL A PAGAR: S/ " + String.format("%.2f", order.getTotal()), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

