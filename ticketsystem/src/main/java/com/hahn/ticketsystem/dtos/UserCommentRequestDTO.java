package com.hahn.ticketsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentRequestDTO {

    private String content;
    //private Long ticketId;
    private LocalDateTime creationDate;

}

