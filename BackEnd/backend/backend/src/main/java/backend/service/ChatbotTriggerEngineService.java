package backend.service;

import backend.dto.ChatbotQuestionDto;
import backend.entity.PatientProfile;
import backend.entity.QuestionDefinition;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChatbotTriggerEngineService {

    private final ChatbotQuestionCatalogService catalogService;

    public ChatbotTriggerEngineService(ChatbotQuestionCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public List<ChatbotQuestionDto> selectQuestions(PatientProfile profile, Map<Integer, LocalDateTime> lastAnswerDates) {
        List<QuestionDefinition> allQuestions = catalogService.getAllQuestions();
        List<ChatbotQuestionDto> selected = new ArrayList<>();
        for (QuestionDefinition q : allQuestions) {
            LocalDateTime lastDate = lastAnswerDates != null ? lastAnswerDates.get(q.getId()) : null;
            if (matchesTrigger(q, profile, lastDate)) {
                selected.add(new ChatbotQuestionDto(q.getId(), q.getCategorie(), q.getQuestion(), q.getTypeReponse()));
            }
            if (selected.size() >= 2) break;
        }
        return selected;
    }

    private boolean matchesTrigger(QuestionDefinition q, PatientProfile profile, LocalDateTime lastAnswerDate) {
        String trigger = q.getDeclencheur();
        if (trigger == null || trigger.isBlank()) return false;

        // Règles sans date
        if ("Dossier incomplet".equalsIgnoreCase(trigger))
            return profile.isDossierIncomplet();
        if ("Printemps".equalsIgnoreCase(trigger))
            return "PRINTEMPS".equalsIgnoreCase(profile.getSaison());
        if ("Age > 65 ans".equalsIgnoreCase(trigger))
            return profile.getAge() != null && profile.getAge() > 65;
        if ("Age > 65 ans + Automne".equalsIgnoreCase(trigger))
            return profile.getAge() != null && profile.getAge() > 65 && "AUTOMNE".equalsIgnoreCase(profile.getSaison());
        if ("Si allergies = Oui".equalsIgnoreCase(trigger))
            return profile.isAllergiesConnues();
        if ("Si sommeil < 3".equalsIgnoreCase(trigger))
            return profile.getSommeilScore() != null && profile.getSommeilScore() < 3;
        if ("Si stress > 3".equalsIgnoreCase(trigger))
            return profile.getStressScore() != null && profile.getStressScore() > 3;

        // Règles de fréquence (tous les X mois/ans)
        if (trigger.startsWith("Tous les")) {
            if (lastAnswerDate == null) return true;
            long months = extractMonths(trigger);
            return lastAnswerDate.plusMonths(months).isBefore(LocalDateTime.now());
        }

        return false;
    }

    private long extractMonths(String trigger) {
        if (trigger.contains("3 mois")) return 3;
        if (trigger.contains("6 mois")) return 6;
        if (trigger.contains("1 an") || trigger.contains("ans")) return 12;
        if (trigger.contains("2 ans")) return 24;
        if (trigger.contains("3 ans")) return 36;
        return 0;
    }
}