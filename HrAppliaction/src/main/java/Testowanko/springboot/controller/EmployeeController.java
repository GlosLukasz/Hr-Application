package Testowanko.springboot.controller;

import Testowanko.springboot.model.Employee;
import Testowanko.springboot.model.EmployeeRepository;
import Testowanko.springboot.model.Profession;
import Testowanko.springboot.model.ProfessionRepository;
import Testowanko.springboot.service.EmployeeService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.Page;
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
import java.util.List;

@Controller
public class EmployeeController {
	private final ProfessionRepository professionRepository;
	private final EmployeeService employeeService;
	private final EmployeeRepository employeeRepository;

	EmployeeController(final ProfessionRepository professionRepository, final EmployeeService employeeService, final EmployeeRepository employeeRepository) {
		this.professionRepository = professionRepository;
		this.employeeService = employeeService;
		this.employeeRepository = employeeRepository;
	}

	//Wyswietlanie listy osób
	@RolesAllowed("user")
	@GetMapping("/listEmployees")
	public String viewHomePage(Model model, Principal principal) {
		return findPaginated(1, "firstName", "asc", model,principal);
	}

	//Dodawanie osoby
	@RolesAllowed("user")
	@GetMapping("/fileEmployeeAdd")
	public String showNewEmployeeForm(Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		List<Profession> allProfessionsName = professionRepository.findAll();

		model.addAttribute("allProfessions", allProfessionsName);
		model.addAttribute("employee", new Employee());

		if(allProfessionsName.isEmpty()){
			model.addAttribute("isEmpty", "Aby dodać osobę, musisz najpierw utworzyć stanowiska");
			return "indexAddEmployee";
		}
		return "indexAddEmployee";
	}

	//Zapisywanie osoby
	@RolesAllowed("user")
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee")@Valid Employee current,
							   BindingResult result,
							   Model model,Principal principal,
							   RedirectAttributes attributes) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		model.addAttribute("allProfessions", professionRepository.findAll());
		if(result.hasErrors()){
			return "indexAddEmployee";
		}

		current.setFirstName(current.getFirstName().substring(0,1).toUpperCase() + current.getFirstName().substring(1).toLowerCase());
		current.setLastName(current.getLastName().substring(0,1).toUpperCase() + current.getLastName().substring(1).toLowerCase());

		employeeService.saveEmployee(current);
		model.addAttribute("successEdit", "Poprawne dodanie osoby");
		return "indexAddEmployee";
	}

	//Edycja osoby
	@RolesAllowed("user")
	@GetMapping("/editEmployee/{id}")
	public String editEmployee(@PathVariable ( value = "id") int id, Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());

		model.addAttribute("allProfessions", professionRepository.findAll());
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		return "indexEditEmployee";
	}

	//Zapisywanie osoby po edycji
	@RolesAllowed("user")
	@PostMapping("/saveEditEmployee")
	public String saveEditEmployee(@ModelAttribute("employee")@Valid Employee current,
									 BindingResult result,
								   Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		model.addAttribute("allProfessions", professionRepository.findAll());
		if(result.hasErrors()){
			return "indexEditEmployee";
		}
		current.setFirstName(current.getFirstName().substring(0,1).toUpperCase() + current.getFirstName().substring(1).toLowerCase());
		current.setLastName(current.getLastName().substring(0,1).toUpperCase() + current.getLastName().substring(1).toLowerCase());
		model.addAttribute("successEdit", "Poprawne edycja osoby");
		employeeService.saveEmployee(current);
		return "indexEditEmployee";
	}

	//Usuwanie osoby
	@RolesAllowed("user")
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") int id,Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/listEmployees";
	}

	@RolesAllowed("user")
	@GetMapping("/employee/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
								Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		int pageSize = 11;
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees= page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("listEmployees", listEmployees);
		return "indexListOfEmployeesEditDelete";
	}
}
