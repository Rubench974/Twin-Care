package backend.rest;

import backend.dto.ChatbotAnswerRequest;
import backend.dto.ChatbotSessionResponse;
import backend.entity.InteractionChatbot;
import backend.service.ChatbotSessionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin("*")
public class ChatbotController {

    private final ChatbotSessionService chatbotSessionService;

    public ChatbotController(ChatbotSessionService chatbotSessionService) {
        this.chatbotSessionService = chatbotSessionService;
    }

    @GetMapping("/session/patient/{patientId}")
    public ChatbotSessionResponse demarrerSession(@PathVariable Long patientId) {
        return chatbotSessionService.demarrerSession(patientId);
    }

    @PostMapping("/session/patient/{patientId}/dossier/{dossierId}/answer")
    public InteractionChatbot enregistrerReponse(@PathVariable Long patientId,
                                                 @PathVariable Long dossierId,
                                                 @RequestBody ChatbotAnswerRequest request) {
        return chatbotSessionService.enregistrerReponse(patientId, dossierId, request);
    }
}