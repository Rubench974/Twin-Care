package backend.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.EnvironnementDTO;
import backend.service.EnvironnementService;

@RestController
@RequestMapping("/api/environnement")
public class EnvironnementController {

    private final EnvironnementService environnementService;

    public EnvironnementController(EnvironnementService environnementService) {
        this.environnementService = environnementService;
    }

    @GetMapping("/donnees")
    public EnvironnementDTO getDonnees(@RequestParam double lat, @RequestParam double lon) {
        return environnementService.getDonneesEnvironnement(lat, lon);
    }
}