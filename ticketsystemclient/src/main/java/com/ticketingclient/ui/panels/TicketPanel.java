package com.ticketingclient.ui.panels;

import com.ticketingclient.enums.Status;
import com.ticketingclient.models.AppUserResponseModel;
import com.ticketingclient.models.TicketResponseModel;
import com.ticketingclient.services.TicketService;
import com.ticketingclient.ui.components.CreateTicketDialog;
import com.ticketingclient.ui.components.TicketDetailsDialog;
import com.ticketingclient.ui.components.TicketTableModel;
import com.ticketingclient.util.SimpleDocumentListener;
import com.ticketingclient.util.TokenManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

public class TicketPanel extends JPanel {

    private AppUserResponseModel currentUser;
    private JTable ticketTable;
    private TicketTableModel tableModel;
    private JTextField searchField;
    private JComboBox<Status> statusFilter;
    private JButton createTicketButton;
    private JButton refreshButton;
    private JButton logoutButton;
    private TicketService ticketService;
    private final Consumer<Void> onLogout;

    public TicketPanel(Consumer<Void> onLogout) {
        this.ticketService = new TicketService();
        this.onLogout = onLogout;

        setLayout(new MigLayout("fill, insets 10", "[grow]", "[]10[]10[grow]"));
        initializeComponents();
    }

    private void initializeComponents() {
        // Barre d'outils
        JPanel toolbarPanel = new JPanel(new MigLayout("insets 0", "[]10[]push[]10[]10[]"));

        // Éléments de recherche et filtrage
        searchField = new JTextField(20);
        searchField.setToolTipText("Rechercher par ID ou titre");

        statusFilter = new JComboBox<>(Status.values());
        statusFilter.insertItemAt(null, 0);
        statusFilter.setSelectedIndex(0);
        statusFilter.setToolTipText("Filtrer par statut");

        // Boutons d'action
        createTicketButton = new JButton("Nouveau Ticket");
        createTicketButton.setEnabled(false); // Désactivé jusqu'à ce que l'utilisateur soit défini

        refreshButton = new JButton("Actualiser");
        logoutButton = new JButton("Se déconnecter");

        // Ajout des composants à la barre d'outils
        toolbarPanel.add(new JLabel("Rechercher:"));
        toolbarPanel.add(searchField);
        toolbarPanel.add(new JLabel("Statut:"));
        toolbarPanel.add(statusFilter);
        toolbarPanel.add(createTicketButton);
        toolbarPanel.add(refreshButton);
        toolbarPanel.add(logoutButton);

        // Configuration de la table des tickets
        tableModel = new TicketTableModel();
        ticketTable = new JTable(tableModel);
        configureTable();

        JScrollPane scrollPane = new JScrollPane(ticketTable);

        // Ajout des composants au panel principal
        add(toolbarPanel, "growx, wrap");
        add(scrollPane, "grow");

        // Configuration des écouteurs d'événements
        setupListeners();
    }

    private void configureTable() {
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketTable.setRowHeight(25);
        ticketTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void setupListeners() {
        // Boutons
        createTicketButton.addActionListener(e -> showCreateTicketDialog());
        refreshButton.addActionListener(e -> refreshTickets());
        logoutButton.addActionListener(e -> handleLogout());

        // Filtres
        searchField.getDocument().addDocumentListener(new SimpleDocumentListener(this::updateFilters));
        statusFilter.addActionListener(e -> updateFilters());

        // Double-clic sur un ticket
        ticketTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showTicketDetails();
                }
            }
        });
    }

    // Méthode appelée lorsque les filtres sont modifiés
    private void updateFilters() {
        String searchText = searchField.getText().toLowerCase();
        Status selectedStatus = (Status) statusFilter.getSelectedItem();

        // Applique les filtres au modèle de table
        tableModel.applyFilters(searchText, selectedStatus);
    }

    // Méthode pour rafraîchir la liste des tickets
    public void refreshTickets() {
        try {
            List<TicketResponseModel> tickets = ticketService.getTickets(TokenManager.getToken());
            if (tickets != null) {
                tableModel.setTickets(tickets);
                updateFilters();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    TicketPanel.this,
                    "Erreur lors du chargement des tickets: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showCreateTicketDialog() {
        CreateTicketDialog dialog = new CreateTicketDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                currentUser,
                ticketService,
                () -> {
                    JOptionPane.showMessageDialog(this, "Ticket créé avec succès !");
                    refreshTickets();
                }
        );
        dialog.setVisible(true);
    }

    private void showTicketDetails() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow >= 0) {
            TicketResponseModel ticket = tableModel.getTicketAt(selectedRow);
            TicketDetailsDialog dialog = new TicketDetailsDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    ticket,
                    currentUser,
                    ticketService,
                    () -> {
                        JOptionPane.showMessageDialog(this, "Ticket mis à jour avec succès !");
                        refreshTickets();
                    }
            );
            dialog.setVisible(true);
        }
    }

    // Méthode pour gérer la déconnexion
    private void handleLogout() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Êtes-vous sûr de vouloir vous déconnecter ?",
                "Confirmation de déconnexion",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            // Effacer le token de session
            TokenManager.clearToken();

            // Notifier le conteneur (MainWindow) de la déconnexion
            if (onLogout != null) {
                onLogout.accept(null);
            }
        }
    }

    public void setUser(AppUserResponseModel user) {
        this.currentUser = user;
        createTicketButton.setEnabled(true);
        refreshTickets();
    }











    /*
    private final TicketService ticketService;
    private AppUserResponseModel currentUser;
    private JTable ticketTable;
    private TicketTableModel tableModel;
    private JTextField searchField;
    private JComboBox<Status> statusFilter;
    private JButton createTicketButton;
    private JButton refreshButton;
    private JButton logoutButton;

    public TicketPanel() {
        this.ticketService = new TicketService();

        setLayout(new MigLayout("fill, insets 10", "[grow]", "[]10[]10[grow]"));

        // Barre d'outils
        JPanel toolbarPanel = new JPanel(new MigLayout("insets 0", "[]10[]push[]10[]10[]"));
        searchField = new JTextField(20);
        statusFilter = new JComboBox<>(Status.values());
        createTicketButton = new JButton("Nouveau Ticket");
        refreshButton = new JButton("Actualiser");
        logoutButton = new JButton("Se déconnecter");

        toolbarPanel.add(new JLabel("Rechercher:"));
        toolbarPanel.add(searchField);
        toolbarPanel.add(new JLabel("Statut:"));
        toolbarPanel.add(statusFilter);
        toolbarPanel.add(createTicketButton);
        toolbarPanel.add(refreshButton);
        toolbarPanel.add(logoutButton);

        // Table des tickets
        tableModel = new TicketTableModel();
        ticketTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ticketTable);

        // Ajout des composants
        add(toolbarPanel, "growx, wrap");
        add(scrollPane, "grow");

        // Événements
        createTicketButton.addActionListener(e -> showCreateTicketDialog());
        refreshButton.addActionListener(e -> refreshTickets());
        logoutButton.addActionListener(e -> logOut());
        searchField.getDocument().addDocumentListener(new SimpleDocumentListener(
                () -> filterTickets()  // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        ));
        statusFilter.addActionListener(e -> filterTickets());

        ticketTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showTicketDetails();
                }
            }
        });
    }

    public void setUser(AppUserResponseModel user) {
        this.currentUser = user;
        createTicketButton.setEnabled(true);
        refreshTickets();
    }
/******** c etait deja comenté ******
    private void refreshTickets() {
        try {
            List<TicketResponseModel> tickets = ticketService.getTickets();
            tableModel.setTickets(tickets);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du chargement des tickets: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

 */

    /*

    public void refreshTickets() {
        try {
            List<TicketResponseModel> tickets = ticketService.getTickets(TokenManager.getToken());
            if (tickets != null) {
                tableModel.setTickets(tickets);
                updateFilters();               //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    TicketPanel.this,
                    "Erreur lors du chargement des tickets: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterTickets() {
        String searchText = searchField.getText().toLowerCase();
        Status selectedStatus = (Status) statusFilter.getSelectedItem();
        tableModel.applyFilters(searchText, selectedStatus);
    }

    private void showCreateTicketDialog() {
        CreateTicketDialog dialog = new CreateTicketDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                currentUser,
                ticketService,
                () -> {
                    JOptionPane.showMessageDialog(null, "Ticket ajouté !");
                }
        );
        dialog.setVisible(true);
        if (dialog.isTicketCreated()) {
            refreshTickets();
        }
    }

    private void showTicketDetails() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow >= 0) {
            TicketResponseModel ticket = tableModel.getTicketAt(selectedRow);
            TicketDetailsDialog dialog = new TicketDetailsDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    ticket,
                    currentUser,
                    ticketService,
                    () -> {
                        JOptionPane.showMessageDialog(null, "Ticket mis à jour !");
                    }

            );
            dialog.setVisible(true);
            if (dialog.getOnTicketUpdated() != null) {
                refreshTickets();
            }
        }
    }

     */
}
