package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.UserProfileDto;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ShowUsersService {

    private static final String BASE_URL = "http://localhost:8080/admin/allusers";
    private static final String AUTH_TOKEN = LoginService.getAuthToken();
    private final HttpClient client;
    private final ObjectMapper mapper;
    Gson gson = new Gson();

    public ShowUsersService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public List<UserProfileDto> getUsers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization","Bearer " +LoginService.getAuthToken())
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("Error fetching users: Status code " + response.statusCode());
        }
        return  gson.fromJson(response.body(), new TypeToken<List<UserProfileDto>>(){}.getType());
    }
}