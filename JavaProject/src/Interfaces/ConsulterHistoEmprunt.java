package Interfaces;

import DTO.*;
import Class.*;
import Exceptions.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class ConsulterHistoEmprunt {
    private static int selectedRow = -1;
	public ConsulterHistoEmprunt(int user_id) {		
		JFrame frame = new JFrame("Consulter Historique des Empruntes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Titre");
        model.addColumn("Auteur");
        model.addColumn("date Emprunte");
        model.addColumn("date Retour");
        model.addColumn("statut Actuel");
              
        try {
            List<HistoriqueEmpDTO> listeHistoriqueEmpDTO= Emprunt.getListeHistEmp(user_id);
            for (HistoriqueEmpDTO object : listeHistoriqueEmpDTO) {
                model.addRow(new Object[]{object.getId_emprunt(),object.getTitre(),object.getAuteur(),object.getDate_emprunt(),object.getDate_retour(),object.getStatut()});
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

        JButton retourner_Button = new JButton("Retourner ce livre");
        retourner_Button.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            if (selectedRow != -1) {
	                String statut = (String) table.getValueAt(selectedRow, 5);
	                if(statut.equals("Terminé")) {
	                    JOptionPane.showMessageDialog(frame, "Livre deja Rotourné !");
	                }else {
	                	int choix = JOptionPane.showConfirmDialog(frame, "Êtes-vous sûr de vouloir retourner ce livre ?", "Confirmation de retour de livre", JOptionPane.YES_NO_OPTION);
	                    if (choix == JOptionPane.YES_OPTION) { 
		                	int id_emprunt =(int) table.getValueAt(selectedRow, 0);
		                	String titre = (String) table.getValueAt(selectedRow, 1);
		                    String auteur = (String) table.getValueAt(selectedRow, 2);
		                    try {
		                    	Livre livre=Livre.RechercherLivre(titre, auteur);
		                    	Emprunt emp=new Emprunt(id_emprunt,user_id,livre.GetId());
		                        emp.retournerLivre();
		                        JOptionPane.showMessageDialog(frame, "Retour de livre réussi !", "Succès du retour", JOptionPane.INFORMATION_MESSAGE);
		                        frame.dispose();
		                        new ConsulterHistoEmprunt(user_id);
		                        selectedRow = -1;          	
		                    }catch (MyException ex) {
		                        JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		                    }                  
	                    }  
                  }    
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne avant de cliquer sur le bouton.");
            }}
        });
        JButton retour =new JButton("Retour a L'Accueil");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();
            	new UserAccueil(user_id);
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(retour);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(retourner_Button);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
}
