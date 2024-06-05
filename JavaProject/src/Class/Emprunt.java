package Class;

import Exceptions.*;
import DTO.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Emprunt {
	private int emp_id;
	private int user_id;
	private int book_id;
	private String date_emp;
	private String date_ret;
	private String statut;
	
	public Emprunt(int user_id,int book_id) {
		this.user_id=user_id;
		this.book_id=book_id;
	}
	public Emprunt(int emp_id,int user_id,int book_id) {
		this.emp_id=emp_id;
		this.book_id=book_id;
		this.user_id=user_id;
	}
	public Emprunt(int emp_id, int user_id, int book_id, String date_emp, String date_ret, String statut) {
        this.emp_id = emp_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.date_emp = date_emp;
        this.date_ret = date_ret;
        this.statut = statut;
    }
	
	public int GetEmpId()
    {
        return emp_id;
    }
    public void SetEmpId(int idEmp)
    {
        this.emp_id = idEmp;
    }
    public int GetUserId()
    {
        return user_id;
    }
    public void SetUserId(int userId)
    {
        this.user_id = userId;
    }
    public int GetBookId()
    {
        return book_id;
    }
    public void SetBookId(int bookId)
    {
        this.book_id = bookId;
    }
    public String GetDateEmp()
    {
        return date_emp;
    }
    public void SetDateEmp(String dateEmp)
    {
        this.date_emp = dateEmp;
    }
    public String GetDateRet()
    {
        return date_ret;
    }
    public void SetDateRet(String dateRet)
    {
        this.date_ret = dateRet;
    }
    public String GetStatut()
    {
        return statut;
    }
    public void SetStatut(String statut)
    {
        this.statut = statut;
    }
	//Methode pour faire l'emprunt
	public void emprunterLivre() throws MyException {
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
			String sql="insert into emprunt(id_utilisateur,id_livre,date_emprunt,date_retour,statut)"+
            " values(?,?,current_date(),DATE_ADD(current_date(), INTERVAL 15 DAY),'En_Cours')";
			String sql2="update livre set dispo='Indisponible' where id_livre=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			PreparedStatement ps2 = conn.prepareStatement(sql2);
	        ps.setInt(1, user_id);
	        ps.setInt(2, book_id);
	        ps2.setInt(1,book_id);
	    	ps.executeUpdate();
	    	ps2.executeUpdate();
	    	ps.close();
	    	ps2.close();
            conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new MyException();
		}
    }
	//pour l'affichage des button dans linterface
	public static boolean emprunterPar(int user_id ,int book_id) throws MyException{
		boolean empPar=false;
		try {
    		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
    		String sql="Select * from emprunt where id_utilisateur=? and id_livre=? and statut='En_Cours' ";
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, user_id);
	        ps.setInt(2, book_id);
	        ResultSet rs=ps.executeQuery();
	        if(rs.next()) {
	        	empPar=true;
	        }
	        ps.close();
            rs.close();
            conn.close();
	        
    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw new MyException();
    	}
		return empPar;
	}
	
	//Methode de retour d'un livre apres emprunt
	public void retournerLivre() throws MyException {
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
			String sql="update emprunt set date_retour=current_date() , statut='Terminé'  where id_emprunt=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, emp_id);
			ps.executeUpdate();
			Reservation res=Reservation.estReserver(book_id);
			if(res==null) {
				String sql2="update livre set dispo='Disponible' where id_livre=?";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
		        ps2.setInt(1,book_id);
		    	ps2.executeUpdate();
		    	ps2.close();
			}else {
				String sql3="insert into emprunt(id_utilisateur,id_livre,date_emprunt,date_retour,statut)"+
                "values(?,?,current_date(),DATE_ADD(current_date(), INTERVAL 15 DAY),'En_Cours')";
				PreparedStatement ps3 = conn.prepareStatement(sql3);
				ps3.setInt(1,res.GetUserId());
		        ps3.setInt(2,book_id);
		    	ps3.executeUpdate();
		    	String sql2="update reservation set statut='Terminé' where id_reservation=?";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
		        ps2.setInt(1,res.GetResId());
		    	ps2.executeUpdate();
		    	ps3.close();
		    	ps2.close();
			}			
			ps.close();
            conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new MyException();
		}	
	}

	public static List<HistoriqueEmpDTO> getListeHistEmp(int user_id) throws MyException {
        List<HistoriqueEmpDTO> liste = new ArrayList<>();
        try {
        	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio", "root", "112713");
            String sql = "SELECT id_emprunt,titre,auteur,date_emprunt,date_retour,statut FROM emprunt e join livre l"+
            " where e.id_livre=l.id_livre and id_utilisateur=?";
            PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, user_id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id_emprunt = resultSet.getInt("id_emprunt");
                String titre=resultSet.getString("titre");
                String auteur=resultSet.getString("auteur");
                String date_emprunt = resultSet.getString("Date_emprunt");
                String date_retour = resultSet.getString("Date_retour");
                String statut=resultSet.getString("statut");
                HistoriqueEmpDTO object=new HistoriqueEmpDTO(id_emprunt,titre,auteur,date_emprunt,date_retour,statut);
                liste.add(object);
            }
            ps.close();
            resultSet.close();
            conn.close();
        }catch(SQLException e) {
        	e.printStackTrace();
			throw new MyException();
        }
        return liste;
    }	

	public static List<RapportStatDTO> getRapportUtilisateur() throws MyException{
        List<RapportStatDTO> liste = new ArrayList<>();
		try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ biblio", "root", "112713");
            String sql = "SELECT count(e.Id_Utilisateur) as nb,Nom,Prenom FROM Emprunt e,Utilisateur u where e. Id_Utilisateur=u. Id_Utilisateur  group by e.Id_Utilisateur order by nb  DESC";
            PreparedStatement ps = conn .prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
            	int nb=resultSet.getInt("nb");
                String nom = resultSet.getString("Nom");
                String prenom = resultSet.getString("Prenom");
                RapportStatDTO object=new RapportStatDTO(nb,nom,prenom);
                liste.add(object);              
            }
            resultSet.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MyException();
        }
        return liste;		
	}

	public static List<RapportStatDTO> getRapportLivre()throws MyException{
        List<RapportStatDTO> liste = new ArrayList<>();
		try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ biblio", "root", "112713");
            String sql = "SELECT count(e.Id_Livre) as nb,Titre,Auteur FROM Emprunt e,Livre l where e. Id_Livre=l.Id_Livre group by e. Id_Livre order by nb DESC   ";
            PreparedStatement ps = conn .prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
            	int nb=resultSet.getInt("nb");
                String titre = resultSet.getString("Titre");
                String auteur = resultSet.getString("Auteur");
                RapportStatDTO object=new RapportStatDTO(nb,titre,auteur);
                liste.add(object);  
            }
            resultSet.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return liste;
	}
	public static List<RappelDTO> getListeRappelUtilisateurs() throws MyException{
        List<RappelDTO> liste = new ArrayList<>();
		try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio", "root", "112713");
            String sql = "SELECT e.Id_Utilisateur ,e.Id_Livre,Nom,Prenom,Titre,auteur ,Date_Emprunt,Date_Retour FROM Emprunt e,Utilisateur u,  Livre l where ( e. Id_Utilisateur=u. Id_Utilisateur and e.Id_Livre=l.Id_Livre  and Date_Retour-curdate()=1  and Date_Retour-curdate()=1 and statut='En_Cours')";
            PreparedStatement ps = conn .prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {            	
                String nom = resultSet.getString("Nom");
                String prenom = resultSet.getString("Prenom");
                String titre = resultSet.getString("Titre");
                String auteur = resultSet.getString("auteur");
                String date_emprunt = resultSet.getString("Date_Emprunt");
                String date_retour = resultSet.getString("Date_Retour");
                RappelDTO object=new RappelDTO(nom,prenom,titre,auteur,date_emprunt,date_retour);
                liste.add(object);
            }
            resultSet.close();
            ps.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MyException();
        }
		return liste;
	}
	
}
	
