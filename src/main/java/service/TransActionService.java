package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.PaymentTransActionDto;
import model.UserProfileDto;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TransActionService {
    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static List<PaymentTransActionDto> getTransActions(String userName,String foodName,String method , String status) throws Exception {
        JSONObject body = new JSONObject();
        body.put("user", userName);
        body.put("search", foodName);
        body.put("method", method);
        body.put("status", status);
        String jsonBody =body.toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL +  "/admin/transactions"))
                .header("Authorization","Bearer " + LoginService.getAuthToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200) {
            throw new Exception("Error" + response.statusCode());

        }
        return  gson.fromJson(response.body(), new TypeToken<List<PaymentTransActionDto>>(){}.getType());

    }
}
