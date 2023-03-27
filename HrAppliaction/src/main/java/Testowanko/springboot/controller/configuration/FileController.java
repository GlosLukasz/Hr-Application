package Testowanko.springboot.controller.configuration;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@Controller
class FileController {

    @RolesAllowed("user")
    @GetMapping("/fileEmployee")
    String fileEmployee(Model model,Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "fileEmployee";
    }

    @RolesAllowed("user")
    @GetMapping("/fileCalendar")
    String fileCalendar(Model model,Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "fileCalendar";
    }

    @RolesAllowed("user")
    @GetMapping("/home")
    String home(Model model,Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "home";
    }

    @RolesAllowed("user")
    @GetMapping("/help")
    String help(Model model, Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "help";
    }
}
