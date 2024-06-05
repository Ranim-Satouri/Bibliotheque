package Class;

import Exceptions.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
	private int id;
	private String nom;
	private String prenom;
	private String login;
	private String pwd;
	private String role;
	//constructeur pour l'inscription
	public Utilisateur(String nom, String prenom, String login, String pwd, String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.pwd = pwd;
        this.role = role;
    }
	//constructeur pour log_in
	public Utilisateur(int id,String nom, String prenom, String login, String pwd, String role) {
		this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.pwd = pwd;
        this.role = role;
    }
	//constructeur pour liste des utilisateurs
	public Utilisateur(int id,String nom, String prenom,String role) {
		this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }	
	public int GetId()
    {
        return id;
    }
    public void SetId(int id_utilisateur)
    {
        this.id = id_utilisateur;
    }
    public String GetNom()
    {
        return nom;
    }
    public void SetNom(String nom)
    {
        this.nom = nom;
    }
    public String GetPrenom()
    {
        return prenom;
    }
    public void SetPrenom(String prenom)
    {
        this.prenom = prenom;
    }
    public String GetLogin()
    {
        return login;
    }
    public void SetLogin(String login)
    {
        this.login = login;
    }
    public String GetPwd()
    {
        return pwd;
    }
    public void SetPwd(String pwd)
    {
        this.pwd = pwd;
    }
    public String GetRole()
    {
        return role;
    }
    public void SetRole(String role)
    {
        this.role = role;
    }
	//Methode pour verifier si un utilisateur existe ou non(mot de passe et login valides)

	public static Utilisateur authentifier(String mail,String pwd) throws MyException {
		Utilisateur user=null;
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
			String sql="Select * from utilisateur where login=? and pwd=? ";
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, mail);
	        ps.setString(2, pwd);
        	ResultSet resultSet = ps.executeQuery();
        	if(resultSet.next()) {
            	int id=resultSet.getInt("id_utilisateur");
            	String nom=resultSet.getString("nom");
            	String prenom=resultSet.getString("prenom");
        		String role=resultSet.getString("role");
            	user=new Utilisateur(id,nom,prenom,mail,pwd,role);
        	}
        	ps.close();
        	resultSet.close();
        	conn.close();        		
		}catch (SQLException e){
			e.printStackTrace();
			throw new MyException();
	    }
		return user;		
    }
	 //l'inscription 
	 
	public void inscrire() throws MailException , MyException ,PwdException {	
		try {
			Utilisateur.verifierEmail(login);
            Utilisateur.verifierMotDePasse(pwd);
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
            String sql="SELECT LOGIN FROM UTILISATEUR WHERE LOGIN=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, login);
			ResultSet rs=ps.executeQuery();	                      
			if(rs.next()) {
				throw new MailException("EMail Exist Deja");
			}else {
				String sql2="INSERT INTO UTILISATEUR(nom,prenom,login,pwd,role)  values(?,?,?,?,?)";
				PreparedStatement ps2=conn.prepareStatement(sql2);
				ps2.setString(1, nom);
		        ps2.setString(2, prenom);
		        ps2.setString(3, login);
		        ps2.setString(4, pwd);
		        ps2.setString(5, role);
		    	ps2.executeUpdate();	
	        	ps2.close();
			}
			ps.close();
        	rs.close();
            conn.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new MyException();			
		}
	}
	private static void verifierEmail(String email) throws MailException  {
        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");
        if(!(atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1)) {
        	throw new MailException("Email Invalid !");
        }
    }
	public static void verifierMotDePasse(String motDePasse) throws PwdException {
		if (motDePasse.length() < 8) {
			throw new PwdException("Le mot de passe doit avoir une longueur d'au moins 8 caractères.");
		}
		boolean Lettres = false;
		boolean Chiffres = false;
		for (char caractere : motDePasse.toCharArray()) {
			if (Character.isLetter(caractere)) {
				Lettres = true;
			}else if (Character.isDigit(caractere)) {
				Chiffres = true;
			}	            
		}
		if (!(Lettres && Chiffres)) {
			throw new PwdException("Le mot de passe doit contenir à la fois des lettres et des chiffres.");
		}
	}	
	public void  supprimerUtilisateur() throws MyException {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ biblio", "root", "112713");		 
			String sql = "DELETE FROM Utilisateur WHERE Id_Utilisateur = ?";
			PreparedStatement ps = conn.prepareStatement(sql); 
			ps.setInt(1, id);
			ps.executeUpdate();             
			ps.close();
			conn.close();
		}catch (SQLException e1) {
		e1.printStackTrace();
		throw new MyException();
		}			
	}
	public static List<Utilisateur> getListeUtilisateurs() throws MyException {
		List<Utilisateur> listeUser = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ biblio", "root", "112713");
			String sql = "SELECT * FROM utilisateur WHERE role= ? or role =?";
			PreparedStatement ps = conn .prepareStatement(sql);
			ps.setString(1,"etudiant");
			ps.setString(2,"enseignant");
			
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				int id=resultSet.getInt("id_Utilisateur");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String role=resultSet.getString("role");
				Utilisateur user=new Utilisateur(id,nom,prenom,role);
				listeUser.add(user);
			}
			ps.close();
			conn.close();
			resultSet.close();
		}catch(SQLException e) {
			e.printStackTrace();
			throw new MyException();
		}
		return listeUser;
	}	
		
}		

