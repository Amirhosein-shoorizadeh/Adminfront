package service;

import com.google.gson.Gson;
import model.LoginDto;
import util.ErrorHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginService {
    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();
    private static String authToken = null;

    public static void login(String phone, String password) throws Exception {
        if (phone == null || password == null || phone.trim().isEmpty() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone and password are required.");
        }

        LoginDto loginDto = new LoginDto(phone, password);
        String jsonBody = gson.toJson(loginDto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            AuthResponse authResponse = gson.fromJson(response.body(), AuthResponse.class);
            authToken = authResponse.getToken();
        } else if (response.statusCode() == 404) {
            throw new Exception("User not found.");
        } else if (response.statusCode() == 401) {
            throw new Exception("Phone or password is incorrect.");
        } else {
            throw new Exception("Login failed: " + ErrorHandler.parseErrorMessage(response.body()));
        }
    }

    private static class AuthResponse {
        private String token;
        private String message;
        private long userId;

        public String getToken() {
            return token;
        }

        public String getMessage() {
            return message;
        }

        public long getUserId() {
            return userId;
        }
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String token) {
        authToken = token;
    }
}