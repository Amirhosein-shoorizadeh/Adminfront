package controller;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.LoginDto;
import model.OrderModel;
import model.OrderStatus;
import service.OrderService;

import java.io.IOException;
import java.util.List;

public class AdminOrdersController {
    @FXML private TextField searchField;
    @FXML private TextField vendorField;
    @FXML private TextField courierField;
    @FXML private TextField customerField;
    @FXML private ChoiceBox<String> statusChoiceBox;
    @FXML private Button applyButton;
    @FXML private TableView<OrderModel> ordersTable;
    @FXML private Button backButton;

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @FXML
    private void initialize() {
        for (OrderStatus status : OrderStatus.values()) {
            statusChoiceBox.getItems().add(status.name());
        }


        // Set up table columns
        ordersTable.getColumns().forEach(column -> {
            String property = column.getText().equals("Order ID") ? "id" :
                    column.getText().equals("Buyer Name") ? "buyerName" :
                            column.getText().equals("Vendor Name") ? "vendorName" :
                                    column.getText().equals("Status") ? "status" :
                                            column.getText().equals("Created At") ? "createdAt" :
                                                    "payPrice";
            column.setCellValueFactory(new PropertyValueFactory<>(property));
        });

        // Load initial data
        loadOrders();
    }

    @FXML
    private void applyFilters() {
        loadOrders();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        loadNewScene(event,"/view/Dashboard/DashboardAnaliz.fxml");
        System.out.println("Back button clicked - Implement navigation in your project");
    }

    private void loadOrders() {
        try {
            String search = searchField.getText().trim();
            String vendor = vendorField.getText().trim();
            String courier = courierField.getText().trim();
            String customer = customerField.getText().trim();
            String status = statusChoiceBox.getValue();
            ObservableList<OrderModel> orderList = OrderService.searchOrders(search, vendor, courier, customer, status);
            System.out.println(orderList.size());

            ordersTable.setItems(orderList);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading orders");
        }
    }
    private void loadNewScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600); // اندازه دلخواه، می‌تونی تغییر بدی
        stage.setScene(scene);
        stage.show();
    }
    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("خطا");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showInfo(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("اطلاعات");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}