package com.ticketingclient.services;

import com.ticketingclient.enums.Role;
import com.ticketingclient.enums.Status;
import com.ticketingclient.models.*;
import com.ticketingclient.util.TokenManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TicketService {

    private static final String BASE_URL = "http://localhost:8089/";
    private final RestTemplate restTemplate;

    public TicketService() {
        this.restTemplate = new RestTemplate();
    }

    public AuthResponse login(AppUserRequestModel userRequestModel) {
        String url = BASE_URL + "auth/login";

        HttpEntity<AppUserRequestModel> entity = new HttpEntity<>(userRequestModel);

        ResponseEntity<AuthResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, AuthResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            TokenManager.setToken(response.getBody().getToken());
        }



        return response.getBody();

    }


    public AuthResponse register(AppUserRequestModel userRequestModel) {
        String url = BASE_URL + "auth/register";
        HttpEntity<AppUserRequestModel> entity = new HttpEntity<>(userRequestModel);
        ResponseEntity<AuthResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, AuthResponse.class);

        return response.getBody();
    }

    public List<TicketResponseModel> getTickets(String token) {
        String url = BASE_URL + "tickets";
        HttpHeaders headers = createAuthHeaders(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<TicketResponseModel>> response = restTemplate.exchange(
                url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<TicketResponseModel>>() {});

        return response.getBody();
    }

    public TicketResponseModel getTicket(String token, Long id) {
        String url = BASE_URL + "tickets/" + id;
        HttpHeaders headers = createAuthHeaders(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<TicketResponseModel> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, TicketResponseModel.class);

        return response.getBody();
    }

    public TicketResponseModel createTicket(String token, TicketRequestModel ticket) {
        String url = BASE_URL + "tickets";
        HttpHeaders headers = createAuthHeaders(token);
        HttpEntity<TicketRequestModel> entity = new HttpEntity<>(ticket, headers);

        ResponseEntity<TicketResponseModel> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, TicketResponseModel.class);

        return response.getBody();
    }



    // Méthode updateTicketStatus détaillée
    public TicketResponseModel updateTicketStatus(String token, Long ticketId, Status newStatus) {
        String url = BASE_URL + "tickets/" + ticketId ;

        // Création des en-têtes avec le token d'authentification
        HttpHeaders headers = createAuthHeaders(token);

        // Création de l'entité HTTP avec le nouveau statut et les en-têtes
        HttpEntity<Status> entity = new HttpEntity<>(newStatus, headers);

        // Exécution de la requête PUT
        ResponseEntity<TicketResponseModel> response = restTemplate.exchange(
                url, HttpMethod.PUT, entity, TicketResponseModel.class);

        // Retourne le ticket mis à jour
        return response.getBody();
    }

    public UserCommentResponseModel addComment(String token, Long ticketId, UserCommentRequestModel comment) {
        String url = BASE_URL + "tickets/" + ticketId + "/comment";
        HttpHeaders headers = createAuthHeaders(token);
        HttpEntity<UserCommentRequestModel> entity = new HttpEntity<>(comment, headers);

        ResponseEntity<UserCommentResponseModel> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, UserCommentResponseModel.class);

        return response.getBody();
    }



    private HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }


}
