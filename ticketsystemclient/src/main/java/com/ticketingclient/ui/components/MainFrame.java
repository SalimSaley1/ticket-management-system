package com.ticketingclient.ui.components;

import com.ticketingclient.models.AppUserResponseModel;
import com.ticketingclient.ui.panels.LoginPanel;
import com.ticketingclient.ui.panels.TicketPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private TicketPanel ticketPanel;
    private AppUserResponseModel currentUser;

    public MainFrame() {
        setTitle("Système de Gestion de Tickets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initializePanels();
        add(mainPanel);

        showLoginPanel();
    }

    private void initializePanels() {
        loginPanel = new LoginPanel(this::onLoginSuccess);
        ticketPanel = new TicketPanel(this::onLogout);

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(ticketPanel, "TICKETS");
    }

    private void onLoginSuccess(AppUserResponseModel user) {
        this.currentUser = user;
        ticketPanel.setUser(user);
        cardLayout.show(mainPanel, "TICKETS");
    }

    private void onLogout(Void v) {
        this.currentUser = null;
        showLoginPanel();
    }

    public void showLoginPanel() {
        cardLayout.show(mainPanel, "LOGIN");
    }


    /*
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private TicketPanel ticketPanel;
    private AppUserResponseModel currentUser;



    private void onLogin(AppUserResponseModel user) {
        this.currentUser = user;
        ticketPanel.setUser(user);
        cardLayout.show(mainPanel, "TICKETS");
    }


    public MainFrame() {
        setTitle("Système de Gestion de Tickets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this::onLogin);
        ticketPanel = new TicketPanel();

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(ticketPanel, "TICKETS");

        add(mainPanel);

        cardLayout.show(mainPanel, "LOGIN");
    }
     */






}
