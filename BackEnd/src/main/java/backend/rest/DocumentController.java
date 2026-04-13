package backend.rest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.entity.Document;
import backend.entity.TypeDocument;
import backend.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value = "/upload/dossier/{dossierId}", consumes = {"multipart/form-data"})
    public Document uploadDocument(@PathVariable Long dossierId,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam("type") TypeDocument type,
                                   @RequestParam("dateDocument")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDocument,
                                   @RequestParam(value = "prescripteur", required = false) String prescripteur,
                                   @RequestParam(value = "commentairePatient", required = false) String commentairePatient) {

        backend.dto.DocumentUploadRequest request = new backend.dto.DocumentUploadRequest();
        request.setType(type);
        request.setDateDocument(dateDocument);
        request.setPrescripteur(prescripteur);
        request.setCommentairePatient(commentairePatient);

        return documentService.uploadDocument(dossierId, file, request);
    }

    @GetMapping("/dossier/{dossierId}")
    public List<Document> getDocumentsByDossier(@PathVariable Long dossierId) {
        return documentService.getDocumentsByDossier(dossierId);
    }

    @GetMapping("/{documentId}")
    public Document getDocumentById(@PathVariable Long documentId) {
        return documentService.getById(documentId);
    }

    @DeleteMapping("/{documentId}")
    public void deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
    }
}