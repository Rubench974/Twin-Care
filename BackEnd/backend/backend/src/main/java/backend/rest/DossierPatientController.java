package backend.rest;

import backend.entity.DossierPatient;
import backend.service.DossierPatientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dossiers")
public class DossierPatientController {

    private final DossierPatientService dossierPatientService;

    public DossierPatientController(DossierPatientService dossierPatientService) {
        this.dossierPatientService = dossierPatientService;
    }

    @GetMapping("/patient/{patientId}")
    public DossierPatient getByPatientId(@PathVariable Long patientId) {
        return dossierPatientService.getByPatientId(patientId);
    }

    @GetMapping("/{dossierId}")
    public DossierPatient getById(@PathVariable Long dossierId) {
        return dossierPatientService.getById(dossierId);
    }
}