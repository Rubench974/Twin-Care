package backend.rest;

import backend.entity.AppUtilisateur;
import backend.entity.Document;
import backend.entity.Role;
import backend.service.DocumentService;
import backend.service.SecurityHelperService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final DocumentService documentService;
    private final SecurityHelperService securityHelperService;

    public FileController(DocumentService documentService,
                          SecurityHelperService securityHelperService) {
        this.documentService = documentService;
        this.securityHelperService = securityHelperService;
    }

    @GetMapping("/document/{documentId}")
    public ResponseEntity<Resource> serveDocument(@PathVariable Long documentId) {
        try {
            Document document = documentService.getById(documentId);
            AppUtilisateur currentUser = securityHelperService.getCurrentUser();

            boolean isOwner = document.getDossierPatient().getPatient().getId().equals(currentUser.getId());
            boolean isMedicalStaff = currentUser.getRole() == Role.ASSISTANT_MEDICAL || currentUser.getRole() == Role.MEDECIN;

            if (!isOwner && !isMedicalStaff) {
                return ResponseEntity.status(403).build();
            }

            String storedFilename = Paths.get(document.getCheminFichier()).getFileName().toString();
            Path filePath = Paths.get(uploadDir).resolve(storedFilename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + document.getNomFichier() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}