package Testowanko.springboot.controller.unit;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@Controller
class UnitController {
    @RolesAllowed("user")
    @GetMapping("/unit")
    String unit(Model model,Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "unit";
    }

    @RolesAllowed("user")
    @GetMapping("/unitDepartment")
    String unitDepartment(Model model,Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "unitDepartment";
    }

    @RolesAllowed("user")
    @GetMapping("/unitProfession")
    String unitProfession(Model model, Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        model.addAttribute("signedName",accessToken.getGivenName() + " " + accessToken.getFamilyName());
        return "unitProfession";
    }
}
