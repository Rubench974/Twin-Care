package backend.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dao.InteractionChatbotRepository;
import backend.entity.InteractionChatbot;

@RestController
@RequestMapping("/api/interactions")
public class InteractionChatbotController {

    private final InteractionChatbotRepository interactionRepository;

    public InteractionChatbotController(InteractionChatbotRepository interactionRepository) {
        this.interactionRepository = interactionRepository;
    }

    @GetMapping("/dossier/{dossierId}")
    public List<InteractionChatbot> getByDossier(@PathVariable Long dossierId) {
        return interactionRepository.findByDossierPatientId(dossierId);
    }
}