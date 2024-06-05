package Interfaces;

import Exceptions.*;
import Class.Utilisateur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Inscription {
	public Inscription() {
		JFrame frame = new JFrame("Inscription");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.PINK); 

        JLabel nom = new JLabel("nom");
        JLabel prenom = new JLabel("prenom:");
        JLabel login= new JLabel("login");
        JLabel pwd = new JLabel("pwd");
        
        JTextField nomfield = new JTextField(15);
        JTextField prenomfield = new JTextField(15);
        JTextField loginfield= new JTextField(20);
        JTextField pwdfield = new JTextField(20);
        
        JButton inscrire =new JButton("S'inscrire");

        frame.setLayout(new GridLayout(6, 2));
        
        JRadioButton etudiantRadioButton = new JRadioButton("Etudiant");
        JRadioButton enseignantRadioButton = new JRadioButton("Enseignant");
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(etudiantRadioButton);
        buttonGroup.add(enseignantRadioButton);
        
        final JRadioButton[] roleSelected = {null}; 

        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (etudiantRadioButton.isSelected()) {
                    roleSelected[0] = etudiantRadioButton;
                } else if (enseignantRadioButton.isSelected()) {
                    roleSelected[0] = enseignantRadioButton;
                }         
            }
        };
        etudiantRadioButton.addActionListener(radioButtonListener);
        enseignantRadioButton.addActionListener(radioButtonListener);
        
        inscrire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String nom=nomfield.getText().trim();
	            String prenom=prenomfield.getText().trim();
	            String login=loginfield.getText().trim();
	            String pwd=pwdfield.getText();
	            String role="";
            	if(!(nom.isEmpty() | prenom.isEmpty() | login.isEmpty() | pwd.isEmpty() | roleSelected == null)) { 
            		role=roleSelected[0].getText();
            		Utilisateur user=new Utilisateur(nom,prenom,login,pwd,role);
            		try {                		
            			user.inscrire();
            			JOptionPane.showMessageDialog(frame, "Nous avons bien recus votre Inscription ,vous pouvez dés maintenant vous connecter !");
                    	frame.dispose();
                        new LogIn();    
            		}catch (MailException | MyException |PwdException m ){
                        JOptionPane.showMessageDialog(frame, m.getMessage(),"Erreur d'Inscription",JOptionPane.ERROR_MESSAGE);
            		}
            	}else {
            		JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.", "Champs Incomplets", JOptionPane.WARNING_MESSAGE);
            	}
            }
        });
        JButton retour =new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();
            	new LogIn();
            }
        });
        frame.add(nom);
        frame.add(nomfield );
        frame.add(prenom);
        frame.add(prenomfield );
        frame.add(login);
        frame.add(loginfield );
        frame.add(pwd);
        frame.add(pwdfield);
        frame.add(etudiantRadioButton);
        frame.add(enseignantRadioButton);
        frame.add(retour);
        frame.add(inscrire);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  
    }	
}