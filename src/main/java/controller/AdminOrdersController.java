package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderModel;
import model.OrderStatus;
import service.OrderService;

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
        // Initialize ChoiceBox
        statusChoiceBox.getItems().add("All");
        for (OrderStatus status : OrderStatus.values()) {
            statusChoiceBox.getItems().add(status.name());
        }
        statusChoiceBox.setValue("All");

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
    private void goBack() {
        // Replace with your navigation logic
        System.out.println("Back button clicked - Implement navigation in your project");
    }

    private void loadOrders() {
        try {
            String search = searchField.getText().trim();
            String vendor = vendorField.getText().trim();
            String courier = courierField.getText().trim();
            String customer = customerField.getText().trim();
            String status = statusChoiceBox.getValue();

            ordersTable.setItems(orderService.searchOrders(search, vendor, courier, customer, status));
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally show an alert to the user
        }
    }
}