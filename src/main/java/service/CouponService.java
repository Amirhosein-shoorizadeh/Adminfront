package service;

import com.google.gson.Gson;
import model.CouponDto;

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
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/coupons")) // فرض endpoint برای گرفتن همه کوپن‌ها
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), List.class); // نیاز به تنظیم دقیق‌تر نوع
        } else {
            throw new Exception("Failed to fetch coupons: " + response.body());
        }
    }

    public static void deleteCoupon(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/coupons/" + id))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("Failed to delete coupon: " + response.body());
        }
    }
}