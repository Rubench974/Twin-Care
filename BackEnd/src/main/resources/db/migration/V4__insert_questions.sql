INSERT INTO question (id, texte, categorie, type_reponse, priorite, declencheur) VALUES

-- MODE DE VIE (HABITUDES_DE_VIE)

(1, 'Fumez-vous du tabac, même occasionnellement ?', 'HABITUDES_DE_VIE', 'OUI_NON', 'MOYENNE', 'Tous les ans'),
(2, 'Avez-vous fumé régulièrement (au moins une cigarette par jour) pendant plus d''un an dans votre vie ?', 'HABITUDES_DE_VIE', 'OUI_NON', 'MOYENNE', 'Si tabac = Non'),
(3, 'Consommez-vous de l''alcool au moins 3 jours par semaine ?', 'HABITUDES_DE_VIE', 'OUI_NON', 'MOYENNE', 'Tous les ans'),
(4, 'Passez-vous plus de 7 heures par jour devant un écran (ordinateur, téléphone, TV) ?', 'HABITUDES_DE_VIE', 'OUI_NON', 'FAIBLE', 'Tous les ans'),
(5, 'Restez-vous assis plus de 7 heures par jour (travail + loisirs) ?', 'HABITUDES_DE_VIE', 'OUI_NON', 'FAIBLE', 'Tous les ans'),
(6, 'Soulevez-vous ou portez-vous régulièrement des charges de plus de 10 kg dans le cadre de votre travail ?', 'HABITUDES_DE_VIE', 'OUI_NON', 'FAIBLE', 'Tous les ans'),
(7, 'Effectuez-vous des gestes répétitifs (poignets, épaules) plus de 4 heures par jour ?', 'HABITUDES_DE_VIE', 'OUI_NON', 'FAIBLE', 'Tous les ans'),
(8, 'Travaillez-vous en horaires décalés (finissant après minuit) ou de nuit ?', 'HABITUDES_DE_VIE', 'OUI_NON', 'FAIBLE', 'Tous les ans'),

-- ACTIVITÉ PHYSIQUE 

(9, 'Combien de jours par semaine pratiquez-vous au moins 30 minutes d''activité physique (marche rapide, sport) ?', 'ACTIVITE_PHYSIQUE', 'ECHELLE_0_7', 'MOYENNE', 'Tous les 6 mois'),
(10, 'Pratiquez-vous une activité physique principalement en extérieur ?', 'ACTIVITE_PHYSIQUE', 'OUI_NON', 'FAIBLE', 'Si activité > 0'),
(11, 'Êtes-vous essoufflé(e) en montant deux étages sans vous arrêter ?', 'ACTIVITE_PHYSIQUE', 'OUI_NON', 'HAUTE', 'Tous les ans'),
(12, 'Avez-vous remarqué une baisse de votre force (difficulté à ouvrir un bocal ou à porter un sac de courses) ?', 'ACTIVITE_PHYSIQUE', 'OUI_NON', 'HAUTE', 'Age > 65 ans'),

-- SOMMEIL 

(13, 'Sur une échelle de 1 (très mauvais) à 5 (excellent), comment évaluez-vous la qualité de votre sommeil ?', 'SOMMEIL', 'ECHELLE_1_5', 'MOYENNE', 'Tous les 6 mois'),
(14, 'Vous réveillez-vous fatigué(e) au moins 4 matins par semaine ?', 'SOMMEIL', 'OUI_NON', 'MOYENNE', 'Si sommeil < 3'),
(15, 'Votre entourage vous a-t-il dit que vous ronflez fort (entendu de l''autre pièce) ?', 'SOMMEIL', 'OUI_NON', 'MOYENNE', 'Tous les ans'),
(16, 'Vous a-t-on déjà signalé que vous arrêtiez de respirer pendant votre sommeil ?', 'SOMMEIL', 'OUI_NON', 'MOYENNE', 'Si ronflement = Oui'),
(17, 'Avez-vous des difficultés à vous endormir (plus de 30 minutes) au moins 3 nuits par semaine ?', 'SOMMEIL', 'OUI_NON', 'MOYENNE', 'Tous les 6 mois'),
(18, 'Vous réveillez-vous la nuit (plus de 2 fois) sans pouvoir vous rendormir facilement ?', 'SOMMEIL', 'OUI_NON', 'MOYENNE', 'Tous les 6 mois'),
(19, 'Ressentez-vous le besoin de faire une sieste (plus de 30 minutes) presque tous les jours ?', 'SOMMEIL', 'OUI_NON', 'FAIBLE', 'Tous les ans'),

-- ALIMENTATION 

(20, 'Mangez-vous au moins 5 portions (environ 400 g) de fruits et légumes par jour ?', 'ALIMENTATION', 'OUI_NON', 'MOYENNE', 'Tous les ans'),
(21, 'Buvez-vous au moins 1,5 litre d''eau par jour (hors café, thé, sodas) ?', 'ALIMENTATION', 'OUI_NON', 'FAIBLE', 'Tous les ans'),
(22, 'Ajoutez-vous systématiquement du sel à vos plats avant de les goûter ?', 'ALIMENTATION', 'OUI_NON', 'FAIBLE', 'Tous les ans'),
(23, 'Buvez-vous des sodas ou boissons sucrées tous les jours ?', 'ALIMENTATION', 'OUI_NON', 'MOYENNE', 'Tous les ans'),
(24, 'Avez-vous perdu plus de 5 % de votre poids en 3 mois sans régime ?', 'ALIMENTATION', 'OUI_NON', 'HAUTE', 'Tous les 6 mois'),
(25, 'Avez-vous des troubles du transit (constipation ou diarrhée) plus de 2 jours par semaine ?', 'ALIMENTATION', 'OUI_NON', 'MOYENNE', 'Tous les 6 mois'),

-- PRÉVENTION 

(26, 'Avez-vous reçu un rappel du vaccin antitétanique (DTP) depuis moins de 20 ans ?', 'PREVENTION', 'OUI_NON', 'MOYENNE', 'Age = 25, 45, 65 ans'),
(27, 'Avez-vous fait le vaccin contre la grippe cette année ?', 'PREVENTION', 'OUI_NON', 'HAUTE', 'Age > 65 ans + Automne'),
(28, 'Avez-vous réalisé un test de dépistage du cancer colorectal (recherche de sang dans les selles) depuis moins de 2 ans ?', 'PREVENTION', 'OUI_NON', 'MOYENNE', 'Age > 50 ans'),
(29, 'Avez-vous fait une mammographie de dépistage depuis moins de 2 ans ?', 'PREVENTION', 'OUI_NON', 'MOYENNE', 'Femme > 50 ans'),
(30, 'Avez-vous fait un frottis cervico-utérin (ou test HPV) depuis moins de 3 ans ?', 'PREVENTION', 'OUI_NON', 'MOYENNE', 'Femme 25-65 ans'),
(31, 'Avez-vous consulté un dentiste dans les 12 derniers mois ?', 'PREVENTION', 'OUI_NON', 'MOYENNE', 'Tous les ans'),
(32, 'Avez-vous consulté un ophtalmologue dans les 2 dernières années ?', 'PREVENTION', 'OUI_NON', 'MOYENNE', 'Tous les 2 ans'),
(33, 'Avez-vous eu une prise de sang avec bilan lipidique et glycémie dans les 3 dernières années ?', 'PREVENTION', 'OUI_NON', 'MOYENNE', 'Tous les 3 ans'),

-- ENVIRONNEMENT 

(34, 'Au printemps, avez-vous le nez qui coule ou les yeux qui grattent plusieurs jours consécutifs ?', 'ENVIRONNEMENT', 'OUI_NON', 'HAUTE', 'Printemps'),
(35, 'Sur une échelle de 1 à 5, à quel point vos allergies vous gênent-elles dans votre vie quotidienne ?', 'ENVIRONNEMENT', 'ECHELLE_1_5', 'MOYENNE', 'Si allergies = Oui'),
(36, 'Avez-vous un animal à poils (chat, chien, lapin) vivant principalement à l''intérieur ?', 'ENVIRONNEMENT', 'OUI_NON', 'FAIBLE', 'Dossier incomplet'),
(37, 'Y a-t-il des traces d''humidité ou de moisissures visibles dans votre logement ?', 'ENVIRONNEMENT', 'OUI_NON', 'FAIBLE', 'Dossier incomplet'),
(38, 'Ressentez-vous une gêne respiratoire (toux, essoufflement) lors des pics de pollution de l''air ?', 'ENVIRONNEMENT', 'OUI_NON', 'HAUTE', 'Dossier incomplet'),

-- DOULEURS 

(39, 'Sur une échelle de 1 (aucune douleur) à 5 (douleur maximale), quel est votre niveau de douleur habituel ?', 'DOULEURS', 'ECHELLE_1_5', 'HAUTE', 'Tous les 6 mois'),
(40, 'Avez-vous des douleurs dans le bas du dos (lombaires) plus de 10 jours par mois ?', 'DOULEURS', 'OUI_NON', 'HAUTE', 'Tous les 6 mois'),
(41, 'Avez-vous des maux de tête ou migraines plus de 5 jours par mois ?', 'DOULEURS', 'OUI_NON', 'HAUTE', 'Tous les 6 mois'),
(42, 'Avez-vous des douleurs articulaires (genoux, hanches, épaules) plus de 10 jours par mois ?', 'DOULEURS', 'OUI_NON', 'HAUTE', 'Tous les 6 mois'),

-- SANTÉ MENTALE 

(43, 'Sur une échelle de 1 (pas de stress) à 5 (stress extrême), quel est votre niveau de stress quotidien ?', 'SANTE_MENTALE', 'ECHELLE_1_5', 'HAUTE', 'Tous les 3 mois'),
(44, 'Vous sentez-vous triste ou démotivé(e) presque tous les jours depuis plus de 2 semaines ?', 'SANTE_MENTALE', 'OUI_NON', 'HAUTE', 'Si stress > 3'),
(45, 'Avez-vous des difficultés de concentration ou de mémoire qui vous gênent au quotidien ?', 'SANTE_MENTALE', 'OUI_NON', 'MOYENNE', 'Tous les 6 mois'),
(46, 'Avez-vous l''impression d''être débordé(e) par vos obligations familiales ou professionnelles ?', 'SANTE_MENTALE', 'OUI_NON', 'MOYENNE', 'Tous les 6 mois'),

-- GÉRIATRIE (signaux forts pour les +65 ans) 

(47, 'Avez-vous fait une chute (même sans gravité) au cours des 6 derniers mois ?', 'GERIATRIE', 'OUI_NON', 'HAUTE', 'Age > 65 ans'),
(48, 'Avez-vous peur de tomber lorsque vous marchez dehors ?', 'GERIATRIE', 'OUI_NON', 'HAUTE', 'Age > 65 ans'),
(49, 'Avez-vous du mal à suivre une conversation dans un endroit bruyant ?', 'GERIATRIE', 'OUI_NON', 'HAUTE', 'Age > 65 ans'),
(50, 'Malgré vos lunettes, avez-vous l''impression que votre vue baisse (difficulté à lire ou reconnaître un visage de loin) ?', 'GERIATRIE', 'OUI_NON', 'HAUTE', 'Age > 65 ans');