package backend.rest;

import backend.dto.ValidationRequest;
import backend.entity.Validation;
import backend.service.ValidationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validations")
@CrossOrigin("*")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/document/{documentId}")
    public Validation validerDocument(@PathVariable Long documentId,
                                      @RequestBody ValidationRequest request) {
        return validationService.validerDocument(documentId, request);
    }
}
