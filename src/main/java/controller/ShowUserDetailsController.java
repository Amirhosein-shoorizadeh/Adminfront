package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.UserProfileDto;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ShowUserDetailsController {

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label bankNameLabel;

    @FXML
    private Label accountNumberLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label imageLabel;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Button backButton;

    private UserProfileDto user;
    private Stage stage;

    public void setUser(UserProfileDto user) {
        this.user = user;
        updateUI();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // Handlers are set via FXML onAction attributes
    }

    @FXML
    public void handleBack() {
        stage.close();
    }

    private void updateUI() {
        idLabel.setText("ID: " + user.getId());
        nameLabel.setText("Full Name: " + user.getFull_name());
        phoneLabel.setText("Phone: " + user.getPhone());
        emailLabel.setText("Email: " + user.getEmail());
        roleLabel.setText("Role: " + user.getRole());
        addressLabel.setText("Address: " + user.getAddress());
        bankNameLabel.setText("Bank Name: " + user.getBank_info().bank_name);
        accountNumberLabel.setText("Account Number: " + user.getBank_info().account_number);
        balanceLabel.setText("Wallet Balance: $" + String.format("%,.2f", user.getBank_info().walletBalance));

        if (user.getProfileImageBase64() != null && !user.getProfileImageBase64().isEmpty()) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(user.getProfileImageBase64());
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                profileImageView.setImage(image);
                imageLabel.setText("Profile Image:");
            } catch (IllegalArgumentException e) {
                profileImageView.setImage(null);
                imageLabel.setText("Profile Image: (Error loading image)");
            }
        } else {
            profileImageView.setImage(null);
            imageLabel.setText("Profile Image: (No image)");
        }
    }
}