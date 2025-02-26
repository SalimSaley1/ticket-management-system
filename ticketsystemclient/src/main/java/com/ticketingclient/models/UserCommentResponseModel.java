package com.ticketingclient.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentResponseModel {

    private Long id;
    private String content;
    private LocalDateTime creationDate;
    private String fullName;
    private Long ticketId;

}
