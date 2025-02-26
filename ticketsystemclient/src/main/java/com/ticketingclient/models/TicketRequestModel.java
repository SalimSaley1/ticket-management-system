package com.ticketingclient.models;

import com.ticketingclient.enums.Category;
import com.ticketingclient.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestModel {

    private String title;
    private String description;
    private Priority priority;
    private Category category;

    private AppUserResponseModel creator;

}
