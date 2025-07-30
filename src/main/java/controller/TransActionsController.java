package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.PaymentTransActionDto;
import service.TransActionService;

import java.io.IOException;
import java.util.List;

public class TransActionsController {

    @FXML
    private Button BackButton;

    @FXML
    private TextField FoodNameTextField;

    @FXML
    private ComboBox<String> MethodComboBox;

    @FXML
    private Button SearchButton;

    @FXML
    private ComboBox<String> StatusComboBox;

    @FXML
    private TableView<PaymentTransActionDto> TransActionsTable;

    @FXML
    private TextField UserNameTextField;

    @FXML
    private TableColumn<PaymentTransActionDto, Long> idColumn;

    @FXML
    private TableColumn<PaymentTransActionDto, Long> orderIdColumn;

    @FXML
    private TableColumn<PaymentTransActionDto, Long> userIdColumn;

    @FXML
    private TableColumn<PaymentTransActionDto, String> methodColumn;

    @FXML
    private TableColumn<PaymentTransActionDto, String> statusColumn;

    @FXML
    private TableColumn<PaymentTransActionDto, String> dateTimeColumn;

    @FXML
    private TableColumn<PaymentTransActionDto,String> subjectColumn;

    @FXML
    private TableColumn<PaymentTransActionDto, Double> amountColumn;

    private final ObservableList<PaymentTransActionDto> transactions = FXCollections.observableArrayList();
    private final TransActionService transActionService = new TransActionService();

    @FXML
    public void initialize() {

        if (TransActionsTable == null || idColumn == null || orderIdColumn == null || userIdColumn == null ||
                methodColumn == null || statusColumn == null || dateTimeColumn == null || amountColumn == null ||
                BackButton == null || SearchButton == null || FoodNameTextField == null ||
                MethodComboBox == null || StatusComboBox == null || UserNameTextField == null) {
            showError("Error in loading transactions table");
            return;
        }


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        methodColumn.setCellValueFactory(new PropertyValueFactory<>("method"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("date_time"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        subjectColumn.setCellValueFactory(cellData -> {
            PaymentTransActionDto transaction = cellData.getValue();
            String subject;
            if (transaction.getOrder_id() == -1) {
                subject = "Recharge Wallet";
            } else {
                subject = "Pay Order :" + transaction.getOrder_id();
            }
            return new SimpleStringProperty(subject);
        });

        idColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.valueOf(item));
            }
        });
        orderIdColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.valueOf(item));
            }
        });
        userIdColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.valueOf(item));
            }
        });
        amountColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("%.0f", item));
            }
        });

        // تنظیم ComboBox‌ها
        MethodComboBox.setItems(FXCollections.observableArrayList("wallet", "online"));
        StatusComboBox.setItems(FXCollections.observableArrayList("SUCCESS", "FAILED"));

        // اتصال لیست Observable به جدول
        TransActionsTable.setItems(transactions);

        // بارگذاری داده‌های اولیه
        refreshTransactions();
    }

    @FXML
    void BackAction(ActionEvent event) {
        try {
            loadNewScene(event, "/view/Dashboard/AdminDashboard.fxml");
        } catch (IOException e) {
            showError("Error :" + e.getMessage());
        }
    }

    @FXML
    void SearchAction(ActionEvent event) {
        new Thread(() -> {
            try {
                String username = UserNameTextField.getText().trim();
                String foodName = FoodNameTextField.getText().trim();
                String method = MethodComboBox.getValue();
                String status = StatusComboBox.getValue();

                List<PaymentTransActionDto> filteredTransactions = TransActionService.getTransActions(
                         username,
                         foodName,
                        method,
                        status
                );

                Platform.runLater(() -> {
                    transactions.clear();
                    transactions.addAll(filteredTransactions);
                    if (filteredTransactions.isEmpty()) {
                        showInfo("not found transaction");
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Error in Search " + e.getMessage()));
            }
        }).start();
    }

    private void refreshTransactions() {
        new Thread(() -> {
            try {
                String username = UserNameTextField.getText().trim();
                String foodName = FoodNameTextField.getText().trim();
                String method = MethodComboBox.getValue();
                String status = StatusComboBox.getValue();
                List<PaymentTransActionDto> TransactionsList = TransActionService.getTransActions(username, foodName, method, status);
                Platform.runLater(() -> {
                    transactions.clear();
                    transactions.addAll(TransactionsList);
                    if (TransactionsList.isEmpty()) {
                        showInfo("not found transactions");
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Error load TransActions" + e.getMessage()));
            }
        }).start();
    }

    private void loadNewScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("خطا");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showInfo(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("اطلاعات");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
