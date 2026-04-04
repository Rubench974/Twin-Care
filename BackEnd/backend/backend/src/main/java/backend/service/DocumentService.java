package backend.service;

import backend.dto.DocumentUploadRequest;
import backend.entity.Document;
import backend.entity.DossierPatient;
import backend.entity.StatutDocument;
import backend.exception.BadRequestException;
import backend.exception.ResourceNotFoundException;
import backend.dao.DocumentRepository;
import backend.dao.DossierPatientRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

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

    @Transactional
    public Document uploadDocument(Long dossierId,
                                   MultipartFile file,
                                   DocumentUploadRequest request) {

        DossierPatient dossier = dossierPatientRepository.findById(dossierId)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier introuvable"));

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Le fichier est obligatoire");
        }

        if (request.getType() == null) {
            throw new BadRequestException("Le type du document est obligatoire");
        }

        if (request.getDateDocument() == null) {
            throw new BadRequestException("La date du document est obligatoire");
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
        log.info("Document uploadé : {} pour le dossier {}", document.getNomFichier(), dossierId);

        return saved;
    }

    public List<Document> getDocumentsByDossier(Long dossierId) {
        return documentRepository.findByDossierPatientId(dossierId);
    }

    public Document getById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document introuvable"));
    }
}