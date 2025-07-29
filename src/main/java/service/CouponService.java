package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.CouponDto;
import util.ErrorHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CouponService {
    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static List<CouponDto> getAllCoupons() throws Exception {
        // دریافت توکن از LoginService
        String authToken = LoginService.getAuthToken();
        if (authToken == null || authToken.isEmpty()) {
            throw new Exception("توکن احراز هویت در دسترس نیست.");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/coupons"))
                .header("Content-Type", "application/json")
                // اضافه کردن توکن به هدر Authorization
                .header("Authorization", "Bearer " + authToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            List<CouponDto> coupons = gson.fromJson(response.body(), new TypeToken<List<CouponDto>>(){}.getType());
            return coupons;
        } else {
            throw new Exception("error" + ErrorHandler.parseErrorMessage(response.body()));
        }
    }

    public static void deleteCoupon(long id) throws Exception {
        // دریافت توکن از LoginService
        String authToken = LoginService.getAuthToken();
        if (authToken == null || authToken.isEmpty()) {
            throw new Exception("توکن احراز هویت در دسترس نیست.");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/coupons/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("خطا در حذف کوپن: " + ErrorHandler.parseErrorMessage(response.body()));
        }
    }

    public static void createCoupon(CouponDto dto) throws Exception {
        // دریافت توکن از LoginService
        String authToken = LoginService.getAuthToken();
        if (authToken == null || authToken.isEmpty()) {
            throw new Exception("توکن احراز هویت در دسترس نیست.");
        }

        String requestBody = gson.toJson(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/coupons"))
                .header("Content-Type", "application/json")
                // اضافه کردن توکن به هدر Authorization
                .header("Authorization", "Bearer " + authToken)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            throw new Exception("خطا در ایجاد کوپن: " + ErrorHandler.parseErrorMessage(response.body()));
        }
    }

    public static void updateCoupon(long id, CouponDto dto) throws Exception {
        String authToken = LoginService.getAuthToken();
        if (authToken == null || authToken.isEmpty()) {
            throw new Exception("Invalid token");
        }
        String requestBody = gson.toJson(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/coupons/" + dto.getId()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("Error" + ErrorHandler.parseErrorMessage(response.body()));
        }
    }
}