package Interfaces;

import Class.Emprunt;
import DTO.RappelDTO;
import Exceptions.MyException;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Rappelle {
	public  Rappelle(){
		JFrame frame = new JFrame("rappelle");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(800, 400);
	    DefaultTableModel model = new DefaultTableModel() {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };        
	    model.addColumn("Nom");
	    model.addColumn("Prenom");
	    model.addColumn("Titre");
	    model.addColumn("Auteur");
	    model.addColumn("date_enprunte");
	    model.addColumn("date_retour");
	
	    try {
	        List<RappelDTO> listeRappelDTO = Emprunt.getListeRappelUtilisateurs();
	
	        for (RappelDTO object : listeRappelDTO) {
	            model.addRow(new Object[]{object.getNom(),object.getPrenom(),object.getTitre(),object.getAuteur(),object.getDate_emprunt(),object.getDate_retour()});
	        }
	    } catch (MyException ex) {
	        JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
	    }
	    
	    JTable table = new JTable(model);
	    JScrollPane scrollPane = new JScrollPane(table);
	    
	    JButton envoyer = new JButton("envoyer un  mail ");
	    envoyer.addActionListener(new ActionListener() {
	    	@Override
	        public void actionPerformed(ActionEvent e) {

	    		int nombreDeLignes = model.getRowCount();
	    		if(nombreDeLignes!=0) {
		    		JOptionPane.showMessageDialog(frame, nombreDeLignes+" E-mails de rappel envoyés avec succès.", "Rappel par Mail", JOptionPane.INFORMATION_MESSAGE);
	    		}
  	        }	        	
	   });
	        
	    JButton retour = new JButton("Retour a L'Accueil");
	    
	    frame.setLayout(new GridLayout(3,1));
	    retour.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose();
	            new BiblioAccueil();	
	        }
	    });
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(retour);
	    buttonPanel.add(envoyer);
	
	    frame.getContentPane().setLayout(new BorderLayout());
	    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	    frame.setSize(800, 400);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
}
