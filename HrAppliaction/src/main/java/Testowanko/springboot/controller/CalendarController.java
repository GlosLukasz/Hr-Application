package Testowanko.springboot.controller;

import Testowanko.springboot.model.Calendar;
import Testowanko.springboot.model.Employee;
import Testowanko.springboot.model.EmployeeRepository;
import Testowanko.springboot.service.CalendarService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class CalendarController {

    private final EmployeeRepository employeeRepository;
    private final CalendarService calendarService;

    CalendarController(final EmployeeRepository employeeRepository, final CalendarService calendarService) {
        this.employeeRepository = employeeRepository;
        this.calendarService = calendarService;
    }

    //Wyswietlanie kalendarza
    @GetMapping("/listCalendars")
    public String viewHomePage(Model model, Principal principal) {
        return findPaginated(1, "date", "asc", model,principal);
    }

    @RolesAllowed("user")
    @GetMapping("/fileCalendarAdd")
    public String showNewEmployeeForm(Model model,Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        var calendar = new Calendar();
        List<Employee> allEmployeesName = employeeRepository.findAll();
        model.addAttribute("calendar", calendar);
        model.addAttribute("allEmployees", employeeRepository.findAll());
        if(allEmployeesName.isEmpty()){
            model.addAttribute("isEmpty", "Musi najpiew zostać dodana osoba");
            return "indexAddCalendar";
        }
        return "indexAddCalendar";
    }

    @RolesAllowed("user")
    @PostMapping("/saveCalendar")
    public String saveEmployee(@ModelAttribute("calendar")@Valid Calendar current,
                               BindingResult result,
                               Model model,
                               Principal principal,
                               RedirectAttributes attributes,
                               @RequestParam(value="date", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        model.addAttribute("allEmployees", employeeRepository.findAll());
        if(result.hasErrors()){
            return "indexAddCalendar";
        }

        calendarService.saveCalendar(current);
        model.addAttribute("successAdd", "Poprawne dodanie");
        return "indexAddCalendar";
    }

    //Edycja działu
    @RolesAllowed("user")
    @GetMapping("/editCalendar/{id}")
    public String editDepartment(@PathVariable ( value = "id") int id, Model model,Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        model.addAttribute("allEmployees", employeeRepository.findAll());
        model.addAttribute("calendar", calendarService.getCalendarById(id));
        return "indexEditCalendar";
    }

    //Zapisywanie działu po edycji
    @RolesAllowed("user")
    @PostMapping("/saveEditCalendar")
    public String saveEditDepartment(@ModelAttribute("calendar")@Valid Calendar current,
                                     BindingResult result,
                                     Model model,Principal principal
                                    ) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        model.addAttribute("allEmployees", employeeRepository.findAll());
        if(result.hasErrors()){
            return "indexEditCalendar";
        }
        model.addAttribute("successEdit", "Poprawne edycja w kalendarzu");
        calendarService.saveCalendar(current);
        return "indexEditCalendar";
    }

    //Usuwanie działu
    @RolesAllowed("user")
    @GetMapping("/deleteCalendar/{id}")
    public String deleteEmployee(@PathVariable (value = "id") int id,Model model,Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        this.calendarService.deleteCalendarById(id);
        return "redirect:/listCalendars";
    }

    @RolesAllowed("user")
    @GetMapping("/calendar/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model,Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        int pageSize = 11;

        Page<Calendar> page = calendarService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Calendar> listCalendars= page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listCalendars", listCalendars);
        return "indexListOfCalendarsEditDelete";
    }
}
