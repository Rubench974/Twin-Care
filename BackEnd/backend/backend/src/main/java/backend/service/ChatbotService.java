package backend.service;

import backend.dto.ChatbotRequest;
import backend.entity.AppUtilisateur;
import backend.entity.DossierPatient;
import backend.entity.InteractionChatbot;
import backend.dao.AppUtilisateurRepository;
import backend.dao.DossierPatientRepository;
import backend.dao.InteractionChatbotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatbotService {

    private final InteractionChatbotRepository interactionChatbotRepository;
    private final AppUtilisateurRepository appUtilisateurRepository;
    private final DossierPatientRepository dossierPatientRepository;

    public ChatbotService(InteractionChatbotRepository interactionChatbotRepository,
                          AppUtilisateurRepository appUtilisateurRepository,
                          DossierPatientRepository dossierPatientRepository) {
        this.interactionChatbotRepository = interactionChatbotRepository;
        this.appUtilisateurRepository = appUtilisateurRepository;
        this.dossierPatientRepository = dossierPatientRepository;
    }

    public InteractionChatbot saveInteraction(Long patientId, Long dossierId, ChatbotRequest request) {
        AppUtilisateur patient = appUtilisateurRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        DossierPatient dossier = dossierPatientRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        InteractionChatbot interaction = new InteractionChatbot();
        interaction.setPatient(patient);
        interaction.setDossierPatient(dossier);
        interaction.setQuestion(request.getQuestion());
        interaction.setReponse(request.getReponse());
        interaction.setCategorie(request.getCategorie());

        return interactionChatbotRepository.save(interaction);
    }

    public List<InteractionChatbot> getByDossier(Long dossierId) {
        return interactionChatbotRepository.findByDossierPatientId(dossierId);
    }
}