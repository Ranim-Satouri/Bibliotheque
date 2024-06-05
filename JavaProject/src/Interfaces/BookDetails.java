package Interfaces;

import Class.*;
import Exceptions.MyException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookDetails {
	public BookDetails(int user_id,Livre livre) {
		JFrame frame = new JFrame("Afficher les Details d'un Livre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.getContentPane().setBackground(Color.PINK); 

        JLabel label1 = new JLabel("Titre:");
        JLabel label2 = new JLabel("Auteur:");
        JLabel label3 = new JLabel("Genre:");
        JLabel label4 = new JLabel("Disponibilité:");

        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        JTextField textField3 = new JTextField();
        JTextField textField4 = new JTextField();
        
        frame.setLayout(new GridLayout(6,4));
        
        frame.add(label1);
        frame.add(textField1);
        frame.add(label2);
        frame.add(textField2);
        frame.add(label3);
        frame.add(textField3);
        frame.add(label4);
        frame.add(textField4);
        JButton emprunt= new JButton("Emprunter");
        JButton reserver = new JButton("Reserver");
        JButton retour=new JButton("Catalogue");
        JButton annuler=new JButton("Annuler Reservation");
        
        textField1.setEditable(false);
	    textField2.setEditable(false);
	    textField3.setEditable(false);
	    textField4.setEditable(false);
	  
	    textField1.setText(livre.GetTitre());
	    textField2.setText(livre.GetAuteur());
	    textField3.setText(livre.GetGenre());
	    textField4.setText(livre.GetDispo());        
             
		if(livre.GetDispo().equals("Disponible")) {
    		frame.add(new JLabel());
    		frame.add(new JLabel());
            frame.add(retour);
			frame.add(emprunt);		
        }else {
        	try {
        		if(Emprunt.emprunterPar(user_id,livre.GetId())) {
            		frame.add(new JLabel());
            		frame.add(new JLabel());
                    frame.add(retour);
            		frame.add(new JLabel("Vous êtes l'emprunteur de ce livre."));
            	}else if(Reservation.reserverPar(user_id,livre.GetId())) {
            		frame.add(new JLabel());
            		frame.add(new JLabel(" Votre demande de reservation est en cours"));
                    frame.add(retour);
            		frame.add(annuler);
            	}else {
            		frame.add(new JLabel());
            		frame.add(new JLabel());
                    frame.add(retour);
            		frame.add(reserver);
            	}	
        	}catch (MyException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
        	}        	       		                   	
        }
		emprunt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Emprunt emp=new Emprunt(user_id,livre.GetId());
                int choix = JOptionPane.showConfirmDialog(frame, "Êtes-vous sûr de vouloir emprunter ce livre ?", "Demande de Confirmation d'emprunt de livre", JOptionPane.YES_NO_OPTION);
                if (choix == JOptionPane.YES_OPTION) { 
	                try {
	                	emp.emprunterLivre();
	                    JOptionPane.showMessageDialog(frame, "Emprunt réussi !", "Confirmation d'emprunt de livre", JOptionPane.INFORMATION_MESSAGE);
	                    frame.dispose();
	                    livre.SetDispo("Indisponible");//khater kif livre.dispo=disponible wa9t naawd nabaath el livre yab9a dispo
	                    new BookDetails(user_id,livre);
	                }catch (MyException ex){
	                    JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
	                }  
                }    
            }
        });
		reserver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int confirmation = JOptionPane.showConfirmDialog(frame, "Voulez-vous réserver ce livre ?", "Confirmation de réservation de livre", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
	                Reservation res=new Reservation(user_id,livre.GetId());
	                
	                try {
	                	 res.reserverLivre();
	                     JOptionPane.showMessageDialog(frame, "Réservation réussie !", "Succès de la réservation", JOptionPane.INFORMATION_MESSAGE);
	                     frame.dispose();
	                     new BookDetails(user_id,livre);
	                	
	                }catch (MyException ex) {
	                    JOptionPane.showMessageDialog(frame, ex.getMessage());
	                }    
                }    
            }
        });
		annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(frame, "Voulez-vous annuler cette réservation ?", "Confirmation d'annulation de réservation", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
	                Reservation res=new Reservation(user_id,livre.GetId());
	                try {
	                	 res.annulerReservation();
	                        JOptionPane.showMessageDialog(frame, "Annulation de réservation réussie !", "Succès de l'annulation", JOptionPane.INFORMATION_MESSAGE);
	                     frame.dispose();
	                     new BookDetails(user_id,livre);
	                	
	                }catch (MyException ex) {
	                    JOptionPane.showMessageDialog(frame, ex.getMessage());
	                }   
                }    
            }
        });
		retour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ConsulterCatalogue(user_id);				
			}
		});     
		
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);    
	}
    
}
