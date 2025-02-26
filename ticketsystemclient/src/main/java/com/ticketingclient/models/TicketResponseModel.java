package com.ticketingclient.models;

import com.ticketingclient.enums.Category;
import com.ticketingclient.enums.Priority;
import com.ticketingclient.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseModel {

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Category category;
    private Status status;
    private LocalDateTime creationDate;
    private String createdByFullName;

    private List<UserCommentResponseModel> comments;

}
