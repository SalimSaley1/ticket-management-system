package com.hahn.ticketsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentResponseDTO {

    private Long id;
    private String content;
    private LocalDateTime creationDate;
    private String fullName;
    private Long ticketId;

}
