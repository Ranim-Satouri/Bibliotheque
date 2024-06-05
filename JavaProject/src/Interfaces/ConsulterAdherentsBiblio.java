package Interfaces;

import Class.Utilisateur;
import Exceptions.MyException;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

public class ConsulterAdherentsBiblio {
	private static int selectedRow = -1;
    public ConsulterAdherentsBiblio() {
        JFrame frame = new JFrame("Supprimer Utilisateur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.PINK); 

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Id");
        model.addColumn("Nom");
        model.addColumn("Prenom");
        model.addColumn("Role");      
        try {
            List<Utilisateur> listeUser = Utilisateur.getListeUtilisateurs();
            for (Utilisateur user : listeUser) {
                model.addRow(new Object[]{user.GetId(), user.GetNom(),user.GetPrenom(),user.GetRole()});
            }
        } catch (MyException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
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
        JButton supprimer = new JButton("Supprimer cet Adhérent ");
        supprimer.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {	            
            	if (selectedRow != -1) {
                    int choix = JOptionPane.showConfirmDialog(frame, "Êtes-vous sûr de supprimer cet Adhérent ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                    if (choix == JOptionPane.YES_OPTION) {                    
	            		try {
			                int id = (int) table.getValueAt(selectedRow, 0);
			                String nom=(String) table.getValueAt(selectedRow, 1);
			                String prenom=(String) table.getValueAt(selectedRow, 2);
			                String role=(String) table.getValueAt(selectedRow, 3);
			                Utilisateur user=new Utilisateur(id,nom,prenom,role);
			                user.supprimerUtilisateur();     
			                JOptionPane.showMessageDialog(null, "Suppression réussie avec succès !", "Suppression", JOptionPane.INFORMATION_MESSAGE);
			                frame.dispose();
			                new ConsulterAdherentsBiblio();
	                        selectedRow = -1;          		               
	                   }catch (Exception ex) {
	                        JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);	            	
	                   }
                    }	
	            }else {
	                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne avant de cliquer sur le bouton.");
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
        buttonPanel.add(supprimer);        

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
             
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


