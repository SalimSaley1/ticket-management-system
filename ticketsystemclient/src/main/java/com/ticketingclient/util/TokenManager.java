package com.ticketingclient.util;


import lombok.Getter;

public class TokenManager {

    @Getter
    private static String token;

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static void clearToken() {
        token = null;
    }

}
