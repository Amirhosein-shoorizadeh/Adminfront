package controller;

import model.UserProfileDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.ApproveService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class UserListController {

    @FXML
    private ListView<UserProfileDto> userListView;

    @FXML
    private Button backButton;

    @FXML
    private Button backButtonList;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label bankInfoLabel;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    private Stage primaryStage;
    private Scene listScene;
    private Scene detailScene;
    private final ApproveService apiService;

    public UserListController() {
        this.apiService = new ApproveService();
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void initialize() {
        // تنظیم ListView برای نمایش فقط نام و نقش
        userListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(UserProfileDto user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.full_name + " (" + user.role + ")");
            }
        });

        // بارگذاری لیست کاربران
        loadUsers();

        // هندل کردن کلیک روی کاربر
        userListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showUserDetails(newSelection);
            }
        });
    }

    private void loadUsers() {
        try {
            UserProfileDto[] users = apiService.getUsers();
            ObservableList<UserProfileDto> userList = FXCollections.observableArrayList(users);
            userListView.setItems(userList);
        } catch (Exception e) {
            showErrorAlert("خطا در بارگذاری کاربران: " + e.getMessage());
        }
    }

    private void showUserDetails(UserProfileDto user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserDetail.fxml"));
            loader.setController(this);
            Parent detailRoot = loader.load();

            fullNameLabel.setText("نام کامل: " + user.full_name);
            phoneLabel.setText("تلفن: " + (user.phone != null ? user.phone : "نامشخص"));
            emailLabel.setText("ایمیل: " + (user.email != null ? user.email : "نامشخص"));
            roleLabel.setText("نقش: " + (user.role != null ? user.role : "نامشخص"));
            addressLabel.setText("آدرس: " + (user.address != null ? user.address : "نامشخص"));
            bankInfoLabel.setText("اطلاعات بانکی: " + (user.bank_info != null ? user.bank_info.toString() : "نامشخص"));

            if (user.profileImageBase64 != null && !user.profileImageBase64.isEmpty()) {
                byte[] imageBytes = Base64.getDecoder().decode(user.profileImageBase64);
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                profileImageView.setImage(image);
            } else {
                profileImageView.setImage(null);
            }

            approveButton.setUserData(user.getId());
            rejectButton.setUserData(user.getId());

            detailScene = new Scene(detailRoot, 800, 600);
            primaryStage.setScene(detailScene);
        } catch (IOException e) {
            showErrorAlert("خطا در نمایش جزئیات کاربر: " + e.getMessage());
        }
    }

    @FXML
    private void handleApproveAction() {
        Long userId = (Long) approveButton.getUserData();
        try {
            apiService.approveUser(userId);
            showSuccessAlert("کاربر با موفقیت تأیید شد.");
            handleBackAction();
        } catch (Exception e) {
            showErrorAlert("خطا در تأیید کاربر: " + e.getMessage());
        }
    }

    @FXML
    private void handleRejectAction() {
        Long userId = (Long) rejectButton.getUserData();
        try {
            apiService.rejectUser(userId);
            showSuccessAlert("کاربر با موفقیت رد شد.");
            handleBackAction();
        } catch (Exception e) {
            showErrorAlert("خطا در رد کاربر: " + e.getMessage());
        }
    }

    @FXML
    private void handleBackAction() {
        if (listScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserList.fxml"));
                loader.setController(this);
                Parent listRoot = loader.load();
                listScene = new Scene(listRoot, 800, 600);
                loadUsers();
            } catch (IOException e) {
                showErrorAlert("خطا در بازگشت به لیست کاربران: " + e.getMessage());
            }
        }
        primaryStage.setScene(listScene);
    }

    @FXML
    private void HandleBackAction(ActionEvent event) {
        try {
            loadNewScene(event, "/view/Dashboard/AdminDashboard.fxml");
        } catch (IOException e) {
            showErrorAlert("خطا در بارگذاری داشبورد: " + e.getMessage());
        }
    }

    private void loadNewScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطا");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}