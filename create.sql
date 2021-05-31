drop table RESERVATION;
drop table EMPRUNT;
drop table EXEMPLAIRE;
drop table LIVRE;
drop table ETUDIANT;


create table ETUDIANT
(
    id_et  int generated as identity primary key,
    nom    varchar(50) not null,
    prenom varchar(50) not null,
    mdp    varchar(50) not null,
    email  varchar(50) not null UNIQUE
)
/
create table LIVRE
(
    id_liv    int generated as identity primary key,
    auteur    varchar(50)  not null,
    titre     varchar(200) not null
)
/
create table EXEMPLAIRE
(
    id_ex  int generated as identity PRIMARY KEY,
    id_liv int not null,
    foreign key (id_liv) references LIVRE (id_liv)
)
/
create table EMPRUNT
(
    date_emp    date not null,
    date_retour date not null,
    id_ex       int  not null UNIQUE,
    id_et       int  not null,
    foreign key (id_et) references ETUDIANT (id_et),
    foreign key (id_ex) references EXEMPLAIRE(id_ex)
)
/
create table RESERVATION
(
    date_res     date not null,
    date_fin_res date not null,
    id_liv       int  not null,
    id_et        int  not null,
    foreign key (id_et) references ETUDIANT (id_et),
    foreign key (id_liv) references LIVRE (id_liv)
)
/

insert into ETUDIANT (nom, prenom, mdp, email) VALUES ('Rouffe', 'Jean-Paul', 'azerty', 'email@gmail.com');
insert into ETUDIANT (nom, prenom, mdp, email) VALUES ('Rogeaux', 'Eliott', 'emdépé', 'eliott@gmail.com');
insert into ETUDIANT (nom, prenom, mdp, email) VALUES ('Lay', 'Stéphane', 'SuruSuruDesu', 'slayer@gmail.com');
insert into ETUDIANT (nom, prenom, mdp, email) VALUES ('Simoes', 'Alipio', '123456', 'pilipio@gmail.com');

insert into LIVRE (auteur, titre) VALUES ('Un mètre cinquante', 'Le TITRE');
insert into LIVRE (auteur, titre) VALUES ('Un mètre cinquante', 'Le TITRE 2 remake !');
insert into LIVRE (auteur, titre) VALUES ('Chugon', 'Solo Leveling Tome 1');
INSERT INTO LIVRE (auteur, titre) VALUES ('Foenkinos David','Charlotte');
INSERT INTO LIVRE (auteur, titre) VALUES ('Carrère Emmanuel','Le Royaume');
INSERT INTO LIVRE (auteur, titre) VALUES ('Slimani Leïla','Chanson douce');
INSERT INTO LIVRE (auteur, titre) VALUES ('Ferrante Elena','L''amie prodigieuse (Tome 2) - Le nouveau nom');
INSERT INTO LIVRE (auteur, titre) VALUES ('Viel Tanguy','Article 353 du code pénal');
INSERT INTO LIVRE (auteur, titre) VALUES ('Walsh Rosie','Les jours de ton absence');
INSERT INTO LIVRE (auteur, titre) VALUES ('Ferney Alice','Les Bourgeois');
INSERT INTO LIVRE (auteur, titre) VALUES ('Vargas Fred','Temps glaciaires');
INSERT INTO LIVRE (auteur, titre) VALUES ('Kerangal Maylis de','Réparer les vivants');
INSERT INTO LIVRE (auteur, titre) VALUES ('Shalev Zeruya','Ce qui reste de nos vies');
INSERT INTO LIVRE (auteur, titre) VALUES ('Del Arbol Victor','La Veille de presque tout');
INSERT INTO LIVRE (auteur, titre) VALUES ('Vuillard Eric','L''ordre du jour');
INSERT INTO LIVRE (auteur, titre) VALUES ('Läckberg Camilla','La sorcière');
INSERT INTO LIVRE (auteur, titre) VALUES ('Paasilinna Arto','Les mille et une gaffes de l''ange gardien Ariel Auvinen');
INSERT INTO LIVRE (auteur, titre) VALUES ('Modiano Patrick','Pour que tu ne te perdes pas dans le quartier');
INSERT INTO LIVRE (auteur, titre) VALUES ('Bagieu Pénélope','Culottées. Des femmes qui ne font que ce qu''elles veulent (Tome 2)');
INSERT INTO LIVRE (auteur, titre) VALUES ('Lagercrantz David','Millénium 4 - Ce qui ne me tue pas');
INSERT INTO LIVRE (auteur, titre) VALUES ('Rufin Jean-Christophe','Le collier rouge');
INSERT INTO LIVRE (auteur, titre) VALUES ('Japp Andrea H.','Barbarie 2.0');
INSERT INTO LIVRE (auteur, titre) VALUES ('Nesbo Jo','Du sang sur la glace');
INSERT INTO LIVRE (auteur, titre) VALUES ('Hegland Jean','Dans la forêt');
INSERT INTO LIVRE (auteur, titre) VALUES ('Zeniter Alice','L''Art de perdre');
INSERT INTO LIVRE (auteur, titre) VALUES ('Penny Louise','Le beau mystère');
INSERT INTO LIVRE (auteur, titre) VALUES ('Négar Djavadi','Désorientale');
INSERT INTO LIVRE (auteur, titre) VALUES ('Marsh Willa','Une famille délicieuse');
INSERT INTO LIVRE (auteur, titre) VALUES ('Dugain Marc','Ils vont tuer Robert Kennedy');
INSERT INTO LIVRE (auteur, titre) VALUES ('Schulman Ninni','Le Garçon qui ne pleurait plus');
INSERT INTO LIVRE (auteur, titre) VALUES ('Salem Carlos','Attends-moi au ciel');
INSERT INTO LIVRE (auteur, titre) VALUES ('Gallay Claudie','La Beauté des jours');
INSERT INTO LIVRE (auteur, titre) VALUES ('May Peter','Les disparus du phare');
INSERT INTO LIVRE (auteur, titre) VALUES ('Rouart Jean-Marie','Ne pars pas avant moi');
INSERT INTO LIVRE (auteur, titre) VALUES ('Harmel Kristin','L''Heure indigo');
INSERT INTO LIVRE (auteur, titre) VALUES ('Kellerman Jonathan','Les soeurs ennemies');
INSERT INTO LIVRE (auteur, titre) VALUES ('Horst Jorn Lier','Fermé pour l''hiver');
INSERT INTO LIVRE (auteur, titre) VALUES ('Matar Hisham','La terre qui les sépare');
INSERT INTO LIVRE (auteur, titre) VALUES ('Bachi Salim','Dieu Allah moi et les autres');
INSERT INTO LIVRE (auteur, titre) VALUES ('Labro Philippe','Ma mère cette inconnue');
INSERT INTO LIVRE (auteur, titre) VALUES ('Frioux Dalibor','Incident voyageurs');
INSERT INTO LIVRE (auteur, titre) VALUES ('Padura Fuentes Leonardo','Hérétiques');
INSERT INTO LIVRE (auteur, titre) VALUES ('Menegoz Mathias','Karpathia');
INSERT INTO LIVRE (auteur, titre) VALUES ('Bartelt Franz','Hôtel du Grand Cerf');
INSERT INTO LIVRE (auteur, titre) VALUES ('Parsons Tony','Des garçons bien élevés');
INSERT INTO LIVRE (auteur, titre) VALUES ('Rouquette Anne','Émilie voit quelqu''un - Après la psy le beau temps ?');
INSERT INTO LIVRE (auteur, titre) VALUES ('Zafon Carlos ruiz','Le Labyrinthe des esprits');
INSERT INTO LIVRE (auteur, titre) VALUES ('Trillard Marc','L''anniversaire du roi');
INSERT INTO LIVRE (auteur, titre) VALUES ('Joncour Serge','L''écrivain national');
INSERT INTO LIVRE (auteur, titre) VALUES ('McDermid Val','Châtiments');
INSERT INTO LIVRE (auteur, titre) VALUES ('d''Aillon Jean','Rouen 1203');
INSERT INTO LIVRE (auteur, titre) VALUES ('Kellerman Jonathan','Un maniaque dans la ville');
INSERT INTO LIVRE (auteur, titre) VALUES ('Wilson Robert Charles','Blind Lake');
INSERT INTO LIVRE (auteur, titre) VALUES ('Flournoy Angela','La Maison des Turner');
INSERT INTO LIVRE (auteur, titre) VALUES ('Wohlsdorf Gina','Sécurité');
INSERT INTO LIVRE (auteur, titre) VALUES ('Ferrari Jérôme','À son image');
INSERT INTO LIVRE (auteur, titre) VALUES ('Bonin Cyril','La délicatesse');
INSERT INTO LIVRE (auteur, titre) VALUES ('Hill Nathan','Les fantômes du vieux pays');
INSERT INTO LIVRE (auteur, titre) VALUES ('Higgins Clark Mary','Le Temps des regrets');
INSERT INTO LIVRE (auteur, titre) VALUES ('Slimani Leïla','Dans le jardin de l''ogre');
INSERT INTO LIVRE (auteur, titre) VALUES ('Reinhardt Éric','L''amour et les forêts');
INSERT INTO LIVRE (auteur, titre) VALUES ('Abécassis Eliette','Un secret du docteur Freud');
INSERT INTO LIVRE (auteur, titre) VALUES ('Ma Jian','La route sombre');
INSERT INTO LIVRE (auteur, titre) VALUES ('Levy Deborah','Sous l''eau');
INSERT INTO LIVRE (auteur, titre) VALUES ('Nesbo Jo','Soleil de nuit');
INSERT INTO LIVRE (auteur, titre) VALUES ('Ellory R. J.','Un coeur sombre');
INSERT INTO LIVRE (auteur, titre) VALUES ('Cook Thomas H.','Danser dans la poussière');
INSERT INTO LIVRE (auteur, titre) VALUES ('Melo Patricia','Feu follet');
INSERT INTO LIVRE (auteur, titre) VALUES ('Modiano Patrick','Une Jeunesse');
INSERT INTO LIVRE (auteur, titre) VALUES ('Busquets Milena','Ça aussi ça passera');
INSERT INTO LIVRE (auteur, titre) VALUES ('Jablonka Ivan','Laëtitia ou la fin des hommes');
INSERT INTO LIVRE (auteur, titre) VALUES ('Miller Jax','Les infâmes');
INSERT INTO LIVRE (auteur, titre) VALUES ('Chavouet Florent','L''île Louvre');
INSERT INTO LIVRE (auteur, titre) VALUES ('Duroy Lionel','Eugenia');
INSERT INTO LIVRE (auteur, titre) VALUES ('Flanagan Joe','Un moindre mal');
INSERT INTO LIVRE (auteur, titre) VALUES ('Ruskovich Emily','Idaho');
INSERT INTO LIVRE (auteur, titre) VALUES ('Enard Mathias','Boussole');
INSERT INTO LIVRE (auteur, titre) VALUES ('Alexieva Elena','Le prix Nobel');
INSERT INTO LIVRE (auteur, titre) VALUES ('Colin Fabrice','La poupée de Kafka');
INSERT INTO LIVRE (auteur, titre) VALUES ('Baricco Alessandro','Mr Gwyn');
INSERT INTO LIVRE (auteur, titre) VALUES ('Lansdale Joe R.','Les mécanos de Vénus');
INSERT INTO LIVRE (auteur, titre) VALUES ('Ahern Cecelia','Tombée du ciel');
INSERT INTO LIVRE (auteur, titre) VALUES ('Baricco Alessandro','Trois fois dès l''aube');
INSERT INTO LIVRE (auteur, titre) VALUES ('Ferus Jim','La Vengeance des mères');
INSERT INTO LIVRE (auteur, titre) VALUES ('Clément Catherine','Indu Boy');
INSERT INTO LIVRE (auteur, titre) VALUES ('Malamud Bernard','Le commis');
INSERT INTO LIVRE (auteur, titre) VALUES ('Gonthier Nicole','Les Chants de la mort');
INSERT INTO LIVRE (auteur, titre) VALUES ('Urbani Ellen','Landfall');
INSERT INTO LIVRE (auteur, titre) VALUES ('Baricco Alessandro','La Jeune Épouse');
INSERT INTO LIVRE (auteur, titre) VALUES ('Groff Lauren','Les Furies');
INSERT INTO LIVRE (auteur, titre) VALUES ('Ghosh Amitav','Un déluge de feu');
INSERT INTO LIVRE (auteur, titre) VALUES ('Penny Louise','Une illusion d''optique');
INSERT INTO LIVRE (auteur, titre) VALUES ('Runcie James','Sidney Chambers et les périls de la nuit');
INSERT INTO LIVRE (auteur, titre) VALUES ('Geni Abby','Farallon Islands');
INSERT INTO LIVRE (auteur, titre) VALUES ('Lothar Ernst','Mélodie de Vienne');
INSERT INTO LIVRE (auteur, titre) VALUES ('Burton Jessie','Les filles au lion');
INSERT INTO LIVRE (auteur, titre) VALUES ('Vales José C.','Cabaret Biarritz');
INSERT INTO LIVRE (auteur, titre) VALUES ('Hope Anna','La salle de bal');
INSERT INTO LIVRE (auteur, titre) VALUES ('Schirach Ferdinand von','L''affaire Collini');
INSERT INTO LIVRE (auteur, titre) VALUES ('Bouchard Nicolas','Tarpeia les venins de Rome');
INSERT INTO LIVRE (auteur, titre) VALUES ('Boyd William','Les Vies multiples d''Amory Clay');
INSERT INTO LIVRE (auteur, titre) VALUES ('Barbato Paola','A mains nues');
INSERT INTO LIVRE (auteur, titre) VALUES ('Reverdy Thomas B.','Il était une ville');

INSERT INTO EXEMPLAIRE(id_liv) VALUES (1);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (1);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (2);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (2);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (3);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (3);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (3);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (3);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (3);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (4);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (5);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (6);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (6);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (7);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (8);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (9);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (10);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (11);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (12);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (13);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (14);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (15);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (16);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (16);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (17);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (18);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (19);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (20);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (21);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (22);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (23);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (24);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (25);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (26);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (27);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (28);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (29);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (30);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (31);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (32);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (33);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (34);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (35);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (36);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (37);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (38);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (39);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (40);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (41);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (42);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (43);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (44);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (45);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (46);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (47);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (48);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (49);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (50);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (51);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (52);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (53);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (54);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (55);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (56);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (57);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (58);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (59);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (60);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (61);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (62);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (63);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (64);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (65);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (66);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (67);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (68);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (69);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (70);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (71);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (72);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (73);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (74);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (75);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (76);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (77);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (78);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (79);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (80);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (81);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (82);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (83);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (84);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (85);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (86);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (87);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (88);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (89);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (90);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (91);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (92);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (93);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (94);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (95);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (96);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (97);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (98);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (99);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (100);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (101);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (102);
INSERT INTO EXEMPLAIRE(id_liv) VALUES (103);

/*DECLARE
    id LIVRE.ID_LIV%TYPE;
BEGIN
    FOR id IN (SELECT ID_LIV FROM LIVRE) loop
            FOR i in 0..(floor(dbms_random.value(1, 10))) loop
                    INSERT INTO EXEMPLAIRE (ID_LIV) VALUES (id.ID_LIV);
                end loop ;
        end loop ;
end ;
/*/

insert into EMPRUNT (date_emp, date_retour, id_ex, id_et) VALUES ('06-05-2021','13-05-2021','1','2');
insert into EMPRUNT (date_emp, date_retour, id_ex, id_et) VALUES ('06-05-2021','13-05-2021','3','2');
insert into EMPRUNT (date_emp, date_retour, id_ex, id_et) VALUES ('06-05-2021','13-05-2021','5','2');
insert into EMPRUNT (date_emp, date_retour, id_ex, id_et) VALUES ('31-05-2021','15-06-2021','2','1');
insert into EMPRUNT (date_emp, date_retour, id_ex, id_et) VALUES ('31-05-2021','15-06-2021','6','1');

select * from ETUDIANT;
select * from LIVRE;
select * from EXEMPLAIRE;
select * from EMPRUNT;
select * from RESERVATION;

SELECT ID_EX FROM EMPRUNT WHERE ID_ET = 2;
SELECT ID_LIV FROM EXEMPLAIRE WHERE ID_EX in (SELECT ID_EX FROM EMPRUNT WHERE ID_ET = 2);

SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET and UPPER(NOM) like UPPER('%%') and UPPER(PRENOM) like UPPER('%%') and UPPER(AUTEUR) like UPPER('%%') and UPPER(TITRE) like UPPER('%tI%');

DELETE EMPRUNT WHERE ID_EX = 8;