package Testowanko.springboot.controller;

import Testowanko.springboot.model.Department;
import Testowanko.springboot.model.DepartmentRepository;
import Testowanko.springboot.model.Profession;
import Testowanko.springboot.service.ProfessionService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProfessionController {


	private final ProfessionService professionService;
	private final DepartmentRepository departmentRepository;

	ProfessionController(final ProfessionService professionService, final DepartmentRepository departmentRepository) {
		this.professionService = professionService;
		this.departmentRepository = departmentRepository;
	}

	//Wyswietlanie listy stanowisk
	@RolesAllowed("user")
	@GetMapping("/listProfessions")
	public String viewHomePage(Model model, Principal principal) {
		return findPaginated(1, "positionName", "asc", model,principal);
	}


	//Dodawanie stanowiska
	@RolesAllowed("user")
	@GetMapping("/unitProfessionAdd")
	public String showNewEmployeeForm(Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		List<Department> allDepartmentsName = departmentRepository.findAll();

		model.addAttribute("allDepartments", allDepartmentsName);
		model.addAttribute("profession", new Profession());

		if(allDepartmentsName.isEmpty()){
			model.addAttribute("isEmpty", "Aby dodać stanowsiko, musisz najpierw utworzyć dział");
			return "indexAddProfession";
		}
		return "indexAddProfession";
	}

	//Zapisywanie stanowiska
	@RolesAllowed("user")
	@PostMapping("/saveProfession")
	public String saveEmployee(@ModelAttribute("profession")@Valid Profession current,
							   BindingResult result,
							   Model model,Principal principal,
							   RedirectAttributes attributes) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		model.addAttribute("allDepartments", departmentRepository.findAll());
		if(result.hasErrors()){
			return "indexAddProfession";
		}

		current.setPositionName(current.getPositionName().substring(0,1).toUpperCase() + current.getPositionName().substring(1).toLowerCase());

		professionService.saveProfession(current);
		model.addAttribute("successEdit", "Poprawne dodanie stanowiska");
		model.addAttribute("allDepartments", departmentRepository.findAll());
		return "indexAddProfession";
	}

	//Edycja stanowiska
	@RolesAllowed("user")
	@GetMapping("/editProfession/{id}")
	public String editDepartment(@PathVariable ( value = "id") int id, Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());

		model.addAttribute("allDepartments", departmentRepository.findAll());
		model.addAttribute("profession", professionService.getProfessionById(id));
		return "indexEditProfession";
	}

	//Zapisywanie stanowiska po edycji
	@RolesAllowed("user")
	@PostMapping("/saveEditProfession")
	public String saveEditDepartment(@ModelAttribute("profession")@Valid Profession current,
									 BindingResult result,
									 Model model,Principal principal,
									 RedirectAttributes attributes) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		model.addAttribute("allDepartments", departmentRepository.findAll());
		if(result.hasErrors()){
			return "indexEditProfession";
		}
		current.setPositionName(current.getPositionName().substring(0,1).toUpperCase() + current.getPositionName().substring(1).toLowerCase());
		model.addAttribute("successEdit", "Poprawne edycja stanowiska");
		professionService.saveProfession(current);
		return "indexEditProfession";
	}

	//Usuwanie stanowiska
	@RolesAllowed("user")
	@GetMapping("/deleteProfession/{id}")
	public String deleteEmployee(@PathVariable (value = "id") int id,Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		this.professionService.deleteProfessionById(id);
		return "redirect:/listProfessions";
	}

	@RolesAllowed("user")
	@GetMapping("/profession/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
								Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		int pageSize = 11;
		
		Page<Profession> page = professionService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Profession> listProfessions= page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("listProfessions", listProfessions);
		return "indexListOfProfessionsEditDelete";
	}


}
