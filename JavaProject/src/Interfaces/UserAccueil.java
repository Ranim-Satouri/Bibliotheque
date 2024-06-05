package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserAccueil {
	public UserAccueil(int user_id) {
		JFrame frame = new JFrame("Accueil");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        JButton chercher_Button = new JButton("Rechercher un livre");
        JButton consult_livre_Button = new JButton("Consulter le Catalogue des Livres");
        JButton hist_Button = new JButton("Consulter l'historique des empruntes");

        frame.setLayout(new GridLayout(3,1));
        
        consult_livre_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ConsulterCatalogue(user_id);
            }
        });
        chercher_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ChercherLivre(user_id);
            }
        });
        hist_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ConsulterHistoEmprunt(user_id);
            }
        });
        frame.add(chercher_Button);
        frame.add(consult_livre_Button);
        frame.add(hist_Button);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);                      
	}
}

