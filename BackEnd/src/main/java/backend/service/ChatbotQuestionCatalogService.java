package backend.service;

import backend.entity.QuestionDefinition;
import backend.entity.TypeReponseChatbot;
import backend.entity.CategorieChatbot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatbotQuestionCatalogService {

    public List<QuestionDefinition> getAllQuestions() {
        List<QuestionDefinition> questions = new ArrayList<>();

        questions.add(new QuestionDefinition(12, CategorieChatbot.ACTIVITE_PHYSIQUE,
                "Avez-vous remarqué une baisse de votre force physique ces derniers mois ?",
                TypeReponseChatbot.OUI_NON, "Age > 65 ans"));

        questions.add(new QuestionDefinition(13, CategorieChatbot.SOMMEIL,
                "Sur une échelle de 1 à 5, comment évaluez-vous la qualité de votre sommeil actuellement ?",
                TypeReponseChatbot.ECHELLE_1_5, "Tous les 6 mois"));

        questions.add(new QuestionDefinition(14, CategorieChatbot.SOMMEIL,
                "Vous réveillez-vous souvent avec une sensation de fatigue le matin ?",
                TypeReponseChatbot.OUI_NON, "Si sommeil < 3"));

        questions.add(new QuestionDefinition(27, CategorieChatbot.PREVENTION,
                "Avez-vous fait (ou prévu de faire) le vaccin contre la grippe cette année ?",
                TypeReponseChatbot.OUI_NON, "Age > 65 ans + Automne"));

        questions.add(new QuestionDefinition(34, CategorieChatbot.ENVIRONNEMENT,
                "Avez-vous souvent le nez qui coule ou les yeux qui grattent à l'arrivée du printemps ?",
                TypeReponseChatbot.OUI_NON, "Printemps"));

        questions.add(new QuestionDefinition(35, CategorieChatbot.ENVIRONNEMENT,
                "Sur une échelle de 1 à 5, à quel point vos allergies vous gênent-elles au quotidien en ce moment ?",
                TypeReponseChatbot.ECHELLE_1_5, "Si allergies = Oui"));

        questions.add(new QuestionDefinition(36, CategorieChatbot.ENVIRONNEMENT,
                "Avez-vous des animaux de compagnie à poils à l'intérieur de votre domicile ?",
                TypeReponseChatbot.OUI_NON, "Dossier incomplet"));

        questions.add(new QuestionDefinition(37, CategorieChatbot.ENVIRONNEMENT,
                "Votre logement présente-t-il des traces d'humidité ou de moisissures ?",
                TypeReponseChatbot.OUI_NON, "Dossier incomplet"));

        questions.add(new QuestionDefinition(43, CategorieChatbot.SANTE_MENTALE,
                "Sur une échelle de 1 à 5, quel est votre niveau de stress général en ce moment ?",
                TypeReponseChatbot.ECHELLE_1_5, "Tous les 3 mois"));

        questions.add(new QuestionDefinition(44, CategorieChatbot.SANTE_MENTALE,
                "Vous sentez-vous souvent triste ou démotivé(e) ces dernières semaines ?",
                TypeReponseChatbot.OUI_NON, "Si stress > 3"));

        return questions;
    }
}