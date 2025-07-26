package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import model.UserProfileDto;
import service.Adminservice;

public class AdminApproval {
    @FXML
    private TableView<UserProfileDto> TabLveiw;
    @FXML
    private TableColumn<UserProfileDto, String> Profile;
    @FXML
    private TableColumn<UserProfileDto, String> NameColumn;
    @FXML
    private TableColumn<UserProfileDto, String> RoleColumn;
    @FXML
    private TableColumn<UserProfileDto, Void> ActionColumn;
    @FXML
    private Button Back;

    public void initialize() {
        // تنظیم ستون‌ها
        Profile.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        RoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // تنظیم کلیک روی نام کاربر
        NameColumn.setCellFactory(col -> new TableCell<>() {
            private final Text text = new Text();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    text.setText(item);
                    text.setStyle("-fx-fill: blue; -fx-underline: true;");
                    setGraphic(text);
                    text.setOnMouseClicked(event -> {
                        UserProfileDto user = getTableRow().getItem();
                        if (user != null) {
                            showUserDetails(user);
                        }
                    });
                }
            }
        });

        // تنظیم ستون اقدام (دکمه Approve)
        ActionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button approveButton = new Button("تأیید");

            {
                approveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                approveButton.setOnAction(event -> {
                    UserProfileDto user = getTableRow().getItem();
                    if (user != null) {
                        try {
//                            Adminservice.approveUser(user.getId());
//                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "کاربر " + user.getFullName() + " با موفقیت تأیید شد.");
//                            alert.showAndWait();
                            // به‌روزرسانی لیست کاربران
                            loadUsers();
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "خطا در تأیید کاربر: " + e.getMessage());
                            alert.showAndWait();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : approveButton);
            }
        });

        // تنظیم دکمه Back
        Back.setOnAction(event -> ((Stage) Back.getScene().getWindow()).close());

        // بارگذاری اولیه کاربران
        loadUsers();
    }

    // بارگذاری کاربران از API
    private void loadUsers() {
        try {
            ObservableList<UserProfileDto> users = Adminservice.getUsers();
            TabLveiw.setItems(users);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "خطا در بارگذاری کاربران: " + e.getMessage());
            alert.showAndWait();
        }
    }

    // نمایش جزئیات کاربر
    private void showUserDetails(UserProfileDto user) {
//        Stage stage = new Stage();
//        VBox vbox = new VBox(10);
//        vbox.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4;");
//
//        vbox.getChildren().addAll(
//                new Text("شناسه: " + user.getId()),
//                new Text("نام کامل: " + user.getFullName()),
//                new Text("تلفن: " + user.getPhone()),
//                new Text("ایمیل: " + user.getEmail()),
//                new Text("نقش: " + user.getRole()),
//                new Text("آدرس: " + user.getAddress()),
//                new Text("تصویر پروفایل (Base64): " + user.getProfileImageBase64()),
//                new Text("نام بانک: " + user.getBankName()),
//                new Text("شماره حساب: " + user.getAccountNumber())
//        );
//
//        Scene scene = new Scene(vbox, 300, 400);
//        stage.setScene(scene);
//        stage.setTitle("جزئیات کاربر: " + user.getFullName());
//        stage.show();
    }
}