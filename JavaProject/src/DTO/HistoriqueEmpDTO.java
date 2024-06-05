package DTO;

public class HistoriqueEmpDTO {
    private int id_emprunt;
    private String titre;
    private String auteur;
    private String date_emprunt;
    private String date_retour;
    private String statut;

    public HistoriqueEmpDTO(int id_emprunt, String titre, String auteur, String date_emprunt, String date_retour, String statut) {
        this.id_emprunt = id_emprunt;
        this.titre = titre;
        this.auteur = auteur;
        this.date_emprunt = date_emprunt;
        this.date_retour = date_retour;
        this.statut = statut;
    }
    public int getId_emprunt() {
        return id_emprunt;
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

    public String getStatut() {
        return statut;
    }
}
