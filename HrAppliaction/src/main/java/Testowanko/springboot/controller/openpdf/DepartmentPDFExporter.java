package Testowanko.springboot.controller.openpdf;

import Testowanko.springboot.model.Department;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DepartmentPDFExporter {
    private List<Department> departmentList;

    DepartmentPDFExporter(final List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setPaddingTop(10);
        cell.setPaddingBottom(10);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(15);

        cell.setPhrase(new Phrase("Nazwa dzialu", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Adres dzialu", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {

        for (Department department : departmentList) {
            table.addCell(String.valueOf(department.getDepartmentName()));
            table.addCell(String.valueOf(department.getDepartmentAdress()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        PdfPTable table = new PdfPTable(2);
        table.setSpacingBefore(15);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);
        font.setSize(18);

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDataTime = dateFormatter.format(new Date());

        Paragraph title1 = new Paragraph("HrApplication ");
        title1.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(title1);
        Paragraph title3 = new Paragraph(currentDataTime);
        title3.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(title3);

        Paragraph title = new Paragraph("Lista dzialów", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}
