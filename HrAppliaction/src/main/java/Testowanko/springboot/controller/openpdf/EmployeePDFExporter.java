package Testowanko.springboot.controller.openpdf;


import Testowanko.springboot.model.Employee;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
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

public class EmployeePDFExporter {
    private List<Employee> departmentEmployee;

    EmployeePDFExporter(final List<Employee> departmentEmployee) {
        this.departmentEmployee = departmentEmployee;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setPaddingTop(10);
        cell.setPaddingBottom(10);

        Font font = FontFactory.getFont(BaseFont.IDENTITY_H);
        font.setSize(15);

        cell.setPhrase(new Phrase("Imie",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nazwisko",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Dzial",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Stanowisko",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("email",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Numer telefonu",font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table){

        for(Employee employee: departmentEmployee){
            table.addCell(String.valueOf(employee.getFirstName()));
            table.addCell(String.valueOf(employee.getLastName()));
            table.addCell(String.valueOf(employee.getProfession().getDepartment().getDepartmentName()));
            table.addCell(String.valueOf(employee.getProfession().getPositionName()));
            table.addCell(String.valueOf(employee.getEmail()));
            table.addCell(String.valueOf(employee.getNumberPhone()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException,IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();

        PdfPTable table = new PdfPTable(6);
        table.setSpacingBefore(15);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);
        font.setSize(18);

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDataTime = dateFormatter.format(new Date());

        Paragraph title1 = new Paragraph("HrApplication ");
        title1.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(title1);
        Paragraph title3= new Paragraph(currentDataTime);
        title3.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(title3);

        Paragraph title = new Paragraph("Lista pracowników", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}


