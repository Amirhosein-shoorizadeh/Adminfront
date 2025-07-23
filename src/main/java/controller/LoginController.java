package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField PhoneTextField;

    @FXML
    private TextField PasswordTextField1;

    @FXML
    private Label SignUpLabel;

    @FXML
    public void initialize() {
        // اختیاری: برای تنظیمات اولیه
    }

    @FXML
    public void LoginAction(ActionEvent event) {
        String phone = PhoneTextField.getText();
        String password = PasswordTextField1.getText();

        // فعلا فقط چاپ می‌کنیم
        System.out.println("Phone: " + phone);
        System.out.println("Password: " + password);

        // اینجا می‌تونی لاجیک ورود یا چک با دیتابیس رو اضافه کنی
    }

    @FXML
    public void goToSignUp() {
        System.out.println("Going to signup page...");
        // اینجا می‌تونی صفحه signup رو لود کنی
    }
}
