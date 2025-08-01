package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.CouponDto;
import service.CouponService;

import java.io.IOException;
import java.time.LocalDate;

public class CreateCouponController {

    @FXML private TextField codeField;
    @FXML private TextField typeField;
    @FXML private TextField valueField;
    @FXML private TextField minPriceField;
    @FXML private TextField userCountField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Button createButton;
    @FXML private Button backButton;

    public void onCreateClicked(ActionEvent event) {
        try {
            CouponDto dto = new CouponDto();
            dto.setCoupon_code(codeField.getText());
            dto.setType(typeField.getText());
            dto.setValue(Double.parseDouble(valueField.getText()));
            dto.setMin_price(Integer.parseInt(minPriceField.getText()));
            dto.setUser_count(Integer.parseInt(userCountField.getText()));
            dto.setStart_date(startDatePicker.getValue().toString());
            dto.setEnd_date(endDatePicker.getValue().toString());

            CouponService.createCoupon(dto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Coupon created successfully!");
            alert.showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    public void onBackClicked(ActionEvent event) throws IOException {
        loadNewScene(event,"/view/Dashboard/CouponDashboard.fxml");
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
