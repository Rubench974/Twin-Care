package backend.rest;

import backend.entity.AppUtilisateur;
import backend.repository.AppUtilisateurRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UtilisateurController {

    private final AppUtilisateurRepository utilisateurRepository;

    public UtilisateurController(AppUtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping
    public List<AppUtilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }
}