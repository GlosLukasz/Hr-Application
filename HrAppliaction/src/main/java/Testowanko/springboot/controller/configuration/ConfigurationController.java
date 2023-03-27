package Testowanko.springboot.controller.configuration;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
class ConfigurationController {
    @RolesAllowed("user")
    @GetMapping("/configuration")
    String configuration(Model model, Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "configuration";
    }

    @PostMapping("/logout")
    String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/home";
    }

    @RolesAllowed("user")
    @GetMapping("/configurationComponentsOfSalariesPermia")
    String configurationComponentsOfSalariesPermia(Model model, Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "configurationComponentsOfSalariesPermia";
    }

    @RolesAllowed("user")
    @GetMapping("/configurationComponentsOfSalariesAdditionalAnnualSalary")
    String configurationComponentsOfSalariesAdditionalAnnualSalary(Model model, Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "configurationComponentsOfSalariesAdditionalAnnualSalary";
    }

    @RolesAllowed("user")
    @GetMapping("/configurationComponents")
    String configurationComponents(Model model, Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "configurationComponentsOfSalaries";
    }

    @RolesAllowed("user")
    @GetMapping("/file")
    String file(Model model, Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "file";
    }
}
