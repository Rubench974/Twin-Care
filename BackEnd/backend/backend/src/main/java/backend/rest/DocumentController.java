package backend.rest;

import backend.dto.CreateDocumentRequest;
import backend.entity.Document;
import backend.service.DocumentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/dossier/{dossierId}")
    public Document createDocument(@PathVariable Long dossierId,
                                   @RequestBody CreateDocumentRequest request) {
        return documentService.createDocument(dossierId, request);
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