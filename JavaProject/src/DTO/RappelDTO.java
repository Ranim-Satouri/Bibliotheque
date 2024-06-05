package DTO;

public class RappelDTO {
    private String nom;
    private String prenom;
    private String titre;
    private String auteur;
    private String date_emprunt;
    private String date_retour;

    public RappelDTO(String nom, String prenom, String titre, String auteur, String date_emprunt, String date_retour) {
        this.nom = nom;
        this.prenom = prenom;
        this.titre = titre;
        this.auteur = auteur;
        this.date_emprunt = date_emprunt;
        this.date_retour = date_retour;
    }
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getDate_emprunt() {
        return date_emprunt;
    }

    public String getDate_retour() {
        return date_retour;
    }
}
