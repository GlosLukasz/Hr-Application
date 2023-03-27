package Testowanko.springboot.controller;

import Testowanko.springboot.model.Department;
import Testowanko.springboot.service.DepartmentService;
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

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	//Wyswietlanie listy działów
	@RolesAllowed("user")
	@GetMapping("/listDepartments")
	public String viewHomePage(Model model, Principal principal) {
		return findPaginated(1, "departmentName", "asc", model,principal);
	}

	//Dodawanie działu
	@RolesAllowed("user")
	@GetMapping("/unitDepartmentAdd")
	public String showNewEmployeeForm(Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		Department department = new Department();
		model.addAttribute("department", department);
		return "indexAddDepartment";
	}

	//Zapisywanie działu
	@RolesAllowed("user")
	@PostMapping("/saveDepartment")
	public String saveEmployee(@ModelAttribute("department")@Valid Department current,
							   BindingResult result,
							   Model model,
							   Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		if(result.hasErrors()){
			return "indexAddDepartment";
		}
		current.setDepartmentAdress(current.getDepartmentAdress().substring(0,1).toUpperCase() + current.getDepartmentAdress().substring(1).toLowerCase());
		current.setDepartmentName(current.getDepartmentName().substring(0,1).toUpperCase() + current.getDepartmentName().substring(1).toLowerCase());

		departmentService.saveDepartment(current);
		model.addAttribute("successEdit", "Poprawne dodanie działu");
		return "indexAddDepartment";
	}

	//Edycja działu
	@RolesAllowed("user")
	@GetMapping("/editDepartment/{id}")
	public String editDepartment(@PathVariable ( value = "id") int id, Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		Department department = departmentService.getDepartmentById(id);

		model.addAttribute("department", department);
		return "indexEditDepartment";
	}

	//Zapisywanie działu po edycji
	@RolesAllowed("user")
	@PostMapping("/saveEditDepartment")
	public String saveEditDepartment(@ModelAttribute("department") Department department,Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		model.addAttribute("successEdit", "Poprawna edycja działu");
		departmentService.saveDepartment(department);
		return "indexEditDepartment";
	}

	//Usuwanie działu
	@RolesAllowed("user")
	@GetMapping("/deleteDepartment/{id}")
	public String deleteEmployee(@PathVariable (value = "id") int id,Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		this.departmentService.deleteDepartmentById(id);
		return "redirect:/listDepartments";
	}

	@RolesAllowed("user")
	@GetMapping("/department/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
								Model model,Principal principal) {
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
		AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
		model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
		int pageSize = 11;
		
		Page<Department> page = departmentService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Department> listDepartments= page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("listDepartments", listDepartments);
		return "indexListOfDepartmentsEditDelete";
	}
}
