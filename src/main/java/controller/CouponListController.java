package controller;

import javafx.scene.layout.VBox;
import model.CouponDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.CouponService;

import java.io.IOException;
import java.util.Optional;

public class CouponListController {

    @FXML
    private TableView<CouponDto> couponTable;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    private ObservableList<CouponDto> couponList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // تنظیم ستون‌ها
        TableColumn<CouponDto, Long> idColumn = (TableColumn<CouponDto, Long>) couponTable.getColumns().get(0);
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleLongProperty(cellData.getValue().getId()).asObject());

        TableColumn<CouponDto, String> codeColumn = (TableColumn<CouponDto, String>) couponTable.getColumns().get(1);
        codeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCoupon_code()));

        // ستون Actions برای دکمه‌ها
        TableColumn<CouponDto, HBox> actionsColumn = (TableColumn<CouponDto, HBox>) couponTable.getColumns().get(2);
        actionsColumn.setCellFactory(column -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                HBox box = new HBox(5, editButton, deleteButton);
                setGraphic(box);

                editButton.setOnAction(event -> {
                    CouponDto coupon = getTableView().getItems().get(getIndex());
                    showEditPage(coupon);
                });

                deleteButton.setOnAction(event -> {
                    CouponDto coupon = getTableView().getItems().get(getIndex());
                    showDeleteConfirmation(coupon);
                });
            }

            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(getGraphic());
                }
            }
        });

        // کلیک روی ستون coupon_code برای نمایش جزئیات
        couponTable.setRowFactory(tv -> {
            TableRow<CouponDto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CouponDto coupon = row.getItem();
                    showDetailsPopup(coupon);
                }
            });
            return row;
        });

        // لود اولیه داده‌ها
        refreshTable();
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        refreshTable();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        loadNewScene(event, "/view/Dashboard/CouponDashboard.fxml");
    }

    private void refreshTable() {
        try {
            couponList.setAll(CouponService.getAllCoupons());
            couponTable.setItems(couponList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load coupons: " + e.getMessage());
        }
    }

    private void showEditPage(CouponDto coupon) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Coupon/EditCoupon.fxml"));
            Parent root = loader.load();

            EditCouponController controller = loader.getController();
            controller.setCoupon(coupon);

            Stage stage = new Stage();
            stage.setTitle("Edit Coupon");
            stage.setScene(new Scene(root, 400, 300));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            showAlert("Error", "Failed to load edit page: " + e.getMessage());
        }
    }

    private void showDeleteConfirmation(CouponDto coupon) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Coupon");
        alert.setHeaderText("Are you sure you want to delete coupon " + coupon.getCoupon_code() + "?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                CouponService.deleteCoupon(coupon.getId());
                couponList.remove(coupon);
            } catch (Exception e) {
                showAlert("Error", "Failed to delete coupon: " + e.getMessage());
            }
        }
    }

    private void showDetailsPopup(CouponDto coupon) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Coupon Details");
        dialog.setHeaderText("Details for Coupon: " + coupon.getCoupon_code());
        dialog.initModality(Modality.APPLICATION_MODAL);

        Label details = new Label(String.format(
                "ID: %d\nCode: %s\nType: %s\nValue: %.2f\nMin Price: %d\nUser Count: %d\nStart Date: %s\nEnd Date: %s",
                coupon.getId(), coupon.getCoupon_code(), coupon.getType(), coupon.getValue(),
                coupon.getMin_price(), coupon.getUser_count(), coupon.getStart_date(), coupon.getEnd_date()
        ));

        dialog.getDialogPane().setContent(new VBox(10, details));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadNewScene(ActionEvent event, String fxmlPath) throws IOException {
        if (getClass().getResource(fxmlPath) == null) {
            throw new IOException("FXML file not found: " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}