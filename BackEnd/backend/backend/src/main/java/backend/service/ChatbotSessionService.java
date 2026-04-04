package backend.service;

import backend.dto.ChatbotAnswerRequest;
import backend.dto.ChatbotQuestionDto;
import backend.dto.ChatbotSessionResponse;
import backend.entity.*;
import backend.exception.BadRequestException;
import backend.exception.ResourceNotFoundException;
import backend.dao.AppUtilisateurRepository;
import backend.dao.DossierPatientRepository;
import backend.dao.InteractionChatbotRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.time.Period;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatbotSessionService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AppUtilisateurRepository appUtilisateurRepository;
    private final DossierPatientRepository dossierPatientRepository;
    private final InteractionChatbotRepository interactionChatbotRepository;
    private final ChatbotTriggerEngineService triggerEngineService;
    private final ChatbotQuestionCatalogService catalogService;


    public ChatbotSessionService(AppUtilisateurRepository appUtilisateurRepository,
                             DossierPatientRepository dossierPatientRepository,
                             InteractionChatbotRepository interactionChatbotRepository,
                             ChatbotTriggerEngineService triggerEngineService,
                             ChatbotQuestionCatalogService catalogService) {
    this.appUtilisateurRepository = appUtilisateurRepository;
    this.dossierPatientRepository = dossierPatientRepository;
    this.interactionChatbotRepository = interactionChatbotRepository;
    this.triggerEngineService = triggerEngineService;
    this.catalogService = catalogService;


    }

    public ChatbotSessionResponse demarrerSession(Long patientId) {
    AppUtilisateur patient = appUtilisateurRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient introuvable"));

    DossierPatient dossier = dossierPatientRepository.findByPatientId(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Dossier introuvable"));

    PatientProfile profile = buildPatientProfile(patient, dossier);

    Map<Integer, LocalDateTime> lastAnswerDates = new HashMap<>();
    List<QuestionDefinition> allQuestions = catalogService.getAllQuestions();
    for (QuestionDefinition q : allQuestions) {
        interactionChatbotRepository.findLastAnswerDateByDossierAndQuestionId(dossier.getId(), q.getId())
            .ifPresent(date -> lastAnswerDates.put(q.getId(), date));
    }

    List<ChatbotQuestionDto> questions = triggerEngineService.selectQuestions(profile, lastAnswerDates);

    ChatbotSessionResponse response = new ChatbotSessionResponse();
    response.setPatientId(patientId);
    response.setDossierId(dossier.getId());
    response.setQuestions(questions);

    return response;
}
    @Transactional
    public InteractionChatbot enregistrerReponse(Long patientId, Long dossierId, ChatbotAnswerRequest request) {
        AppUtilisateur patient = appUtilisateurRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient introuvable"));

        DossierPatient dossier = dossierPatientRepository.findById(dossierId)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier introuvable"));

        InteractionChatbot interaction = new InteractionChatbot();
        interaction.setPatient(patient);
        interaction.setDossierPatient(dossier);
        interaction.setQuestionId(request.getQuestionId());
        interaction.setQuestion(request.getQuestion());
        interaction.setCategorie(request.getCategorie());

        TypeReponseChatbot type = request.getTypeReponse();
        interaction.setTypeReponse(type);

        if (type == TypeReponseChatbot.OUI_NON) {
            if (request.getReponseTexte() == null ||
                    (!"OUI".equalsIgnoreCase(request.getReponseTexte()) &&
                     !"NON".equalsIgnoreCase(request.getReponseTexte()))) {
                throw new BadRequestException("Réponse OUI/NON invalide");
            }
            interaction.setReponseTexte(request.getReponseTexte().toUpperCase());
        }

        if (type == TypeReponseChatbot.ECHELLE_1_5) {
            if (request.getReponseNumerique() == null || request.getReponseNumerique() < 1 || request.getReponseNumerique() > 5) {
                throw new BadRequestException("La réponse doit être comprise entre 1 et 5");
            }
            interaction.setReponseNumerique(request.getReponseNumerique());
        }

        if (type == TypeReponseChatbot.ECHELLE_0_7) {
            if (request.getReponseNumerique() == null || request.getReponseNumerique() < 0 || request.getReponseNumerique() > 7) {
                throw new BadRequestException("La réponse doit être comprise entre 0 et 7");
            }
            interaction.setReponseNumerique(request.getReponseNumerique());
        }
        if (type == TypeReponseChatbot.OUI_NON && "OUI".equalsIgnoreCase(request.getReponseTexte())) {
            interaction.setARevoirParProfessionnel(true);
        } else if (type == TypeReponseChatbot.ECHELLE_1_5 && request.getReponseNumerique() != null
                && request.getReponseNumerique() >= 4) {
            interaction.setARevoirParProfessionnel(true);
        } else if (type == TypeReponseChatbot.ECHELLE_0_7 && request.getReponseNumerique() != null
                && request.getReponseNumerique() >= 5) {
            interaction.setARevoirParProfessionnel(true);
        } else {
            interaction.setARevoirParProfessionnel(false);
        }

        log.info("Réponse enregistrée pour le patient {} question {}", patientId, request.getQuestionId());
        return interactionChatbotRepository.save(interaction);
    }
    @Transactional
    private PatientProfile buildPatientProfile(AppUtilisateur patient, DossierPatient dossier) {
        PatientProfile profile = new PatientProfile();
        profile.setPatientId(patient.getId());

        // MVP: valeurs simulées / calculées simplement
        profile.setAge(Period.between(patient.getDateNaissance(), LocalDate.now()).getYears());
        profile.setSexe(patient.getSexe());
        profile.setDossierIncomplet(dossier.getDocuments() == null || dossier.getDocuments().isEmpty());
        profile.setSaison(getCurrentSeason());

        List<InteractionChatbot> history = interactionChatbotRepository.findByDossierPatientId(dossier.getId());

        profile.setAllergiesConnues(history.stream()
                .anyMatch(i -> i.getCategorie() == CategorieChatbot.ENVIRONNEMENT
                        && "OUI".equalsIgnoreCase(i.getReponseTexte())));

        profile.setSommeilScore(history.stream()
                .filter(i -> i.getQuestionId() != null && i.getQuestionId() == 13)
                .map(InteractionChatbot::getReponseNumerique)
                .findFirst()
                .orElse(null));

        profile.setStressScore(history.stream()
                .filter(i -> i.getQuestionId() != null && i.getQuestionId() == 43)
                .map(InteractionChatbot::getReponseNumerique)
                .findFirst()
                .orElse(null));

        return profile;
    }

    @Transactional
    private String getCurrentSeason() {
        Month month = LocalDate.now().getMonth();

        return switch (month) {
            case MARCH, APRIL, MAY -> "PRINTEMPS";
            case JUNE, JULY, AUGUST -> "ETE";
            case SEPTEMBER, OCTOBER, NOVEMBER -> "AUTOMNE";
            default -> "HIVER";
        };
    }
}