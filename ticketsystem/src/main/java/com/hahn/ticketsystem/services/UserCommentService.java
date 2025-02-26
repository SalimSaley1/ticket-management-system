package com.hahn.ticketsystem.services;

import com.hahn.ticketsystem.dtos.UserCommentRequestDTO;
import com.hahn.ticketsystem.dtos.UserCommentResponseDTO;
import com.hahn.ticketsystem.entities.UserComment;

public interface UserCommentService {

    UserCommentResponseDTO addComment(UserCommentRequestDTO userCommentRequestDTO, Long ticketId, String email);

    UserCommentResponseDTO userCommentToUserCommentResponseDTO(UserComment userComment);

}
