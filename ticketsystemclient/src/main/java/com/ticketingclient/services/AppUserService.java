package com.ticketingclient.services;

import com.ticketingclient.enums.Status;
import com.ticketingclient.models.AppUserRequestModel;
import com.ticketingclient.models.AppUserResponseModel;
import com.ticketingclient.models.TicketRequestModel;
import com.ticketingclient.models.TicketResponseModel;
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
public class AppUserService {

    private static final String BASE_URL = "http://localhost:8089/";
    private final RestTemplate restTemplate;

    public AppUserService() {
        this.restTemplate = new RestTemplate();
    }


    public AppUserResponseModel register(AppUserRequestModel userRequestModel) {
        String url = BASE_URL + "users";
        HttpEntity<AppUserRequestModel> entity = new HttpEntity<>(userRequestModel);
        ResponseEntity<AppUserResponseModel> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, AppUserResponseModel.class);

        return response.getBody();
    }

    private HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

}
