package service;


import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.OrderModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OrderService {
    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();


    public static ObservableList<OrderModel> searchOrders(String search, String vendor, String courier, String customer, String status) throws Exception {
        JSONObject filters = new JSONObject();
        if (search != null && !search.isEmpty()) filters.put("search", search);
        if (vendor != null && !vendor.isEmpty()) filters.put("vendor", vendor);
        if (courier != null && !courier.isEmpty()) filters.put("courier", courier);
        if (customer != null && !customer.isEmpty()) filters.put("customer", customer);
        if (status != null && !status.equals("همه")) filters.put("status", status);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/orders"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + LoginService.getAuthToken())
                .POST(HttpRequest.BodyPublishers.ofString(filters.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("Error: HTTP " + response.statusCode());
        }

        JSONArray ordersArray = new JSONArray(response.body());
        System.out.println(ordersArray.length());
        ObservableList<OrderModel> orderList = FXCollections.observableArrayList();
        for (int i = 0; i < ordersArray.length(); i++) {
            JSONObject orderJson = ordersArray.getJSONObject(i);
            OrderModel order = new OrderModel();
            order.setId(orderJson.optString("id"));
            order.setBuyerName(orderJson.optString("buyerName"));
            order.setVendorName(orderJson.optString("vendorName"));
            order.setStatus(orderJson.optString("status"));
            order.setCreatedAt(orderJson.optString("created_at"));
            order.setPayPrice(orderJson.optString("pay_price"));
            orderList.add(order);
        }
        return orderList;
    }
}