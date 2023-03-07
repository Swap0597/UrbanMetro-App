package com.example.urbanmetroapp;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.FileNotFoundException;

public class PrintTicket {
    static int invoiceNo = 100001;

    public static void printTicket(Customer loggedInCustomer, int source, int destination, int[] minDist) throws FileNotFoundException {

        int minDistance = minDist[0];
        System.out.println(minDist[0]);
        String fair = minDistance * 3 + "";
        String path = "E:\\Ticket1.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        float col = 280f;
        float columnWidth[] = {col, col};
        Table table = new Table(columnWidth);

        table.setBackgroundColor(new DeviceRgb(235, 86, 52));
        table.setFontColor(Color.BLACK);
        table.addCell(new Cell().add("TICKET")
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setFontSize(30f)
                .setBorder(Border.NO_BORDER)
        );
        table.addCell(new Cell().add("UrbanMetro App \n District - Nagpur \n Service No - 776655234")
                .setMarginTop(30f)
                .setMarginBottom(30f)
                .setBorder(Border.NO_BORDER)
                .setMarginRight(10f)
        );
        float colWidth[] = {80, 300, 100, 80};
        Table customerInfoTable = new Table(colWidth);
        customerInfoTable.addCell(new Cell(0, 4)
                .add("Customer Information")
                .setBold()
                .setBorder(Border.NO_BORDER)
        );
        customerInfoTable.addCell(new Cell().add("Name").setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add(loggedInCustomer.getName()).setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add("Invoice No: ").setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add("" + invoiceNo++).setBorder(Border.NO_BORDER));

        customerInfoTable.addCell(new Cell().add("Email: ").setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add(loggedInCustomer.getEmail()).setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add("Date:").setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add(String.valueOf(java.time.LocalDate.now())).setBorder(Border.NO_BORDER));

        float tableWidth[] = {140, 430};
        Table ticketInfo = new Table(tableWidth);
        ticketInfo.addCell(new Cell(0, 2)
                .add("Ticket Details")
                .setBold()
        );
        ticketInfo.addCell(new Cell().add("From").setBold());
        ticketInfo.addCell(new Cell().add(Shortestpath.stations.get(source)));

        ticketInfo.addCell(new Cell().add("To").setBold());
        ticketInfo.addCell(new Cell().add(Shortestpath.stations.get(destination)));

        ticketInfo.addCell(new Cell().add("Fair").setBold());
        ticketInfo.addCell(new Cell().add("Rs. " + fair));

        ticketInfo.addCell(new Cell().add("Total Distance").setBold());
        ticketInfo.addCell(new Cell().add(minDistance + " Kms"));

        ticketInfo.addCell(new Cell(0, 2)
                .add((Shortestpath.sendString)
                ));

        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(customerInfoTable);
        document.add(new Paragraph("\n"));
        document.add(ticketInfo);
        document.close();
        System.out.println("PdfCreated");
    }
}
