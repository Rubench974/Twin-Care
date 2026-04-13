package backend.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dao.AppUtilisateurRepository;
import backend.entity.AppUtilisateur;

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