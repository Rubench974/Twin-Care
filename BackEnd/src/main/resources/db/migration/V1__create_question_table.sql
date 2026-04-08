CREATE TABLE app_utilisateur (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    sexe VARCHAR(30) NOT NULL
);

CREATE TABLE question (
    id SERIAL PRIMARY KEY,
    texte VARCHAR(500) NOT NULL,
    categorie VARCHAR(50) NOT NULL,
    type_reponse VARCHAR(20) NOT NULL,
    priorite VARCHAR(10) NOT NULL,
    declencheur VARCHAR(255)
);

CREATE TABLE dossier_patient (
    id BIGSERIAL PRIMARY KEY,
    date_creation TIMESTAMP NOT NULL,
    statut VARCHAR(40) NOT NULL,
    patient_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_dossier_patient_utilisateur FOREIGN KEY (patient_id) REFERENCES app_utilisateur(id)
);

CREATE TABLE document (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    date_document DATE NOT NULL,
    date_depot TIMESTAMP NOT NULL,
    statut VARCHAR(30) NOT NULL,
    nom_fichier VARCHAR(255) NOT NULL,
    chemin_fichier VARCHAR(500) NOT NULL,
    prescripteur VARCHAR(255),
    commentaire_patient VARCHAR(1000),
    dossier_patient_id BIGINT NOT NULL,
    CONSTRAINT fk_document_dossier FOREIGN KEY (dossier_patient_id) REFERENCES dossier_patient(id)
);

CREATE TABLE validation (
    id BIGSERIAL PRIMARY KEY,
    date_validation TIMESTAMP NOT NULL,
    decision VARCHAR(30) NOT NULL,
    commentaire VARCHAR(1000),
    document_id BIGINT NOT NULL UNIQUE,
    assistant_medical_id BIGINT NOT NULL,
    CONSTRAINT fk_validation_document FOREIGN KEY (document_id) REFERENCES document(id),
    CONSTRAINT fk_validation_assistant FOREIGN KEY (assistant_medical_id) REFERENCES app_utilisateur(id)
);

CREATE TABLE interaction_chatbot (
    id BIGSERIAL PRIMARY KEY,
    date_interaction TIMESTAMP,
    question_id INTEGER,
    question VARCHAR(500) NOT NULL,
    categorie VARCHAR(100),
    type_reponse VARCHAR(30),
    reponse_texte VARCHAR(255),
    reponse_numerique INTEGER,
    declaratif_patient BOOLEAN NOT NULL,
    a_revoir_par_professionnel BOOLEAN NOT NULL,
    patient_id BIGINT NOT NULL,
    dossier_patient_id BIGINT NOT NULL,
    CONSTRAINT fk_interaction_patient FOREIGN KEY (patient_id) REFERENCES app_utilisateur(id),
    CONSTRAINT fk_interaction_dossier FOREIGN KEY (dossier_patient_id) REFERENCES dossier_patient(id)
);