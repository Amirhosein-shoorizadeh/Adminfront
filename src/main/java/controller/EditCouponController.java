package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import model.CouponDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CouponService;

import java.io.IOException;

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
        try{
            coupon.setCoupon_code(codeField.getText());
            coupon.setType(typeField.getText());
            coupon.setValue(Double.valueOf(valueField.getText()));
            coupon.setMin_price(Integer.valueOf(minPriceField.getText()));
            coupon.setUser_count(Integer.valueOf(userCountField.getText()));
            coupon.setStart_date(startDateField.getText());
            coupon.setEnd_date(endDateField.getText());
            System.out.println(coupon.Id);
            CouponService.updateCoupon(coupon.Id,coupon);
        }catch(Exception e){
            showError("Error in updating coupon" + e.getMessage());
        }

    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
      loadNewScene(event,"/view/Coupon/CouponList.fxml");
    }
    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
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