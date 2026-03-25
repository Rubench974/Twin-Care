package backend.rest;

import backend.dto.ChatbotRequest;
import backend.entity.InteractionChatbot;
import backend.service.ChatbotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin("*")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/patient/{patientId}/dossier/{dossierId}")
    public InteractionChatbot saveInteraction(@PathVariable Long patientId,
                                              @PathVariable Long dossierId,
                                              @RequestBody ChatbotRequest request) {
        return chatbotService.saveInteraction(patientId, dossierId, request);
    }

    @GetMapping("/dossier/{dossierId}")
    public List<InteractionChatbot> getByDossier(@PathVariable Long dossierId) {
        return chatbotService.getByDossier(dossierId);
    }
}