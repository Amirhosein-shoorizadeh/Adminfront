package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.UserProfileDto;
import service.ApproveService;

import java.io.IOException;

public class UserListController {

    @FXML
    private TableView<UserProfileDto> userTable;

    @FXML
    private TableColumn<UserProfileDto, Long> idColumn;

    @FXML
    private TableColumn<UserProfileDto, String> nameColumn;

    @FXML
    private TableColumn<UserProfileDto, String> phoneColumn;

    @FXML
    private TableColumn<UserProfileDto, String> roleColumn;

    @FXML
    private TableColumn<UserProfileDto, Void> actionColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    private final ApproveService approveService = new ApproveService();
    private final ObservableList<UserProfileDto> users = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Actions column with Details button
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button detailsButton = new Button("Details");

            {
                detailsButton.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-cursor: hand;");
                detailsButton.setOnAction(event -> {
                    UserProfileDto user = getTableView().getItems().get(getIndex());
                    showDetailsWindow(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : detailsButton);
            }
        });

        userTable.setItems(users);

        // Load initial data
        refreshUsers();

        // Refresh button logic
        refreshButton.setOnAction(event -> refreshUsers());

        // Back button logic (closes stage; adjust based on your app)
        backButton.setOnAction(event -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });
    }

    private void refreshUsers() {
        try {
            UserProfileDto[] fetchedUsers = approveService.getUsers();
            users.clear();
            users.addAll(fetchedUsers);
            System.out.println("User list refreshed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error refreshing users: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void showDetailsWindow(UserProfileDto user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/model/UserDetails.fxml"));
            Stage detailsStage = new Stage();
            detailsStage.setTitle("User Details");
            detailsStage.setScene(new Scene(loader.load(), 400, 500));

            UserDetailsController controller = loader.getController();
            controller.setUser(user);
            controller.setStage(detailsStage);
            controller.setApproveService(approveService);

            detailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading details page!");
            alert.showAndWait();
        }
    }
}