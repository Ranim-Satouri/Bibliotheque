package Interfaces;

import Class.Livre;
import Exceptions.MyException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class ConsulterCatalogue {
    private static int selectedRow = -1;
    public ConsulterCatalogue(int user_id) {
        JFrame frame = new JFrame("Consulter le Catalogue des Livres");
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
            JOptionPane.showMessageDialog(frame, e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
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
        JButton afficher_Button = new JButton("Afficher les détails de ce livre");
        afficher_Button.setPreferredSize(new Dimension(210, 30));
        afficher_Button.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            if (selectedRow != -1) {
                String titre = (String) table.getValueAt(selectedRow, 0);
                String auteur = (String) table.getValueAt(selectedRow, 1);
                try {
                	Livre livre=Livre.RechercherLivre(titre, auteur);
                    frame.dispose();
                    new BookDetails(user_id,livre);
                    selectedRow = -1;                	
                }catch(MyException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
                }
                
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne avant de cliquer sur le bouton.");
            }}
        });
        JButton retour =new JButton("Retour a L'acceuil");
        retour.setPreferredSize(new Dimension(210, 30));
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
        buttonPanel.add(afficher_Button);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
