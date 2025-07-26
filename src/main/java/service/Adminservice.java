package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.UserProfileDto;
import util.ErrorHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Adminservice {
    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    // دریافت لیست کاربران
    public static ObservableList<UserProfileDto> getUsers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users")) // فرض بر وجود این endpoint
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + LoginService.getAuthToken())
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            List<UserProfileDto> users = gson.fromJson(response.body(), new TypeToken<List<UserProfileDto>>(){}.getType());
            return FXCollections.observableArrayList(users);
        }
        throw new Exception("خطا در دریافت کاربران: " + ErrorHandler.parseErrorMessage(response.body()));
    }

    // تأیید کاربر
    public static void approveUser(String userId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/approve/" + userId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + LoginService.getAuthToken())
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("خطا در تأیید کاربر: " + ErrorHandler.parseErrorMessage(response.body()));
        }
    }
}