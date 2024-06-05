package Interfaces;

import Class.Livre;
import Exceptions.MyException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsulterLivresBiblio {
	private static int selectedRow = -1;
    public ConsulterLivresBiblio() {
        JFrame frame = new JFrame("Supprimer livres");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Titre");
        model.addColumn("Auteur");
      
        try {
            List<Livre> listeLivres = Livre.getListeLivres();

            for (Livre livre : listeLivres) {
                model.addRow(new Object[]{livre.GetTitre(), livre.GetAuteur()});
            }
        } catch (MyException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
                
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedRow = table.getSelectedRow();
                }
            }
        });

        JButton supprimer = new JButton("supprimer ce livre ");
        supprimer.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            if (selectedRow != -1) {
                int choix = JOptionPane.showConfirmDialog(frame, "Êtes-vous sûr de supprimer ce livre ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                if (choix == JOptionPane.YES_OPTION) {
	                String titre = (String) table.getValueAt(selectedRow, 0);
	                String auteur = (String) table.getValueAt(selectedRow, 1);
	                try {
	                	Livre livre=Livre.RechercherLivre(titre, auteur);
	                	livre.supprimerLivre();
	                   	JOptionPane.showMessageDialog(frame, "Suppression réussie avec succès !", "Suppression", JOptionPane.INFORMATION_MESSAGE);
	                   	frame.dispose();
	                   	new ConsulterLivresBiblio();
	                    selectedRow = -1;          	
	                }catch (MyException ex) {
	                   JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
	            	}
                }   
        	}else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne avant de cliquer sur le bouton.");
            }  
        	}
        });
        
        JButton retour = new JButton("Retour a L'Accueil ");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new BiblioAccueil();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(retour);
        buttonPanel.add(supprimer);        

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

