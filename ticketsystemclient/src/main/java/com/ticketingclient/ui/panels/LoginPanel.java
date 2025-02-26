package com.ticketingclient.ui.panels;

import com.ticketingclient.enums.Priority;
import com.ticketingclient.models.AppUserRequestModel;
import com.ticketingclient.models.AppUserResponseModel;
import com.ticketingclient.services.AuthResponse;
import com.ticketingclient.services.TicketService;
import com.ticketingclient.ui.components.RegisterDialog;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.function.Consumer;

public class LoginPanel extends JPanel {

        private JTextField emailField;
        private JPasswordField passwordField;
        private JButton loginButton;
        private JButton registerButton;
        private final Consumer<AppUserResponseModel> onLoginSuccess;
        private final TicketService ticketService;

        public LoginPanel(Consumer<AppUserResponseModel> onLoginSuccess) {
            this.onLoginSuccess = onLoginSuccess;
            this.ticketService = new TicketService();

            setLayout(new MigLayout("fill, insets 20", "[grow]"));

            // Création des composants
            JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[][grow]", "[]20[]"));

            emailField = new JTextField(20);
            passwordField = new JPasswordField(20);
            loginButton = new JButton("Connexion");
            registerButton = new JButton("S'inscrire");


            // Ajout des composants
            formPanel.add(new JLabel("Nom d'utilisateur:"));
            formPanel.add(emailField, "growx");
            formPanel.add(new JLabel("Mot de passe:"));
            formPanel.add(passwordField, "growx");
            formPanel.add(loginButton, "span 2, split 2, align center");
            formPanel.add(registerButton);

            add(formPanel, "center, center");

            // Gestion des événements
            loginButton.addActionListener(e -> handleLogin());
            registerButton.addActionListener(e -> showRegisterDialog());
        }
/*
        private void handleLogin() {
            try {
                ticketService.login(
                        emailField.getText(),
                        new String(passwordField.getPassword())
                );
                // Récupérer l'utilisateur et appeler onLoginSuccess
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erreur de connexion: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

 */

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        AppUserRequestModel appUserRequestModel = new AppUserRequestModel(email, password);

        try {
            AuthResponse response = ticketService.login(appUserRequestModel);
            if (response != null) {
                AppUserResponseModel user = response.getUser();
                onLoginSuccess.accept(user);  //like a setter
            } else {
                JOptionPane.showMessageDialog(LoginPanel.this,
                        "Échec de la connexion. Veuillez vérifier vos identifiants.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(LoginPanel.this,
                    "Erreur de connexion au serveur: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

        private void showRegisterDialog() {
            RegisterDialog dialog = new RegisterDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this)
            );
            dialog.setVisible(true);
        }

}
