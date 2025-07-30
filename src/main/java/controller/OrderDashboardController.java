package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderDashboardController {

    @FXML
    private Button back;
    @FXML
    private Button ordersList;
    @FXML
    private Button transiction;
    @FXML
    private Label ordersManagment;

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Dashboard/AdminDashboard.fxml");
    }

    @FXML
    private void handleOrdersList(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Transiction/admin_orders.fxml");
    }

    @FXML
    private void handleTransiction(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Transaction/Transactions.fxml");
    }

    private void loadNewScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}