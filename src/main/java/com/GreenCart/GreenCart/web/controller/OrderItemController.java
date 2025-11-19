package com.GreenCart.GreenCart.web.controller;

import com.GreenCart.GreenCart.domain.OrderItem;
import com.GreenCart.GreenCart.domain.service.OrderItemService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidoitems")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    //Listar todos los items
    @GetMapping("/all")
    public ResponseEntity<List<OrderItem>> getAll() {
        List<OrderItem> items = orderItemService.getAll();
        return items.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(items);
    }

    //Buscar item por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getItem(@PathVariable("id") int itemId) {
        return orderItemService.getItem(itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Listar items por pedido
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getByOrder(@PathVariable("orderId") int orderId) {
        List<OrderItem> items = orderItemService.getByOrder(orderId);
        return items.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(items);
    }

    //Listar items por vendedor
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<OrderItem>> getBySeller(@PathVariable("sellerId") int sellerId) {
        List<OrderItem> items = orderItemService.getBySeller(sellerId);
        return items.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(items);
    }

    //Crear nuevo item
    @PostMapping("/save")
    public ResponseEntity<OrderItem> save(@RequestBody OrderItem item) {
        System.out.println("Recibiendo item: " + item);
        OrderItem saved = orderItemService.save(item);
        System.out.println("Item guardado: " + saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    //Eliminar item por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int itemId) {
        boolean deleted = orderItemService.delete(itemId);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{itemId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable int itemId,
            @RequestBody Map<String, String> body
    ) {
        String newStatus = body.get("status"); // obtiene "EN_PROCESO"
        boolean updated = orderItemService.updateStatus(itemId, newStatus);

        return updated
                ? ResponseEntity.ok("Estado actualizado")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item no encontrado");
    }

    // Generar Excel con logo pequeño en esquina y sin columna ID Item
    @GetMapping("/seller/{sellerId}/excel")
    public ResponseEntity<byte[]> descargarExcelVendedor(@PathVariable int sellerId) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos Vendidos");

            // Estilos
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            int rowIdx = 0;

            // Título centrado
            Row titleRow = sheet.createRow(rowIdx);
            Cell titleCell = titleRow.createCell(1); // columna 1 para dejar espacio al logo
            titleCell.setCellValue("GreenCart S.A.");
            CellStyle titleStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short)16);
            titleStyle.setFont(font);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0,0,1,6)); // centrar entre col 1 y 6

            // Logo a la izquierda de la fila del título
            InputStream logoStream = getClass().getResourceAsStream("/static/img.png");
            if (logoStream != null) {
                byte[] bytes = logoStream.readAllBytes();
                int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                CreationHelper helper = workbook.getCreationHelper();
                Drawing<?> drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(0); // columna izquierda
                anchor.setRow1(rowIdx); // misma fila que el título
                anchor.setCol2(1); // ocupa 1 columna de ancho
                anchor.setRow2(rowIdx + 1); // ocupa 1 fila de alto
                Picture pict = drawing.createPicture(anchor, pictureIdx);
                pict.resize(1.3); // ajusta proporcionalmente
            }

            rowIdx += 2; // espacio después del título y logo

            // Cabecera de la tabla (sin ID Item)
            String[] columns = {"ID Pedido","ID Producto","Nombre Producto","Cantidad","Precio Unitario","Total","Estado"};
            Row header = sheet.createRow(rowIdx++);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Datos
            List<OrderItem> items = orderItemService.getBySeller(sellerId);
            for (OrderItem item : items) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(item.getOrderId());
                row.createCell(1).setCellValue(item.getProductId());
                row.createCell(2).setCellValue(item.getProductName());
                row.createCell(3).setCellValue(item.getQuantity());
                row.createCell(4).setCellValue(item.getUnitPrice());
                row.createCell(5).setCellValue(item.getQuantity() * item.getUnitPrice());
                row.createCell(6).setCellValue(item.getStatus());
                for (int i = 0; i < 7; i++) row.getCell(i).setCellStyle(cellStyle);
            }

            // Auto tamaño columnas
            for (int i = 0; i < columns.length; i++) sheet.autoSizeColumn(i);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("Productos_Vendedor_" + sellerId + ".xlsx")
                    .build());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok().headers(headers).body(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
