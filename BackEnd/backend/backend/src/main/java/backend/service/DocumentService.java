package backend.service;

import backend.dto.CreateDocumentRequest;
import backend.entity.Document;
import backend.entity.DossierPatient;
import backend.dao.DocumentRepository;
import backend.dao.DossierPatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DossierPatientRepository dossierPatientRepository;

    public DocumentService(DocumentRepository documentRepository,
                           DossierPatientRepository dossierPatientRepository) {
        this.documentRepository = documentRepository;
        this.dossierPatientRepository = dossierPatientRepository;
    }

    public Document createDocument(Long dossierId, CreateDocumentRequest request) {
        DossierPatient dossier = dossierPatientRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        if (request.getType() == null || request.getDateDocument() == null) {
            throw new RuntimeException("Le type et la date du document sont obligatoires");
        }

        Document document = new Document();
        document.setType(request.getType());
        document.setDateDocument(request.getDateDocument());
        document.setNomFichier(request.getNomFichier());
        document.setCheminFichier(request.getCheminFichier());
        document.setPrescripteur(request.getPrescripteur());
        document.setCommentairePatient(request.getCommentairePatient());
        document.setDossierPatient(dossier);

        return documentRepository.save(document);
    }

    public List<Document> getDocumentsByDossier(Long dossierId) {
        return documentRepository.findByDossierPatientId(dossierId);
    }

    public Document getById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));
    }
}