package backend.service;

import backend.dto.ValidationRequest;
import backend.entity.*;
import backend.exception.ResourceNotFoundException;
import backend.exception.UnauthorizedActionException;
import backend.dao.AppUtilisateurRepository;
import backend.dao.DocumentRepository;
import backend.dao.ValidationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValidationService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final ValidationRepository validationRepository;
    private final DocumentRepository documentRepository;
    private final AppUtilisateurRepository appUtilisateurRepository;
    private final DossierPatientService dossierPatientService;

    public ValidationService(ValidationRepository validationRepository,
                             DocumentRepository documentRepository,
                             AppUtilisateurRepository appUtilisateurRepository,
                             DossierPatientService dossierPatientService) {
        this.validationRepository = validationRepository;
        this.documentRepository = documentRepository;
        this.appUtilisateurRepository = appUtilisateurRepository;
        this.dossierPatientService = dossierPatientService;
    }

    @Transactional
    public Validation validerDocument(Long documentId, ValidationRequest request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document introuvable"));

        AppUtilisateur assistant = appUtilisateurRepository.findById(request.getAssistantMedicalId())
                .orElseThrow(() -> new ResourceNotFoundException("Assistant médical introuvable"));

        if (assistant.getRole() != Role.ASSISTANT_MEDICAL && assistant.getRole() != Role.MEDECIN) {
            throw new UnauthorizedActionException("Seul un assistant médical ou un médecin peut valider un document");
        }

        if (document.getValidation() != null) {
            throw new RuntimeException("Ce document a déjà été validé");
}

        Validation validation = new Validation();
        validation.setDocument(document);
        validation.setAssistantMedical(assistant);
        validation.setDecision(request.getDecision());
        validation.setCommentaire(request.getCommentaire());

        if (request.getDecision() == DecisionValidation.VALIDER) {
            document.setStatut(StatutDocument.VALIDE);
        } else if (request.getDecision() == DecisionValidation.REFUSER) {
            document.setStatut(StatutDocument.REFUSE);
        } else {
            throw new RuntimeException("Décision de validation invalide");
        }

        documentRepository.save(document);
        Validation savedValidation = validationRepository.save(validation);
        log.info("Document {} {} par {}", documentId, request.getDecision(), assistant.getEmail());

        Long dossierId = document.getDossierPatient().getId();
        dossierPatientService.mettreAJourStatutDossier(dossierId);

        return savedValidation;
    }
}