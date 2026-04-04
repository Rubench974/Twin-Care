package backend.dao;

import backend.entity.DossierPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DossierPatientRepository extends JpaRepository<DossierPatient, Long> {
    Optional<DossierPatient> findByPatientId(Long patientId);
}