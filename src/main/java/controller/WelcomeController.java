package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class WelcomeController {
    @FXML
    private Button loginButton; // استفاده از private برای بهتر بودن استاندارد

    public void onLoginClick(ActionEvent event) throws IOException {
        // لود کردن فایل Login.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Auth/Login.fxml"));
        Parent root = loader.load();

        // دریافت Stage فعلی
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // تنظیم Scene جدید
        Scene scene = new Scene(root, 800, 600); // اندازه دلخواه، می‌تونی تغییر بدی
        stage.setScene(scene);
        stage.show();
    }
}