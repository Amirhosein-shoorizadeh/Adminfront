package controller;

import javafx.scene.control.Button;
import model.CouponDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCouponController {

    @FXML
    private TextField codeField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField valueField;

    @FXML
    private TextField minPriceField;

    @FXML
    private TextField userCountField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField endDateField;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    private CouponDto coupon;

    public void setCoupon(CouponDto coupon) {
        this.coupon = coupon;
        codeField.setText(coupon.getCoupon_code());
        typeField.setText(coupon.getType());
        valueField.setText(coupon.getValue().toString());
        minPriceField.setText(coupon.getMin_price().toString());
        userCountField.setText(coupon.getUser_count().toString());
        startDateField.setText(coupon.getStart_date());
        endDateField.setText(coupon.getEnd_date());
    }

    @FXML
    private void handleSave(ActionEvent event) {
        coupon.setCoupon_code(codeField.getText());
        coupon.setType(typeField.getText());
        coupon.setValue(Double.valueOf(valueField.getText()));
        coupon.setMin_price(Integer.valueOf(minPriceField.getText()));
        coupon.setUser_count(Integer.valueOf(userCountField.getText()));
        coupon.setStart_date(startDateField.getText());
        coupon.setEnd_date(endDateField.getText());

        // اینجا باید درخواست به سرور برای آپدیت بفرستی (نیاز به سرویس جدید داره)
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}