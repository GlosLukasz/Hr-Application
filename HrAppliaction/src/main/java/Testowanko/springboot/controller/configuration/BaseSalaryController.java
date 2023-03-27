package Testowanko.springboot.controller.configuration;

import Testowanko.springboot.model.DepartmentRepository;
import Testowanko.springboot.model.Profession;
import Testowanko.springboot.service.ProfessionService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class BaseSalaryController {
    private final ProfessionService professionService;
    private final DepartmentRepository departmentRepository;

    BaseSalaryController(final ProfessionService professionService, final DepartmentRepository departmentRepository) {
        this.professionService = professionService;
        this.departmentRepository = departmentRepository;
    }

    //Wyswietlanie listy stanowisk
    @GetMapping("/configurationComponentsOfSalariesBaseSalary")
    public String viewHomePage(Model model,Principal principal) {
        return findPaginated1(1, "positionName", "asc", model,principal);
    }

    @GetMapping("/configurationComponentsOfSalariesBaseSalary/page/{pageNo}")
    public String findPaginated1(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                 Model model,
                                 Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        int pageSize = 15;

        Page<Profession> page = professionService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Profession> listProfessions= page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listProfessions", listProfessions);
        return "configurationComponentsOfSalariesBaseSalary";
    }
}
