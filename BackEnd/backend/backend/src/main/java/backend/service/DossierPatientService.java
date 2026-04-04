package backend.service;

import backend.entity.Document;
import backend.entity.DossierPatient;
import backend.entity.StatutDocument;
import backend.entity.StatutDossier;
import backend.exception.ResourceNotFoundException;
import backend.dao.DocumentRepository;
import backend.dao.DossierPatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DossierPatientService {

    private final DossierPatientRepository dossierPatientRepository;
    private final DocumentRepository documentRepository;

    public DossierPatientService(DossierPatientRepository dossierPatientRepository,
                                 DocumentRepository documentRepository) {
        this.dossierPatientRepository = dossierPatientRepository;
        this.documentRepository = documentRepository;
    }

    public DossierPatient getByPatientId(Long patientId) {
        return dossierPatientRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier patient introuvable"));
    }

    public DossierPatient getById(Long dossierId) {
        return dossierPatientRepository.findById(dossierId)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier introuvable"));
    }

    public void mettreAJourStatutDossier(Long dossierId) {
        DossierPatient dossier = getById(dossierId);
        List<Document> documents = documentRepository.findByDossierPatientId(dossierId);

        if (documents.isEmpty()) {
            dossier.setStatut(StatutDossier.A_COMPLETER);
        } else if (documents.stream().allMatch(doc -> doc.getStatut() == StatutDocument.VALIDE)) {
            dossier.setStatut(StatutDossier.PRET_POUR_CONSULTATION);
        } else {
            dossier.setStatut(StatutDossier.EN_COURS);
        }

        dossierPatientRepository.save(dossier);
    }
}