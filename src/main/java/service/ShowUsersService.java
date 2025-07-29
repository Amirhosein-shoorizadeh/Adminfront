package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.UserProfileDto;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ShowUsersService {

    private static final String BASE_URL = "http://localhost:8080/admin/users";
    private static final String AUTH_TOKEN = LoginService.getAuthToken();
    private final HttpClient client;
    private final ObjectMapper mapper;

    public ShowUsersService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public UserProfileDto[] getUsers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", AUTH_TOKEN)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("Error fetching users: Status code " + response.statusCode());
        }
        return mapper.readValue(response.body(), UserProfileDto[].class);
    }
}