package backend.service;

import backend.dto.ValidationRequest;
import backend.entity.*;
import backend.dao.AppUtilisateurRepository;
import backend.dao.DocumentRepository;
import backend.dao.ValidationRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private final ValidationRepository validationRepository;
    private final DocumentRepository documentRepository;
    private final AppUtilisateurRepository AppUtilisateurRepository;
    private final DossierPatientService dossierPatientService;

    public ValidationService(ValidationRepository validationRepository,
                             DocumentRepository documentRepository,
                             AppUtilisateurRepository AppUtilisateurRepository,
                             DossierPatientService dossierPatientService) {
        this.validationRepository = validationRepository;
        this.documentRepository = documentRepository;
        this.AppUtilisateurRepository = AppUtilisateurRepository;
        this.dossierPatientService = dossierPatientService;
    }

    public Validation validerDocument(Long documentId, ValidationRequest request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));

        AppUtilisateur assistant = AppUtilisateurRepository.findById(request.getAssistantMedicalId())
                .orElseThrow(() -> new RuntimeException("Assistant médical introuvable"));

        if (assistant.getRole() != Role.ASSISTANT_MEDICAL && assistant.getRole() != Role.MEDECIN) {
            throw new RuntimeException("Seul un assistant médical ou un médecin peut valider un document");
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

        Long dossierId = document.getDossierPatient().getId();
        dossierPatientService.mettreAJourStatutDossier(dossierId);

        return savedValidation;
    }
}