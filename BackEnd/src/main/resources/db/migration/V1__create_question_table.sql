CREATE TABLE question (
    id SERIAL PRIMARY KEY,
    texte VARCHAR(500) NOT NULL,
    categorie VARCHAR(50) NOT NULL,
    type_reponse VARCHAR(20) NOT NULL,
    priorite VARCHAR(10) NOT NULL,
    declencheur VARCHAR(255)
);