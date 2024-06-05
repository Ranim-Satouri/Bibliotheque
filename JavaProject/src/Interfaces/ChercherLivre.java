package Interfaces;

import Exceptions.MyException;
import Class.Livre;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChercherLivre {	
	public ChercherLivre(int user_id) {
		JFrame frame = new JFrame("Rechercher un Livre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.PINK); 

        frame.setSize(800, 400);
        JLabel titre = new JLabel("Nom de Livre :");
        JTextField titreField = new JTextField(10);
        JLabel auteur = new JLabel("Auteur de Livre :");
        JTextField auteurField = new JTextField(10);
        
        JButton chercher = new JButton("Rechercher");
        JButton retourButton = new JButton("Retour a L'acceuil");
        frame.setLayout(new GridLayout(6,2));
        
        chercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titreField.getText().trim();
                String auteur = auteurField.getText().trim();
                if(! title.isEmpty() & ! auteur.isEmpty()) {
                	try {
                		Livre livre=Livre.RechercherLivre(title, auteur);
                        if(livre==null) {
                        	JOptionPane.showMessageDialog(frame, "Livre inexistant", "Erreur", JOptionPane.ERROR_MESSAGE);
                		} else {
                			frame.dispose();
                			new BookDetails(user_id,livre);
                		}                		
                	}catch (MyException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
                	}      
                }else {
                	JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.", "Champs Incomplets", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 frame.dispose();
                 new UserAccueil(user_id);
            }
        });
        frame.add( new JLabel());
        frame.add( new JLabel());
        frame.add(titre);
        frame.add(titreField);
        frame.add( new JLabel());
        frame.add( new JLabel());
        frame.add(auteur);
        frame.add(auteurField);
        frame.add( new JLabel());
        frame.add( new JLabel());
        frame.add(retourButton);
        frame.add(chercher);       
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);    
	}	
}
