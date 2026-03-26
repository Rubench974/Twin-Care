package backend.rest;

import backend.entity.Document;
import backend.entity.TypeDocument;
import backend.service.DocumentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
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
}