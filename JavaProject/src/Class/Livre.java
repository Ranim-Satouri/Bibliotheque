package Class;

import Exceptions.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Livre {
	private int id;
	private String Titre;
	private String Auteur;
	private String Genre;
	private String dispo;
	public Livre(String titre,String auteur) {
		this.Titre=titre;
		this.Auteur=auteur;	
	}
	public Livre(int id,String Titre,String Auteur,String Genre,String dispo) {
		this.id=id;
		this.Titre=Titre;
		this.Auteur=Auteur;
		this.Genre=Genre;
		this.dispo=dispo;
	}
	public Livre(String Titre,String Auteur,String Genre) {
		this.Titre=Titre;
		this.Auteur=Auteur;
		this.Genre=Genre;
	}
	public int GetId()
    {
        return id;
    }
	public void SetId(int Id)
	{
	    this.id =Id;
	}
	public String GetTitre()
	{
	    return Titre;
	}
    public void SetTitre(String titre)
    {
        this.Titre = titre;
    }
    public String GetAuteur()
    {
        return  Auteur;
    }
    public void SetAuteur(String auteur)
    {
        this.Auteur = auteur;
    }

    public String GetGenre()
    {
        return Genre;
    }

    public void SetGenre(String genre)
    {
        this.Genre = genre;
    }

    public String GetDispo()
    {
        return dispo;
    }

    public void SetDispo(String dispo)
    {
        this.dispo = dispo;
    }
	//Mehtode chercher un livre pour recuperer ses details apartir de son titre et auteur

	public static Livre RechercherLivre(String title,String auteur) throws MyException {
		Livre livre=null;
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
			String sql="Select * from livre where titre=? and auteur=?";
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, title);
	        ps.setString(2, auteur);
	    	ResultSet resultSet = ps.executeQuery();
	    	if(resultSet.next()) {
	    		String Genre=resultSet.getString("genre");
		    	int id=resultSet.getInt("id_livre");
		    	String dispo=resultSet.getString("dispo");
		    	livre=new Livre(id,title,auteur,Genre,dispo);	 
	    	}
	    	ps.close();
	    	resultSet.close();
	    	conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new MyException();
	    }
    	return livre;
	}
    public static List<Livre> getListeLivres() throws MyException {
        List<Livre> listeLivres = new ArrayList<>();

        try {
        	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio", "root", "112713");
            String sql = "SELECT * FROM livre";
            Statement ps = conn.createStatement();
            ResultSet resultSet = ps.executeQuery(sql);

            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                Livre livre = new Livre(titre, auteur);
                listeLivres.add(livre);
            }
            ps.close();
            resultSet.close();
            conn.close();
        }catch(SQLException e) {
        	e.printStackTrace();
			throw new MyException();
        }
        return listeLivres;
    }		    
    public void  supprimerLivre() throws MyException{
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ biblio", "root", "112713");
			String sql="DELETE FROM livre WHERE Id_Livre= ?";
			PreparedStatement ps = conn.prepareStatement(sql); 
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new MyException();
	    }
	}
	public void AjouterLivre() throws MyException{
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ Biblio", "root", "112713");
			String sql="Insert INTO Livre (Titre, Auteur,Genre,dispo) values(?,?,?,'Disponible')";
			PreparedStatement ps = conn.prepareStatement(sql); 
			ps.setString(1, Titre);
			ps.setString(2,  Auteur);
			ps.setString(3, Genre);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
		    throw new MyException();
	    }				
	}		    
}

