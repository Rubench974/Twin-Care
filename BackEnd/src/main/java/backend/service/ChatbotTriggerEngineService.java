// backend/service/ChatbotTriggerEngineService.java
package backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import backend.dto.ChatbotQuestionDto;
import backend.entity.PatientProfile;
import backend.entity.Priorite;
import backend.entity.QuestionDefinition;

@Service
public class ChatbotTriggerEngineService {

    private final ChatbotQuestionCatalogService catalogService;

    public ChatbotTriggerEngineService(ChatbotQuestionCatalogService catalogService) {
        this.catalogService = catalogService;
    }

      public List<ChatbotQuestionDto> selectQuestions(PatientProfile profile,
                                                    Map<Integer, LocalDateTime> lastAnswerDates,
                                                    int limit) {
        List<QuestionDefinition> allQuestions = catalogService.getAllQuestions();

        // Tri par priorité : HAUTE (1), MOYENNE (2), FAIBLE (3)
        allQuestions.sort(Comparator.comparingInt(q -> {
            Priorite p = q.getPriorite();
            if (p == null) return 4; // les questions sans priorité à la fin
            switch (p) {
                case HAUTE: return 1;
                case MOYENNE: return 2;
                case FAIBLE: return 3;
                default: return 4;
            }
        }));

        List<ChatbotQuestionDto> selected = new ArrayList<>();
        for (QuestionDefinition q : allQuestions) {
            LocalDateTime lastDate = lastAnswerDates != null ? lastAnswerDates.get(q.getId()) : null;
            if (matchesTrigger(q, profile, lastDate)) {
                selected.add(new ChatbotQuestionDto(q.getId(), q.getCategorie(), q.getQuestion(), q.getTypeReponse()));
            }
            if (selected.size() >= limit) {
                break;
            }
        }
        return selected;
    }

    private boolean matchesTrigger(QuestionDefinition q, PatientProfile profile, LocalDateTime lastAnswerDate) {
        String trigger = q.getDeclencheur();

        if (trigger == null || trigger.isBlank() || "Aucun".equalsIgnoreCase(trigger)) {
            return true; 
        }

        if ("Femme > 50 ans".equalsIgnoreCase(trigger))
            return profile.getSexe() != null && "FEMME".equalsIgnoreCase(profile.getSexe().toString()) && profile.getAge() != null && profile.getAge() > 50;
            
        if ("Femme 25-65 ans".equalsIgnoreCase(trigger))
            return profile.getSexe() != null && "FEMME".equalsIgnoreCase(profile.getSexe().toString()) && profile.getAge() != null && profile.getAge() >= 25 && profile.getAge() <= 65;
            
        if ("Age = 25, 45, 65 ans".equalsIgnoreCase(trigger))
            return profile.getAge() != null && (profile.getAge() == 25 || profile.getAge() == 45 || profile.getAge() == 65);

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

        if ("Si ronflement = Oui".equalsIgnoreCase(trigger))
            return true; 

        if ("Dossier incomplet".equalsIgnoreCase(trigger))
            return profile.isDossierIncomplet();
            
        if ("Printemps".equalsIgnoreCase(trigger))
            return "PRINTEMPS".equalsIgnoreCase(profile.getSaison());

        if (trigger.startsWith("Tous les")) {
            if (lastAnswerDate == null) return true;
            long months = extractMonths(trigger);
            return lastAnswerDate.plusMonths(months).isBefore(LocalDateTime.now());
        }

        return true;
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