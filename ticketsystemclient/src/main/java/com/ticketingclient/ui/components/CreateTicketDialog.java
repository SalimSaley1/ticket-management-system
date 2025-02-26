package com.ticketingclient.ui.components;

import com.ticketingclient.enums.Category;
import com.ticketingclient.enums.Priority;
import com.ticketingclient.models.AppUserResponseModel;
import com.ticketingclient.models.TicketRequestModel;
import com.ticketingclient.models.TicketResponseModel;
import com.ticketingclient.services.TicketService;
import com.ticketingclient.util.TokenManager;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CreateTicketDialog extends JDialog {
    private final TicketService ticketService;
    private final AppUserResponseModel creator;
    @Getter
    private boolean ticketCreated = false;

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<Priority> priorityCombo;
    private JComboBox<Category> categoryCombo;

    private Runnable onTicketCreated;

    public CreateTicketDialog(JFrame parent, AppUserResponseModel creator, TicketService ticketService, Runnable onTicketCreated) {
        super(parent, "Nouveau Ticket", true);
        this.ticketService = ticketService;
        this.creator = creator;

        this.onTicketCreated = onTicketCreated;

        setLayout(new MigLayout("fill, insets 20", "[right][grow]", "[]10[]"));

        // Création des composants
        titleField = new JTextField(30);
        descriptionArea = new JTextArea(5, 30);
        priorityCombo = new JComboBox<>(Priority.values());
        categoryCombo = new JComboBox<>(Category.values());

        JButton createButton = new JButton("Créer");
        JButton cancelButton = new JButton("Annuler");

        // Ajout des composants
        add(new JLabel("Titre:"), "right");
        add(titleField, "growx, wrap");

        add(new JLabel("Description:"), "right");
        add(new JScrollPane(descriptionArea), "growx, wrap");

        add(new JLabel("Priorité:"), "right");
        add(priorityCombo, "growx, wrap");

        add(new JLabel("Catégorie:"), "right");
        add(categoryCombo, "growx, wrap");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, "span 2, growx");

        // Événements
        createButton.addActionListener(e -> createTicket());
        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }

    /*

    private void createTicket() {
        try {
            TicketRequestModel ticket = new TicketRequestModel();
            ticket.setTitle(titleField.getText());
            ticket.setDescription(descriptionArea.getText());
            ticket.setPriority((Priority) priorityCombo.getSelectedItem());
            ticket.setCategory((Category) categoryCombo.getSelectedItem());
            ticket.setCreator(creator);

            ticketService.createTicket(ticket);
            ticketCreated = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors de la création du ticket: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    */




    public void createTicket() {
        if (validateForm()) {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            Priority priority = (Priority) priorityCombo.getSelectedItem();
            Category category = (Category) categoryCombo.getSelectedItem();

            TicketRequestModel ticket = new TicketRequestModel();
            ticket.setTitle(title);
            ticket.setDescription(description);
            ticket.setPriority(priority);
            ticket.setCategory(category);
            ticket.setCreator(creator);

            try {
                TicketResponseModel createdTicket = ticketService.createTicket(
                        TokenManager.getToken(), ticket);

                if (createdTicket != null) {
                    JOptionPane.showMessageDialog(CreateTicketDialog.this,
                            "Ticket créé avec succès",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);

                    if (onTicketCreated != null) {
                        onTicketCreated.run();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CreateTicketDialog.this,
                            "Erreur lors de la création du ticket",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(CreateTicketDialog.this,
                        "Erreur de connexion au serveur: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateForm() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer un titre pour le ticket",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer une description pour le ticket",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }






}
