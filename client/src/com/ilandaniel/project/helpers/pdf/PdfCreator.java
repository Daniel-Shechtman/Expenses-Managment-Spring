package com.ilandaniel.project.helpers.pdf;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfCreator {

    private static String[][] data;

    private static final PDRectangle PAGE_SIZE = PDRectangle.A4;
    private static final float MARGIN = 20;
    private static final boolean IS_LANDSCAPE = true;

    // Font configuration
    private static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE = 10;

    // Table configuration
    private static final float ROW_HEIGHT = 15;
    private static final float CELL_MARGIN = 2;


    public void createPdfFile(String filePath, String[][] dataArray, PdfDTO pdfDTO) {
        data = dataArray;

        try {
            new PDFTableGenerator().generatePDF(createContent(), filePath, pdfDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Table createContent() {
        // Total size of columns must not be greater than table width.
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("Category", 90));
        columns.add(new Column("Currency", 90));
        columns.add(new Column("Cost", 90));
        columns.add(new Column("Info", 300));
        columns.add(new Column("Date", 200));

        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);

        return new TableBuilder()
                .setCellMargin(CELL_MARGIN)
                .setColumns(columns)
                .setContent(data)
                .setHeight(tableHeight)
                .setNumberOfRows(data.length)
                .setRowHeight(ROW_HEIGHT)
                .setMargin(MARGIN)
                .setPageSize(PAGE_SIZE)
                .setLandscape(IS_LANDSCAPE)
                .setTextFont(TEXT_FONT)
                .setFontSize(FONT_SIZE)
                .build();
    }
}
