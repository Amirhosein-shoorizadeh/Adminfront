package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.CouponDto;
import service.CouponService;

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

    public void onBackClicked(ActionEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close(); // یا اگر صفحه اصلی دارید به اون برگردید
    }
}
