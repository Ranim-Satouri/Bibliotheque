package Class;

import Exceptions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation {
	private int id_res;
	private int user_id;
	private int book_id;
	private String date_res;
	private String statut;
	
	public Reservation(int user_id,int book_id){
		this.user_id=user_id;
		this.book_id=book_id;
	}
	public Reservation(int id_res, int user_id, int book_id, String date_res, String statut) {
        this.id_res = id_res;
        this.user_id = user_id;
        this.book_id = book_id;
        this.date_res = date_res;
        this.statut = statut;
    }
	
	public int GetResId()
    {
        return id_res;
    }
    public void SetResId(int idRes)
    {
        this.id_res = idRes;
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
    public String GetDateRes()
    {
        return date_res;
    }
    public void SetDateRes(String dateRes)
    {
        this.date_res = dateRes;
    }
    public String GetStatut()
    {
        return statut;
    }
    public void SetStatut(String statut)
    {
        this.statut = statut;
    }
	//methode de reservation
	public void reserverLivre() throws MyException {
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
			String sql="insert into reservation(id_utilisateur,id_livre,date_reservation,statut) values(?,?,NOW(),'En_Cours')";
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, user_id);
	        ps.setInt(2, book_id);
	        ps.executeUpdate();
	        ps.close();
            conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new MyException();
		}
    }
	//pour verifier si le livre est reservé avant de rendre son statut disponible 
	public static Reservation estReserver(int book_id) throws MyException{
		Reservation res=null;
    	try {
    		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
    		String sql="Select id_reservation,id_utilisateur,id_livre,date_reservation,statut from reservation where id_livre=? and statut='En_Cours' ORDER BY date_reservation ASC LIMIT 1";
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, book_id);
	        ResultSet rs=ps.executeQuery();
	        
	        if(rs.next()) {
	        	int res_id=rs.getInt("id_reservation");
	        	int user_id=rs.getInt("id_utilisateur");
		        String date_reservation=rs.getString("date_reservation");
		        res=new Reservation(res_id,user_id,book_id,date_reservation,"En_Cours");
	        }
	        ps.close();
            rs.close();
            conn.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
			throw new MyException();
    	}
    	return res;
	}
	
	//pour l'affichage des button dans linterface
	public static boolean reserverPar(int user_id ,int book_id) throws MyException {
		boolean resPar=false;
		try {
    		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
    		String sql="Select * from reservation where id_utilisateur=? and id_livre=? and statut='En_Cours' ";
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, user_id);
	        ps.setInt(2, book_id);
	        ResultSet rs=ps.executeQuery();	        
	        if(rs.next()) {
	        	resPar=true;;
	        }
	        ps.close();
            rs.close();
            conn.close();	        
    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw new MyException();
    	}
		return resPar;
	}
	public void annulerReservation() throws MyException {
		try {
    		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","112713");
    		String sql="delete from reservation where id_utilisateur=? and id_livre=? and statut='En_Cours'";
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, user_id);
	        ps.setInt(2, book_id);
	        ps.executeUpdate();
	        ps.close();
            conn.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw new MyException();
    	}	
	}

}
