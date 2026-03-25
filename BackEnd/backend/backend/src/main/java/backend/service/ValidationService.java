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
    private final AppUtilisateurRepository appUtilisateurRepository;

    public ValidationService(ValidationRepository validationRepository,
                             DocumentRepository documentRepository,
                             AppUtilisateurRepository appUtilisateurRepository) {
        this.validationRepository = validationRepository;
        this.documentRepository = documentRepository;
        this.appUtilisateurRepository = appUtilisateurRepository;
    }

    public Validation validerDocument(Long documentId, ValidationRequest request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));

        AppUtilisateur assistant = appUtilisateurRepository.findById(request.getAssistantMedicalId())
                .orElseThrow(() -> new RuntimeException("Assistant médical introuvable"));

        Validation validation = new Validation();
        validation.setDocument(document);
        validation.setAssistantMedical(assistant);
        validation.setDecision(request.getDecision());
        validation.setCommentaire(request.getCommentaire());

        if (request.getDecision() == DecisionValidation.VALIDER) {
            document.setStatut(StatutDocument.VALIDE);
        } else {
            document.setStatut(StatutDocument.REFUSE);
        }

        documentRepository.save(document);
        return validationRepository.save(validation);
    }
}
