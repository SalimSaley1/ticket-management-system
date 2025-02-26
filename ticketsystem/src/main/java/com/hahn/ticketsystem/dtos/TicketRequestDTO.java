package com.hahn.ticketsystem.dtos;


import com.hahn.ticketsystem.enums.Category;
import com.hahn.ticketsystem.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO {

    private String title;
    private String description;
    private Priority priority;
    private Category category;

}
