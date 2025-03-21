package com.ticketingclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentRequestModel {

    private String content;
    //private Long ticketId;
    private LocalDateTime creationDate;


}

