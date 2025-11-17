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
            Document document = new Document(PageSize.A4, 40, 40, 80, 40);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // ======================================
            //   1. LOGO COMO FONDO (MARCA DE AGUA)
            // ======================================
            try {
                URL logoUrl = PDFService.class.getResource("/static/img.png");
                if (logoUrl != null) {
                    Image bg = Image.getInstance(logoUrl);

                    float pageWidth = PageSize.A4.getWidth();
                    float pageHeight = PageSize.A4.getHeight();

                    bg.scaleToFit(pageWidth * 0.8f, pageHeight * 0.8f);
                    bg.setAbsolutePosition((pageWidth - bg.getScaledWidth()) / 2,
                            (pageHeight - bg.getScaledHeight()) / 2);

                    PdfContentByte canvas = writer.getDirectContentUnder();

                    PdfGState gs = new PdfGState();
                    gs.setFillOpacity(0.08f);
                    canvas.setGState(gs);
                    canvas.addImage(bg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // ===================== FUENTES =====================
            Font infoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);

            // ===================== HEADER =====================
            PdfPTable header = new PdfPTable(3);
            header.setWidthPercentage(100);
            header.setWidths(new float[]{2, 4, 2});

            // Logo pequeño en el encabezado
            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(Rectangle.NO_BORDER);
            try {
                URL logoUrl = PDFService.class.getResource("/static/img.png");
                if (logoUrl != null) {
                    Image logo = Image.getInstance(logoUrl);
                    logo.scaleToFit(70, 70);
                    logoCell.addElement(logo);
                }
            } catch (Exception ignored) {}
            header.addCell(logoCell);

            // Título
            PdfPCell titleCell = new PdfPCell(new Phrase("GreenCart – Detalle del Pedido", titleFont));
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.addCell(titleCell);

            // Fecha
            PdfPCell dateCell = new PdfPCell(new Phrase(
                    "Fecha: " + order.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    infoFont));
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            dateCell.setVerticalAlignment(Element.ALIGN_TOP);
            header.addCell(dateCell);

            document.add(header);
            document.add(new Paragraph("\n"));

            // ===================== DATOS DEL CLIENTE =====================
            PdfPTable client = new PdfPTable(2);
            client.setWidthPercentage(100);
            client.setSpacingBefore(10);
            client.setWidths(new float[]{1, 1});

            PdfPCell c1 = new PdfPCell();
            c1.setBorder(Rectangle.NO_BORDER);
            c1.addElement(new Phrase("Cliente: " + buyer.getFirstName() + " " + buyer.getLastName(), infoFont));
            c1.addElement(new Phrase("Correo: " + buyer.getEmail(), infoFont));
            client.addCell(c1);

            PdfPCell c2 = new PdfPCell();
            c2.setBorder(Rectangle.NO_BORDER);
            c2.addElement(new Phrase("Teléfono: " + buyer.getPhone(), infoFont));
            c2.addElement(new Phrase("Dirección: " + buyer.getAddress(), infoFont));
            client.addCell(c2);

            document.add(client);
            document.add(new Paragraph("\n"));

            // Estado
            Paragraph estado = new Paragraph("Estado del Pedido: " + order.getStatus(), boldFont);
            document.add(estado);
            document.add(new Paragraph("\n"));

            // ===================== TABLA DE PRODUCTOS =====================
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4, 1, 2, 2});
            table.setSpacingBefore(10);

            // Header
            addHeaderCell(table, "Producto");
            addHeaderCell(table, "Cant.");
            addHeaderCell(table, "P. Unitario");
            addHeaderCell(table, "Subtotal");

            for (OrderItem i : order.getItems()) {

                PdfPCell p1 = new PdfPCell(new Phrase(i.getProductName(), infoFont));
                p1.setHorizontalAlignment(Element.ALIGN_CENTER);
                p1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(p1);

                PdfPCell p2 = new PdfPCell(new Phrase(i.getQuantity().toString(), infoFont));
                p2.setHorizontalAlignment(Element.ALIGN_CENTER);
                p2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(p2);

                PdfPCell p3 = new PdfPCell(new Phrase("S/ " + String.format("%.2f", i.getUnitPrice()), infoFont));
                p3.setHorizontalAlignment(Element.ALIGN_CENTER);
                p3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(p3);

                PdfPCell p4 = new PdfPCell(new Phrase("S/ " + String.format("%.2f", i.getTotal()), infoFont));
                p4.setHorizontalAlignment(Element.ALIGN_CENTER);
                p4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(p4);
            }

            document.add(table);
            document.add(new Paragraph("\n"));

            // ===================== TOTAL =====================
            Paragraph total = new Paragraph(
                    "TOTAL: S/ " + String.format("%.2f", order.getTotal()),
                    new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)
            );
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cell.setBackgroundColor(new BaseColor(230, 230, 230));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        table.addCell(cell);
    }
}
