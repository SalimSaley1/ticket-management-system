package com.hahn.ticketsystem.dtos;

import com.hahn.ticketsystem.enums.Category;
import com.hahn.ticketsystem.enums.Priority;
import com.hahn.ticketsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Category category;
    private Status status;
    private LocalDateTime creationDate;
    private String createdByFullName;

    private List<UserCommentResponseDTO> comments;

}
