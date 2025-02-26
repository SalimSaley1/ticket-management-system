package com.ticketingclient.ui.components;

import com.ticketingclient.enums.Category;
import com.ticketingclient.enums.Priority;
import com.ticketingclient.enums.Role;
import com.ticketingclient.models.AppUserRequestModel;
import com.ticketingclient.models.AppUserResponseModel;
import com.ticketingclient.models.TicketRequestModel;
import com.ticketingclient.models.TicketResponseModel;
import com.ticketingclient.services.AppUserService;
import com.ticketingclient.services.TicketService;
import com.ticketingclient.util.TokenManager;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class RegisterDialog extends JDialog {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JComboBox<Role> roleCombo;
    private JButton signUpButton;
    private JButton cancelButton;
    private AppUserService appUserService;

    public RegisterDialog(JFrame parent) {
        super(parent, "Nouvelle Inscription", true);

        this.appUserService = new AppUserService();
        setLayout(new MigLayout("fill, insets 20", "[right][grow]", "[]10[]"));

        // Création des composants
        JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[][grow]", "[]10[]"));

        emailField = new JTextField(30);
        passwordField = new JPasswordField(30);
        passwordField.setEchoChar('*'); // Masquer les caractères du mot de passe
        fullNameField = new JTextField(30);

        // Vérification que Role.values() ne plante pas
        Role[] roles = Role.values();
        roleCombo = new JComboBox<>(roles);

        signUpButton = new JButton("S'inscrire");
        cancelButton = new JButton("Annuler");

        // Ajout des composants au formPanel
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField, "growx");

        formPanel.add(new JLabel("Mot de passe:"));
        formPanel.add(passwordField, "growx");

        formPanel.add(new JLabel("Nom complet:"));
        formPanel.add(fullNameField, "growx");

        formPanel.add(new JLabel("Rôle:"));
        formPanel.add(roleCombo, "growx");

        formPanel.add(signUpButton, "span 2, split 2, align center");
        formPanel.add(cancelButton);

        // Ajout du formPanel au dialog
        add(formPanel, "span, grow, wrap");

        // Événements
        signUpButton.addActionListener(e -> handleSignUp());
        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }


    public void handleSignUp() {
        if (validateForm()) {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String fullName = fullNameField.getText().trim();
            Role role = (Role) roleCombo.getSelectedItem();

            AppUserRequestModel user = new AppUserRequestModel();
            user.setEmail(email);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setRole(role);

            try {
                AppUserResponseModel createdUser = appUserService.register(user);

                if (createdUser != null) {
                    JOptionPane.showMessageDialog(RegisterDialog.this,
                            "Inscription éffectué avec succès",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);

                    /*if (onUserCreated != null) {
                        onUserCreated.run();
                    }

                     */
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegisterDialog.this,
                            "Erreur lors de l'inscription!",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(RegisterDialog.this,
                        "Erreur de connexion au serveur: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateForm() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer votre email",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer un mot de passe",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


/*
    private final AppUserService appUserService;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JComboBox<Role> roleCombo;
    private JButton signUpButton;
    private JButton cancelButton;


    public RegisterDialog(JFrame parent) {
        super(parent, "Nouveau Ticket", true);

        this.appUserService = new AppUserService();

        setLayout(new MigLayout("fill, insets 20", "[right][grow]", "[]10[]"));

        // Création des composants
        JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[][grow]", "[]20[]"));

        emailField = new JTextField(30);
        passwordField = new JPasswordField(30);
        fullNameField = new JTextField(30);
        roleCombo = new JComboBox<>(Role.values());

        signUpButton = new JButton("S'inscrire");
        cancelButton = new JButton("Annuler");

        // Ajout des composants
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField, "growx");

        formPanel.add(new JLabel("Mot de passe:"));
        formPanel.add(passwordField, "growx");

        formPanel.add(new JLabel("Nom complet:"));
        formPanel.add(fullNameField, "growx");

        add(new JLabel("Role:"), "right");
        add(roleCombo, "growx, wrap");

        formPanel.add(signUpButton, "span 2, split 2, align center");
        formPanel.add(cancelButton);

        // Événements

        signUpButton.addActionListener(e -> handleSignUp());
        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    } */











}