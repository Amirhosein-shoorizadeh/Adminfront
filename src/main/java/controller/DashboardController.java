package controller;

import javafx.application.Platform;
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

public class DashboardController {

    @FXML
    private Button Coupon;

    @FXML
    private Button Financial_Analysis;

    @FXML
    private Button Dashboardtext;

    @FXML
    private Button Logout;

    @FXML
    private Label labelDashboard;

    @FXML
    private void handleCoupon(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Dashboard/CouponDashboard.fxml");
    }

    @FXML
    private void handleFinancialAnalysis(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Dashboard/FinancialAnalysisDashboard.fxml"); // فرض شده یه صفحه جدید
    }

    @FXML
    private void handleDashboardtext(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Dashboard/ApprovalDashboard.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        // بستن کامل برنامه
        Platform.exit();
    }

    private void loadNewScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600); // اندازه دلخواه، می‌تونی تغییر بدی
        stage.setScene(scene);
        stage.show();
    }
}