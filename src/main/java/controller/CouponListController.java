package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CouponDto;
import service.CouponService;

import java.io.IOException;
import java.util.List;

public class CouponListController {

    @FXML
    private Button BackButton;

    @FXML
    private ListView<CouponDto> CouponListView;
    List<CouponDto> couponList;


    @FXML
    public void initialize() {
        setUpCouponList();
        loadCoupons();
    }

    @FXML
    void BackAction(ActionEvent event) throws IOException {
        loadNewScene(event,"/view/Dashboard/CouponDashboard.fxml");
    }

    private void setUpCouponList() {
        CouponListView.setCellFactory(listView -> new ListCell<>() {
            private final HBox mainLayout = new HBox(10); // Horizontal box with spacing
            private final VBox leftBox = new VBox(5);
            private final Label couponCodeLabel = new Label();
            private final Label typeLabel = new Label();
            private final Label startDateLabel = new Label();
            private final Label endDateLabel = new Label();
            private final HBox DateBox = new HBox(5);

            private final VBox rightBox = new VBox(5);
            private final Label userCountLabel = new Label();

            private final HBox buttonBox = new HBox(5);
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                mainLayout.getStyleClass().add("my-hbox");
                couponCodeLabel.getStyleClass().add("label-title");
                couponCodeLabel.setStyle("-fx-font-size: 20px;");
                typeLabel.getStyleClass().add("label-title");
                typeLabel.setStyle("-fx-font-size: 15px;");
                startDateLabel.getStyleClass().add("label-title");
                startDateLabel.setStyle("-fx-font-size: 12px;");
                endDateLabel.getStyleClass().add("label-title");
                endDateLabel.setStyle("-fx-font-size: 12px;");
                editButton.getStyleClass().add("button2");
                deleteButton.getStyleClass().add("button2");
                deleteButton.setStyle("-fx-background-radius: 15px;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-background-color:  #f44336;" +
                        "-fx-text-fill: white;");
                editButton.setOnAction(event -> {
                    CouponDto couponDto = getItem();
                    ShowEditCoupon(couponDto,event);
                });
                deleteButton.setOnAction(event -> {
                    CouponDto couponDto = getItem();
                    deleteCoupon(couponDto);
                });
            }

            @Override
            protected void updateItem(CouponDto coupon, boolean empty) {
                super.updateItem(coupon, empty);
                if (empty || coupon == null) {
                    setGraphic(null);
                }else {
                    couponCodeLabel.setText(coupon.coupon_code);
                    typeLabel.setText(coupon.type);
                    startDateLabel.setText(coupon.start_date);
                    endDateLabel.setText(coupon.end_date);
                    userCountLabel.setText(String.valueOf(coupon.user_count));
                    buttonBox.getChildren().addAll(editButton, deleteButton);
                    rightBox.getChildren().clear();
                    rightBox.getChildren().addAll(userCountLabel,buttonBox);
                    DateBox.getChildren().addAll(startDateLabel,endDateLabel);
                    leftBox.getChildren().addAll(couponCodeLabel,typeLabel,DateBox);
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);
                    mainLayout.getChildren().addAll(leftBox,spacer,rightBox);
                    setGraphic(mainLayout);
                }
            }


        });
    }

    private void loadCoupons(){
        try{
            couponList = CouponService.getAllCoupons();
            CouponListView.getItems().clear();
            CouponListView.getItems().setAll(couponList);
        }catch (Exception e){
            showError("Error load coupons" + e.getMessage());
        }

    }

    private void ShowEditCoupon(CouponDto coupon , ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Coupon/EditCoupon.fxml"));
            Parent root = loader.load();


            EditCouponController controller = loader.getController();
            controller.setCoupon(coupon);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle("coupon");
            stage.show();
        } catch (IOException e) {
            showError("Error loading ViewFoodsOfRestaurant: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteCoupon(CouponDto coupon){
        try {
            CouponService.deleteCoupon(coupon.Id);
            couponList.remove(coupon);
            CouponListView.getItems().remove(coupon);
        }catch (Exception e){
            showError("Error deleting coupon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadNewScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600); // اندازه دلخواه، می‌تونی تغییر بدی
        stage.setScene(scene);
        stage.show();
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

}
