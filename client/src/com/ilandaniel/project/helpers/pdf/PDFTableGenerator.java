package com.ilandaniel.project.helpers.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.Arrays;

public class PDFTableGenerator {

    private PdfDTO pdfDTO;

    // Generates document from Table object
    public void generatePDF(Table table, String path, PdfDTO pdfDTO) throws IOException {
        PDDocument doc = null;
        this.pdfDTO = pdfDTO;
        try {
            // add pdf extension if there is no one
            if (!path.endsWith(".pdf")) {
                path += ".pdf";
            }

            doc = new PDDocument();
            drawTable(doc, table);
            doc.save(path);
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

    // Configures basic setup for the table and draws it page by page
    public void drawTable(PDDocument doc, Table table) throws IOException {
        // Calculate pagination
        int rowsPerPage = (int) Math.floor(table.getHeight() / table.getRowHeight()) - 1; // subtract
        int numberOfPages = (int) Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage);

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            String[][] currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
            drawCurrentPage(table, currentPageContent, contentStream);
        }
    }

    // Draws current page table grid and borderlines and content
    private void drawCurrentPage(Table table, String[][] currentPageContent, PDPageContentStream contentStream)
            throws IOException {
        float tableTopY = table.isLandscape() ? table.getPageSize().getWidth() - table.getMargin() - 20 : table.getPageSize().getHeight() - table.getMargin() - 20;

        // Draws grid and borders
        drawTableGrid(table, currentPageContent, contentStream, tableTopY);

        // Position cursor to start drawing content
        float nextTextX = table.getMargin() + table.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getRowHeight() / 2)
                - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table.getFontSize()) / 4);

        // Write column headers
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 11);
        writeContentLine(table.getColumnsNamesAsArray(), contentStream, nextTextX, nextTextY, table);
        nextTextY -= table.getRowHeight();
        nextTextX = table.getMargin() + table.getCellMargin();

        writeLine("Expenses Report [ " + pdfDTO.getFrom() + " - " + pdfDTO.getTo() + " ]", contentStream, table.getMargin(), tableTopY + 15, true);

        contentStream.setFont(PDType1Font.HELVETICA, 10);

        // Write content
        for (String[] strings : currentPageContent) {
            writeContentLine(strings, contentStream, nextTextX, nextTextY, table);
            nextTextY -= table.getRowHeight();
            nextTextX = table.getMargin() + table.getCellMargin();
        }

        writeLine("Summary is: " + pdfDTO.getSum() + " ILS", contentStream, table.getMargin(), nextTextY - 5, true);

        contentStream.close();
    }

    // Writes the content for one line
    private void writeContentLine(String[] lineContent, PDPageContentStream contentStream, float nextTextX, float nextTextY,
                                  Table table) throws IOException {
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            String text = lineContent[i];
            contentStream.beginText();
            contentStream.newLineAtOffset(nextTextX, nextTextY);
            contentStream.showText(text != null ? text : "");
            contentStream.endText();
            nextTextX += table.getColumns().get(i).getWidth();
        }
    }

    private void writeLine(String txt, PDPageContentStream contentStream, float nextTextX, float nextTextY, boolean isBold) throws IOException {
        if (isBold) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        }

        contentStream.beginText();
        contentStream.newLineAtOffset(nextTextX, nextTextY);
        contentStream.showText(txt != null ? txt : "");
        contentStream.endText();
    }

    private void drawTableGrid(Table table, String[][] currentPageContent, PDPageContentStream contentStream, float tableTopY)
            throws IOException {
        // Draw row lines
        float nextY = tableTopY;
        for (int i = 0; i <= currentPageContent.length + 1; i++) {
            contentStream.drawLine(table.getMargin(), nextY, table.getMargin() + table.getWidth(), nextY);
            nextY -= table.getRowHeight();
        }

        // Draw column lines
        final float tableYLength = table.getRowHeight() + (table.getRowHeight() * currentPageContent.length);
        final float tableBottomY = tableTopY - tableYLength;
        float nextX = table.getMargin();
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
            nextX += table.getColumns().get(i).getWidth();
        }
        contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
    }

    private String[][] getContentForCurrentPage(Table table, Integer rowsPerPage, int pageCount) {
        int startRange = pageCount * rowsPerPage;
        int endRange = (pageCount * rowsPerPage) + rowsPerPage;
        if (endRange > table.getNumberOfRows()) {
            endRange = table.getNumberOfRows();
        }
        return Arrays.copyOfRange(table.getContent(), startRange, endRange);
    }

    private PDPage generatePage(PDDocument doc, Table table) {
        PDPage page = new PDPage();
        page.setMediaBox(table.getPageSize());
        page.setRotation(table.isLandscape() ? 90 : 0);
        doc.addPage(page);
        return page;
    }

    private PDPageContentStream generateContentStream(PDDocument doc, PDPage page, Table table) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, false, false);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        if (table.isLandscape()) {
            contentStream.concatenate2CTM(0, 1, -1, 0, table.getPageSize().getWidth(), 0);
        }
        contentStream.setFont(table.getTextFont(), table.getFontSize());
        return contentStream;
    }
}
