package backend.service;

import backend.entity.DossierPatient;
import backend.dao.DossierPatientRepository;
import org.springframework.stereotype.Service;

@Service
public class DossierPatientService {

    private final DossierPatientRepository dossierPatientRepository;

    public DossierPatientService(DossierPatientRepository dossierPatientRepository) {
        this.dossierPatientRepository = dossierPatientRepository;
    }

    public DossierPatient getByPatientId(Long patientId) {
        return dossierPatientRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Dossier patient introuvable"));
    }

    public DossierPatient getById(Long dossierId) {
        return dossierPatientRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));
    }
}