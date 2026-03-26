package backend.service;

import backend.dto.DocumentUploadRequest;
import backend.entity.Document;
import backend.entity.DossierPatient;
import backend.entity.StatutDocument;
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
    private final DossierPatientService dossierPatientService;

    public DocumentService(DocumentRepository documentRepository,
                           DossierPatientRepository dossierPatientRepository,
                           FileStorageService fileStorageService,
                           DossierPatientService dossierPatientService) {
        this.documentRepository = documentRepository;
        this.dossierPatientRepository = dossierPatientRepository;
        this.fileStorageService = fileStorageService;
        this.dossierPatientService = dossierPatientService;
    }

    public Document uploadDocument(Long dossierId,
                                   MultipartFile file,
                                   DocumentUploadRequest request) {

        DossierPatient dossier = dossierPatientRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Le fichier est obligatoire");
        }

        if (request.getType() == null) {
            throw new RuntimeException("Le type du document est obligatoire");
        }

        if (request.getDateDocument() == null) {
            throw new RuntimeException("La date du document est obligatoire");
        }

        String storedFilename = fileStorageService.storeFile(file);

        Document document = new Document();
        document.setType(request.getType());
        document.setDateDocument(request.getDateDocument());
        document.setNomFichier(file.getOriginalFilename());
        document.setCheminFichier("uploads/" + storedFilename);
        document.setPrescripteur(request.getPrescripteur());
        document.setCommentairePatient(request.getCommentairePatient());
        document.setStatut(StatutDocument.EN_ATTENTE);
        document.setDossierPatient(dossier);

        Document saved = documentRepository.save(document);
        dossierPatientService.mettreAJourStatutDossier(dossierId);

        return saved;
    }

    public List<Document> getDocumentsByDossier(Long dossierId) {
        return documentRepository.findByDossierPatientId(dossierId);
    }

    public Document getById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));
    }
}