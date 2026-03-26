package backend.service;

import backend.dto.DocumentUploadRequest;
import backend.entity.Document;
import backend.entity.DossierPatient;
import backend.dao.DocumentRepository;
import backend.dao.DossierPatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DossierPatientRepository dossierPatientRepository;
    private final FileStorageService fileStorageService;

    public DocumentService(DocumentRepository documentRepository,
                           DossierPatientRepository dossierPatientRepository,
                           FileStorageService fileStorageService) {
        this.documentRepository = documentRepository;
        this.dossierPatientRepository = dossierPatientRepository;
        this.fileStorageService = fileStorageService;
    }

    public Document uploadDocument(Long dossierId,
                                   MultipartFile file,
                                   DocumentUploadRequest request) {

        DossierPatient dossier = dossierPatientRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        if (request.getType() == null || request.getDateDocument() == null) {
            throw new RuntimeException("Le type et la date du document sont obligatoires");
        }

        String storedFilename = fileStorageService.storeFile(file);

        Document document = new Document();
        document.setType(request.getType());
        document.setDateDocument(request.getDateDocument());
        document.setNomFichier(file.getOriginalFilename());
        document.setCheminFichier("uploads/" + storedFilename);
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