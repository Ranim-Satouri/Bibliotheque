package DTO;

public class RapportStatDTO {
    private int nbEmp;
    private String chaine1;
    private String chaine2;

    public RapportStatDTO(int nbEmp, String chaine1, String chaine2) {
        this.nbEmp = nbEmp;
        this.chaine1 = chaine1;
        this.chaine2 = chaine2;
    }
    public int getNbEmp() {
        return nbEmp;
    }
    public String getChaine1() {
        return chaine1;
    }
    public String getChaine2() {
        return chaine2;
    }
}
