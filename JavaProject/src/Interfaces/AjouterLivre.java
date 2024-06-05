package Interfaces;

import Class.*;
import Exceptions.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AjouterLivre {
    public AjouterLivre(){
        JFrame frame = new JFrame("Ajouter un livre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.PINK); 

        JLabel titre = new JLabel("Titre");
        JLabel auteur = new JLabel("Auteur");
        JLabel genre = new JLabel("Genre");

        JTextField titrefield = new JTextField(5);
        JTextField auteurfield = new JTextField(5);
        JTextField genrefield = new JTextField(5);

        frame.setLayout(new GridLayout(8, 2));
   
        JButton ajouter = new JButton("Ajouter ce livre ");
        ajouter.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		String titre=titrefield.getText().trim();
        		String auteur=auteurfield.getText().trim();
        		String genre=genrefield.getText().trim();
        		if(!(titre.isEmpty() | auteur.isEmpty() | genre.isEmpty())) {
        			try {
	        			if(Livre.RechercherLivre(titre,auteur)!=null) {
	        				JOptionPane.showMessageDialog(frame, "Le livre est déjà présent dans la bibliothèque.", "Livre Existant", JOptionPane.INFORMATION_MESSAGE);
		                } else {
		                	Livre livre=new Livre(titre,auteur,genre);
		                	livre.AjouterLivre();
		                	JOptionPane.showMessageDialog(frame, "Livre a été ajouté avec succès.", "Ajout Réussi", JOptionPane.INFORMATION_MESSAGE);
                            titrefield.setText("");
                            auteurfield.setText("");
                            genrefield.setText("");
		                }	        			
	        		}catch (MyException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur d'ajout",JOptionPane.ERROR_MESSAGE);
	        		}        			
        		}else {
                	JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.", "Champs Incomplets", JOptionPane.WARNING_MESSAGE);
                }        	            
        	}
	        	
	    });
	       	        
		JButton retour = new JButton("Retour a L'Accueil");
	       retour.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent e) {
	               frame.dispose();
	               new BiblioAccueil();	
	           }
	       });
	    frame.add(new JLabel());
	    frame.add(new JLabel());
	    frame.add(titre);
        frame.add(titrefield);
        frame.add(new JLabel());
        frame.add(new JLabel());
        frame.add(auteur);
        frame.add(auteurfield);
        
        frame.add(new JLabel());
        frame.add(new JLabel());
        frame.add(genre);
        frame.add(genrefield);   
        frame.add(new JLabel());
        frame.add(new JLabel());

        frame.add(retour);
        frame.add(ajouter);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

	
	



