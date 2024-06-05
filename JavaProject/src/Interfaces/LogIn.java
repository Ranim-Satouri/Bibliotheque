package Interfaces;

import Class.Utilisateur;
import Exceptions.MyException;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class LogIn{	
	public LogIn() {
		JFrame frame = new JFrame("S'authentifier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.getContentPane().setBackground(Color.PINK); 

        JLabel welcomeLabel = new JLabel("Bienvenue dans notre bibliothèque");
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(30.0f));
        welcomeLabel.setForeground(Color.BLUE);

        JLabel mailLabel = new JLabel("Email:");
        JTextField mailField = new JTextField(20);
        JLabel pwdLabel = new JLabel("Mot de Passe:");
        JPasswordField pwdField = new JPasswordField(20);
        pwdField.setEchoChar('*');
        JButton loginButton = new JButton("Se Connecter");
        JButton SignInButton = new JButton("S'inscrire");
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mail = mailField.getText().trim();
                char[] password = pwdField.getPassword();
                String pwd = new String(password);
                if(! mail.isEmpty() & ! pwd.isEmpty()) {
                	try {
                		Utilisateur user=Utilisateur.authentifier(mail, pwd);
                    	if(user==null) {
                            JOptionPane.showMessageDialog(frame, "E-mail ou mot de passe incorrect.");
                		} else {
                            if(! user.GetRole().equals("biblio")) {
                    			frame.dispose();
                            	new UserAccueil(user.GetId());
                            }else {
                            	frame.dispose();
                            	new BiblioAccueil();
                            }	
                	    }
            	   }catch (MyException ex) {
                       JOptionPane.showMessageDialog(frame, ex.getMessage(),"Erreur d'authentification",JOptionPane.ERROR_MESSAGE);
            	   }
                }else {
                	JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.", "Champs Incomplets", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        SignInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();
                new Inscription();
            }
        });
                
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20); 

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.PAGE_START; 
        frame.add(welcomeLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(mailLabel, gbc);
        mailLabel.setPreferredSize(new Dimension(40, 30));

        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(mailField, gbc);
        mailField.setPreferredSize(new Dimension(200, 30));

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(pwdLabel, gbc);
        pwdLabel.setPreferredSize(new Dimension(130, 30));

        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(pwdField, gbc);
        pwdField.setPreferredSize(new Dimension(200, 30));

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(SignInButton, gbc);
        SignInButton.setPreferredSize(new Dimension(222, 30));

        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(loginButton, gbc);
        loginButton.setPreferredSize(new Dimension(222, 30));

        frame.setLocationRelativeTo(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);			
	}
}
	
	
