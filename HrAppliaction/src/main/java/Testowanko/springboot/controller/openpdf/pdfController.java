package Testowanko.springboot.controller.openpdf;

import Testowanko.springboot.model.Calendar;
import Testowanko.springboot.model.Department;
import Testowanko.springboot.model.Employee;
import Testowanko.springboot.model.Profession;
import Testowanko.springboot.service.CalendarService;
import Testowanko.springboot.service.DepartmentService;
import Testowanko.springboot.service.EmployeeService;
import Testowanko.springboot.service.ProfessionService;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
class pdfController {
    ProfessionService professionService;
    CalendarService calendarService;
    EmployeeService employeeService;
    DepartmentService departmentService;

    pdfController(final ProfessionService professionService, final CalendarService calendarService, final EmployeeService employeeService, final DepartmentService departmentService) {
        this.professionService = professionService;
        this.calendarService = calendarService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/departmentPdf")
    public void pdfxd(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDataTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = Lista_dzialów_ " +  currentDataTime+ ".pdf";

        response.setHeader(headerKey, headerValue);
        List<Department> listDepartments = departmentService.getAllDepartments();

        DepartmentPDFExporter exporter = new DepartmentPDFExporter(listDepartments);
        exporter.export(response);
    }

    @GetMapping("/professionPdf")
    public void professionPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDataTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = Lista_stanowisk_ " +  currentDataTime+ ".pdf";

        response.setHeader(headerKey, headerValue);
        List<Profession> listProfessions = professionService.getAllProfessions();

        ProfessionPDFExporter exporter = new ProfessionPDFExporter(listProfessions);
        exporter.export(response);
    }

    @GetMapping("/employeePdf")
    public void employeePdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDataTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = Lista_pracowników_ " +  currentDataTime+ ".pdf";

        response.setHeader(headerKey, headerValue);
        List<Employee> listEmployees = employeeService.getAllEmployees();

        EmployeePDFExporter exporter = new EmployeePDFExporter(listEmployees);
        exporter.export(response);
    }

    @GetMapping("/calendarPdf")
    public void calendarPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDataTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = Kalendarz_ " +  currentDataTime+ ".pdf";

        response.setHeader(headerKey, headerValue);
        List<Calendar> listCalendars = calendarService.getAllCalendars();

        CalendarPDFExporter exporter = new CalendarPDFExporter(listCalendars);
        exporter.export(response);
    }
}
