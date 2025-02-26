// TicketDetailsDialog - Dialog to view and modify a ticket
package com.ticketingclient.ui.components;

import com.ticketingclient.enums.Role;
import com.ticketingclient.enums.Status;
import com.ticketingclient.models.AppUserResponseModel;
import com.ticketingclient.models.TicketResponseModel;
import com.ticketingclient.models.UserCommentRequestModel;
import com.ticketingclient.models.UserCommentResponseModel;
import com.ticketingclient.services.TicketService;
import com.ticketingclient.util.TokenManager;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketDetailsDialog extends JDialog {
    private final TicketResponseModel ticket;
    private final AppUserResponseModel currentUser;
    private final TicketService ticketService;
    @Getter
    private final Runnable onTicketUpdated;

    private JTextArea commentArea;
    private JComboBox<Status> statusCombo;
    private JPanel commentsPanel;

    private TicketTableModel ticketTableModel;

    public TicketDetailsDialog(Window owner, TicketResponseModel ticket, AppUserResponseModel currentUser,
                               TicketService ticketService, Runnable onTicketUpdated) {
        super(owner, "Détails du ticket #" + ticket.getId(), ModalityType.APPLICATION_MODAL);
        this.ticket = ticket;
        this.currentUser = currentUser;
        this.ticketService = ticketService;
        this.onTicketUpdated = onTicketUpdated;

        this.ticketTableModel = new TicketTableModel();

        setSize(600, 500);
        setLocationRelativeTo(owner);

        initializeComponents();
        loadComments();
    }

    private void initializeComponents() {
        setLayout(new MigLayout("fill, insets 20", "[grow]"));

        // Ticket information panel
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, "grow, wrap");

        // Comments section
        add(new JSeparator(), "growx, wrap");
        add(new JLabel("Commentaires:"), "wrap");

        commentsPanel = new JPanel(new MigLayout("fillx, insets 0", "[grow]"));
        add(new JScrollPane(commentsPanel), "grow, wrap");

        // Add comment & status update (if IT support)
        add(new JSeparator(), "growx, wrap");

        if (currentUser.getRole() == Role.IT_SUPPORT) {
            add(createStatusUpdatePanel(), "growx, wrap");
        }

        add(createAddCommentPanel(), "growx, wrap");
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 0", "[][grow]"));

        panel.add(new JLabel("Titre:"), "right");
        panel.add(new JLabel(ticket.getTitle()), "wrap");

        panel.add(new JLabel("Description:"), "right, top");
        JTextArea descArea = new JTextArea(ticket.getDescription());
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(descArea), "grow, wrap");

        panel.add(new JLabel("Catégorie:"), "right");
        panel.add(new JLabel(ticket.getCategory().toString()), "wrap");

        panel.add(new JLabel("Priorité:"), "right");
        panel.add(new JLabel(ticket.getPriority().toString()), "wrap");

        panel.add(new JLabel("Statut:"), "right");
        panel.add(new JLabel(ticket.getStatus().toString()), "wrap");

        panel.add(new JLabel("Créé par:"), "right");
        panel.add(new JLabel(ticket.getCreatedByFullName()), "wrap");

        panel.add(new JLabel("Date de création:"), "right");
        panel.add(new JLabel(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(ticket.getCreationDate())), "wrap");

        return panel;
    }

    private JPanel createStatusUpdatePanel() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));

        panel.add(new JLabel("Changer le statut:"), "left");

        statusCombo = new JComboBox<>(Status.values());
        statusCombo.setSelectedItem(ticket.getStatus());
        panel.add(statusCombo);

        JButton updateButton = new JButton("Mettre à jour");
        panel.add(updateButton);

        updateButton.addActionListener(e -> updateTicketStatus());

        return panel;
    }

    private JPanel createAddCommentPanel() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 0", "[grow][]"));

        commentArea = new JTextArea(3, 30);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);

        JButton addButton = new JButton("Ajouter un commentaire");

        panel.add(new JScrollPane(commentArea), "grow");
        panel.add(addButton, "top");

        addButton.addActionListener(e -> addComment());

        return panel;
    }


    private void loadComments() {
        commentsPanel.removeAll();

        if (ticket.getComments() == null || ticket.getComments().isEmpty()) {
            commentsPanel.add(new JLabel("Aucun commentaire pour ce ticket."), "wrap");
        } else {
            for (UserCommentResponseModel comment : ticket.getComments()) {
                JPanel commentPanel = new JPanel(new MigLayout("fillx, insets 5", "[grow]"));
                commentPanel.setBorder(BorderFactory.createEtchedBorder());

                JLabel authorLabel = new JLabel(comment.getFullName());
                authorLabel.setFont(authorLabel.getFont().deriveFont(Font.BOLD));

                JLabel dateLabel = new JLabel(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(comment.getCreationDate()));
                dateLabel.setFont(dateLabel.getFont().deriveFont(Font.ITALIC));

                JPanel headerPanel = new JPanel(new MigLayout("insets 0"));
                headerPanel.add(authorLabel);
                headerPanel.add(dateLabel);

                JTextArea contentArea = new JTextArea(comment.getContent());
                contentArea.setEditable(false);
                contentArea.setLineWrap(true);
                contentArea.setWrapStyleWord(true);
                contentArea.setBackground(commentPanel.getBackground());

                commentPanel.add(headerPanel, "wrap");
                commentPanel.add(contentArea, "wrap");

                commentsPanel.add(commentPanel, "growx, wrap");
            }
        }

        // Add audit log entries
        /*
        if (ticket.getAuditLog() != null && !ticket.getAuditLog().isEmpty()) {
            commentsPanel.add(new JSeparator(), "growx, wrap");
            commentsPanel.add(new JLabel("Historique des modifications:"), "wrap");

            for (AuditLog entry : ticket.getAuditLog()) {
                JPanel logPanel = new JPanel(new MigLayout("fillx, insets 5", "[grow]"));
                logPanel.setBorder(BorderFactory.createEtchedBorder());
                logPanel.setBackground(new Color(245, 245, 245));

                JLabel userLabel = new JLabel(entry.getUser().getUsername());
                userLabel.setFont(userLabel.getFont().deriveFont(Font.ITALIC));

                JLabel dateLabel = new JLabel(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(entry.getTimestamp()));

                JLabel actionLabel = new JLabel(entry.getAction());
                actionLabel.setFont(actionLabel.getFont().deriveFont(Font.BOLD));

                JPanel headerPanel = new JPanel(new MigLayout("insets 0"));
                headerPanel.setBackground(logPanel.getBackground());
                headerPanel.add(userLabel);
                headerPanel.add(dateLabel);

                logPanel.add(headerPanel, "wrap");
                logPanel.add(actionLabel, "wrap");

                if (entry.getDetails() != null && !entry.getDetails().isEmpty()) {
                    JLabel detailsLabel = new JLabel(entry.getDetails());
                    logPanel.add(detailsLabel, "wrap");
                }

                commentsPanel.add(logPanel, "growx, wrap");
            }
        } */

        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

    private void addComment() {
        String content = commentArea.getText().trim();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer un commentaire.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserCommentRequestModel comment = new UserCommentRequestModel();
        comment.setContent(content);
        //comment.setAuthor(currentUser);
        comment.setCreationDate(LocalDateTime.now());


        try {
            UserCommentResponseModel createdComment = ticketService.addComment(
                    TokenManager.getToken(), ticket.getId(), comment);

            if (createdComment != null) {
                JOptionPane.showMessageDialog(TicketDetailsDialog.this,
                        "Commentaire créé avec succès",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshTicket();  //!!!!!!!!!!!! See later

                //dispose();
            } else {
                JOptionPane.showMessageDialog(TicketDetailsDialog.this,
                        "Erreur lors de l'ajout du commentaire",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(TicketDetailsDialog.this,
                    "Erreur de connexion au serveur",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }

    }





    private void updateTicketStatus() {
        Status newStatus = (Status) statusCombo.getSelectedItem();
        if (newStatus == ticket.getStatus()) {
            return;
        }

        try {
            TicketResponseModel updatedTicket = ticketService.updateTicketStatus(
                    TokenManager.getToken(), ticket.getId(), newStatus);

            if (updatedTicket != null) {
                //refreshTicket();
                refreshTicket();
            } else {
                JOptionPane.showMessageDialog(TicketDetailsDialog.this,
                        "Erreur lors de la mise à jour du statut",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(TicketDetailsDialog.this,
                    "Erreur de connexion au serveur: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void refreshTicket() {
        try {
            TicketResponseModel updatedTicket = ticketService.getTicket(
                    TokenManager.getToken(), ticket.getId());

            if (updatedTicket != null) {
                // Update ticket data
                ticket.setStatus(updatedTicket.getStatus());
                ticket.setComments(updatedTicket.getComments());
                //ticket.setAuditLog(updatedTicket.getAuditLog());  !!!!!!! see later

                // Refresh UI
                loadComments();
                if (statusCombo != null) {
                    statusCombo.setSelectedItem(ticket.getStatus());
                }

                // Notify parent that ticket was updated
                //onTicketUpdated.run();
                if (onTicketUpdated != null) {
                    onTicketUpdated.run();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(TicketDetailsDialog.this,
                    "Erreur lors de l'actualisation du ticket: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }




/*
    private void addComment() {
        String content = commentArea.getText().trim();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer un commentaire.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(currentUser);
        comment.setCreationDate(LocalDateTime.now());

        try {
            Comment addedComment = ticketService.addComment(
                    TokenManager.getToken(), ticket.getId(), comment);

            if (addedComment != null) {
                commentArea.setText("");
                refreshTicket();
            } else {
                JOptionPane.showMessageDialog(TicketDetailsDialog.this,
                        "Erreur lors de l'ajout du commentaire",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(TicketDetailsDialog.this,
                    "Erreur de connexion au serveur: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

 */

/*    Good one
    private void refreshTicket() {
        ticketService.getTicket(TokenManager.getToken(), ticket.getId())
                .enqueue(new Callback<TicketResponseModel>() {
                    @Override
                    public void onResponse(Call<TicketResponseModel> call, Response<TicketResponseModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Update ticket data
                            TicketResponseModel updatedTicket = response.body();
                            ticket.setStatus(updatedTicket.getStatus());
                            //ticket.setComments(updatedTicket.getComments());
                            //ticket.setAuditLog(updatedTicket.getAuditLog());

                            // Refresh UI
                            //loadComments();
                            if (statusCombo != null) {
                                statusCombo.setSelectedItem(ticket.getStatus());
                            }

                            // Notify parent that ticket was updated
                            onTicketUpdated.run();
                        }
                    }

                    @Override
                    public void onFailure(Call<TicketResponseModel> call, Throwable t) {
                        JOptionPane.showMessageDialog(TicketDetailsDialog.this,
                                "Erreur lors de l'actualisation du ticket",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
    }

 */


}