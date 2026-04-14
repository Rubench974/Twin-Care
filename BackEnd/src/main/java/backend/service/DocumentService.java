package backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import backend.dao.DocumentRepository;
import backend.dao.DossierPatientRepository;
import backend.dto.DocumentUploadRequest;
import backend.entity.Document;
import backend.entity.DossierPatient;
import backend.entity.StatutDocument;
import backend.exception.BadRequestException;
import backend.exception.ResourceNotFoundException;

@Service
public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;
    private final DossierPatientRepository dossierPatientRepository;
    private final FileStorageService fileStorageService;
    private final DossierPatientService dossierPatientService;

    public DocumentService(DocumentRepository documentRepository,
                           DossierPatientRepository dossierPatientRepository,
                           FileStorageService fileStorageService,
                           DossierPatientService dossierPatientService) {
        this.documentRepository =document.setCheminFichier("uploads/" + storedFilename);
        documentRepository;
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
        document.setCheminFichier(storedFilename);
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

    @Transactional
    public void deleteDocument(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document introuvable"));

        documentRepository.delete(document);
        log.info("Document supprimé : ID {}", documentId);
    }
}