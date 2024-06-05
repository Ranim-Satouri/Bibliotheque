CREATE DATABASE Biblio;
USE Biblio;

CREATE TABLE Utilisateur (
    Id_Utilisateur INT AUTO_INCREMENT,
    Nom VARCHAR(50) NOT NULL,
    Prenom VARCHAR(50) NOT NULL,
    Login VARCHAR(50) NOT NULL UNIQUE,
    Pwd VARCHAR(50) NOT NULL,
    Role VARCHAR(50) NOT NULL,
    CHECK (Role IN ('Etudiant', 'Enseignant', 'biblio')),
    PRIMARY KEY(Id_Utilisateur)
);
CREATE TABLE Livre (
    Id_Livre INT AUTO_INCREMENT,
    Titre VARCHAR(50) NOT NULL,
    Auteur VARCHAR(50) NOT NULL,
    Genre VARCHAR(50) NOT NULL,
    Dispo VARCHAR(50) NOT NULL,
    CHECK (Dispo IN ('Disponible', 'Indisponible')),
    PRIMARY KEY(Id_Livre)
);

CREATE TABLE Emprunt (
    Id_Emprunt INT AUTO_INCREMENT,
    Id_Utilisateur INT not null,
    Id_Livre INT not null,
    Date_Emprunt DATE NOT NULL,
    Date_Retour DATE NOT NULL,
    CHECK (Date_Emprunt <= Date_Retour),
    Statut VARCHAR(50) CHECK (Statut IN ('Terminé', 'En_Cours')) not null,
    PRIMARY KEY(Id_Emprunt,Id_Livre,Id_Utilisateur),
    CONSTRAINT FK_User_Emp FOREIGN KEY(Id_Utilisateur) REFERENCES Utilisateur(Id_Utilisateur) on delete cascade
        ON UPDATE CASCADE,
    CONSTRAINT FK_Book_Emp FOREIGN KEY(Id_Livre) REFERENCES Livre(Id_Livre) on delete cascade
        ON UPDATE CASCADE
);

CREATE TABLE Reservation (
    Id_Reservation INT AUTO_INCREMENT,
    Id_Utilisateur INT not null,
    Id_Livre INT not null,
    Date_Reservation DATETIME NOT NULL,
    Statut VARCHAR(50) CHECK (Statut IN ('Terminé', 'En_Cours'))not null,
    PRIMARY KEY(Id_Reservation,Id_Utilisateur,Id_Livre),
    CONSTRAINT FK_User_Res FOREIGN KEY(Id_Utilisateur) REFERENCES Utilisateur(Id_Utilisateur) on delete cascade
        ON UPDATE CASCADE,
    CONSTRAINT FK_Book_Res FOREIGN KEY(Id_Livre) REFERENCES Livre(Id_Livre) on delete cascade
        ON UPDATE CASCADE
);
use biblio;
insert into utilisateur(nom,prenom,login,pwd,role) values('biblio','biblio','biblio@gmail.com','biblio123',"biblio");
insert into utilisateur(nom,prenom,login,pwd,role) values('ranim','ranim','ranim@gmail.com','ranim123',"Etudiant");
insert into utilisateur(nom,prenom,login,pwd,role) values('rihem','rihem','rihem@gmail.com','rihem123',"Etudiant");
insert into utilisateur(nom,prenom,login,pwd,role) values('amal','amal','amal@gmail.com','amal123',"Enseignant");
insert into utilisateur(nom,prenom,login,pwd,role) values('ALi','Ali','Ali@gmail.com','ali123',"Etudiant");
insert into utilisateur(nom,prenom,login,pwd,role) values('rayen','rayen','rayen@gmail.com','rayen123',"Etudiant");
insert into utilisateur(nom,prenom,login,pwd,role) values('aziz','aziz','aziz@gmail.com','aziz123',"Etudiant");
insert into utilisateur(nom,prenom,login,pwd,role) values('achraf','achraf','achraf@gmail.com','achraf123',"Etudiant");
insert into utilisateur(nom,prenom,login,pwd,role) values('imen','imen','imen@gmail.com','imen123',"Etudiant");
insert into utilisateur(nom,prenom,login,pwd,role) values('karim','karim','karim@gmail.com','karim123',"Etudiant");


INSERT INTO livre (titre, auteur, genre, dispo)
VALUES 
  ("L'Alchimiste", "Paulo Coelho", "Fiction", "Disponible"),
  ("Les Fleurs du Mal", "Charles Baudelaire", "Poésie", "Disponible"),
  ("L'Écume des jours", "Boris Vian", "Roman", "Disponible"),
  ("La Nausée", "Jean-Paul Sartre", "Philosophique", "Disponible"),
  ("Cent ans de solitude", "Gabriel García Márquez", "Magique réalisme", "Disponible"),
  ("Le Maître et Marguerite", "Mikhaïl Boulgakov", "Roman fantastique", "Disponible"),
  ("Fahrenheit 451", "Ray Bradbury", "Science-fiction", "Disponible"),
  ("Le Guépard", "Giuseppe Tomasi di Lampedusa", "Roman historique", "Disponible"),
  ("Moby Dick", "Herman Melville", "Roman maritime", "Disponible"),
  ("Les Liaisons Dangereuses", "Pierre Choderlos de Laclos", "Épistolaire", "Disponible"),
  ("Le Comte de Monte-Cristo", "Alexandre Dumas", "Aventure", "Disponible"),
  ("Le Rouge et le Noir", "Stendhal", "Roman psychologique", "Disponible"),
  ("Voyage au bout de la nuit", "Louis-Ferdinand Céline", "Roman satirique", "Disponible"),
  ("La Recherche du Temps Perdu", "Marcel Proust", "Roman psychologique", "Disponible"),
  ("Les Fourmis", "Bernard Werber", "Science-fiction", "Disponible"),
  ("Autant en emporte le vent", "Margaret Mitchell", "Roman historique", "Disponible"),
  ("La Horde du Contrevent", "Alain Damasio", "Science-fiction", "Disponible"),
  ("Les Particules élémentaires", "Michel Houellebecq", "Roman", "Disponible"),
  ("Le Château", "Franz Kafka", "Roman", "Disponible"),
  ("Les Trois Mousquetaires", "Alexandre Dumas", "Aventure", "Disponible");


/* livre lekehr bech ntasti bih el RechercherLivre*/

select * from emprunt;
select * from utilisateur;
select * from reservation ;
select * from livre;

/* bech ntasti beha el rappel */
insert into emprunt(id_utilisateur,id_livre,date_emprunt,date_retour,statut) values(4,18,current_date(),'2023-12-12','En_Cours');
update livre set dispo="Indisponible" where id_livre=18;
