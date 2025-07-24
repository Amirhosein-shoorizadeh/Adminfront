package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.LoginService;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField PhoneTextField;

    @FXML
    private TextField PasswordTextField1;

    @FXML
    private Button LoginButton;

    @FXML
    public void initialize() {
        // می‌تونی اینجا کدهای اولیه مثل تنظیمات پیش‌فرض رو بذاری
    }

    @FXML
    public void LoginAction(ActionEvent event) throws IOException {
        String phone = PhoneTextField.getText();
        String password = PasswordTextField1.getText();

        if (phone.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill all fields.");
            return;
        }

        try {
//            LoginService.login(phone, password);
//            System.out.println("Logged in successfully.");

            // لود کردن فایل AdminDashboard.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard/AdminDashboard.fxml"));
            Parent root = loader.load();

            // دریافت Stage فعلی
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // تنظیم Scene جدید
            Scene scene = new Scene(root, 800, 600); // اندازه دلخواه، می‌تونی تغییر بدی
            stage.setScene(scene);
            stage.show();
            System.out.println("Redirecting to dashboard...");

        } catch (Exception e) {
            Platform.runLater(() -> {
                showAlert("Login Error", e.getMessage());
            });
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}