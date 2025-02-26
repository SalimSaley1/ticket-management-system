package com.ticketingclient.ui.components;

import com.ticketingclient.enums.Status;
import com.ticketingclient.models.TicketResponseModel;

import javax.swing.table.AbstractTableModel;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES = {"ID", "Titre", "Catégorie", "Priorité", "Statut", "Date de création"};
    private List<TicketResponseModel> tickets;
    private List<TicketResponseModel> filteredTickets;

    public TicketTableModel() {
        this.tickets = new ArrayList<>();
        this.filteredTickets = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return filteredTickets.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TicketResponseModel ticket = filteredTickets.get(rowIndex);

        switch (columnIndex) {
            case 0: return ticket.getId();
            case 1: return ticket.getTitle();
            case 2: return ticket.getCategory();
            case 3: return ticket.getPriority();
            case 4: return ticket.getStatus();
            case 5: return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(ticket.getCreationDate());
            default: return null;
        }
    }

    public void setTickets(List<TicketResponseModel> tickets) {
        this.tickets = new ArrayList<>(tickets);
        this.filteredTickets = new ArrayList<>(tickets);
        fireTableDataChanged();
    }

    public void applyFilters(String searchText, Status statusFilter) {
        filteredTickets = tickets.stream()
                .filter(ticket -> {
                    boolean matchesSearch = searchText.isEmpty() ||
                            String.valueOf(ticket.getId()).contains(searchText) ||
                            ticket.getTitle().toLowerCase().contains(searchText) ||
                            ticket.getStatus().toString().toLowerCase().contains(searchText);

                    boolean matchesStatus = statusFilter == null || ticket.getStatus() == statusFilter;

                    return matchesSearch && matchesStatus;
                })
                .collect(Collectors.toList());

        fireTableDataChanged();
    }

    public TicketResponseModel getTicketAt(int row) {
        if (row >= 0 && row < filteredTickets.size()) {
            return filteredTickets.get(row);
        }
        return null;
    }
}
