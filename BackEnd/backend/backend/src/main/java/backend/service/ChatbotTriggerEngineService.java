package backend.service;

import backend.dto.ChatbotQuestionDto;
import backend.entity.PatientProfile;
import backend.entity.QuestionDefinition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatbotTriggerEngineService {

    private final ChatbotQuestionCatalogService catalogService;

    public ChatbotTriggerEngineService(ChatbotQuestionCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public List<ChatbotQuestionDto> selectQuestions(PatientProfile profile) {
        List<QuestionDefinition> allQuestions = catalogService.getAllQuestions();
        List<ChatbotQuestionDto> selected = new ArrayList<>();

        for (QuestionDefinition q : allQuestions) {
            if (matchesTrigger(q, profile)) {
                selected.add(new ChatbotQuestionDto(
                        q.getId(),
                        q.getCategorie(),
                        q.getQuestion(),
                        q.getTypeReponse()
                ));
            }

            if (selected.size() >= 2) {
                break;
            }
        }

        return selected;
    }

    private boolean matchesTrigger(QuestionDefinition q, PatientProfile profile) {
        String trigger = q.getDeclencheur();

        if (trigger == null || trigger.isBlank()) {
            return false;
        }

        if ("Dossier incomplet".equalsIgnoreCase(trigger)) {
            return profile.isDossierIncomplet();
        }

        if ("Printemps".equalsIgnoreCase(trigger)) {
            return "PRINTEMPS".equalsIgnoreCase(profile.getSaison());
        }

        if ("Age > 65 ans".equalsIgnoreCase(trigger)) {
            return profile.getAge() != null && profile.getAge() > 65;
        }

        if ("Age > 65 ans + Automne".equalsIgnoreCase(trigger)) {
            return profile.getAge() != null
                    && profile.getAge() > 65
                    && "AUTOMNE".equalsIgnoreCase(profile.getSaison());
        }

        if ("Si allergies = Oui".equalsIgnoreCase(trigger)) {
            return profile.isAllergiesConnues();
        }

        if ("Si sommeil < 3".equalsIgnoreCase(trigger)) {
            return profile.getSommeilScore() != null && profile.getSommeilScore() < 3;
        }

        if ("Si stress > 3".equalsIgnoreCase(trigger)) {
            return profile.getStressScore() != null && profile.getStressScore() > 3;
        }

        if ("Tous les ans".equalsIgnoreCase(trigger)
                || "Tous les 6 mois".equalsIgnoreCase(trigger)
                || "Tous les 3 mois".equalsIgnoreCase(trigger)
                || "Tous les 2 ans".equalsIgnoreCase(trigger)
                || "Tous les 3 ans".equalsIgnoreCase(trigger)) {
            return true;
        }

        return false;
    }
}