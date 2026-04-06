Phase de test sur Postman :
    1. register : Methode Post, lien http://localhost:8080/api/auth/register et on rempli le Body avec un JSON qui va remplir le constructeur => retourne un Utilisateur
    2. login : Methode Post, lien http://localhost:8080/api/auth/login et on rempli le Body avec JSON qui va reprendre mail et mdp => retourne Token , email, role
    3. Chatbot : Methode Get, lien http://localhost:8080/api/chatbot/session/patient/1 et on rempli le Header avec Authorization en key et Bearer Token avec le Token recu juste avant => QuestionChatbot cohérente
    4. Document : Methode Post, lien http://localhost:8080/api/documents/upload/dossier/1 Header comme pour le Chatbot + KEY	TYPE	VALUE
                            file	File	(choisir un PDF)
                            type	Text	ORDONNANCE
                            dateDocument	Text	2025-03-20 => Document