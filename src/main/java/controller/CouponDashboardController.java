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

public class CouponDashboardController {

    @FXML
    private Button Back;

    @FXML
    private Button Craetecoupon;

    @FXML
    private Button EditCoupon;

    @FXML
    private Label labelDashboard;

    @FXML
    private void handleCraetecoupon(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Dashboard/CreateCoupon.fxml"); // چک کن این فایل وجود داره
    }

    @FXML
    private void handleEditCoupon(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Coupon/CouponList.fxml");
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Dashboard/AdminDashboard.fxml");
    }

    private void loadNewScene(ActionEvent event, String fxmlPath) throws IOException {
        // بررسی وجود مسیر
        if (getClass().getResource(fxmlPath) == null) {
            throw new IOException("FXML file not found: " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600); // اندازه دلخواه، می‌تونی تغییر بدی
        stage.setScene(scene);
        stage.show();
    }
}