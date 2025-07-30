package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderModel {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty buyerName = new SimpleStringProperty();
    private final StringProperty vendorName = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty createdAt = new SimpleStringProperty();
    private final StringProperty payPrice = new SimpleStringProperty();

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty buyerNameProperty() {
        return buyerName;
    }

    public StringProperty vendorNameProperty() {
        return vendorName;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty createdAtProperty() {
        return createdAt;
    }

    public StringProperty payPriceProperty() {
        return payPrice;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setBuyerName(String buyerName) {
        this.buyerName.set(buyerName);
    }

    public void setVendorName(String vendorName) {
        this.vendorName.set(vendorName);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt.set(createdAt);
    }

    public void setPayPrice(String payPrice) {
        this.payPrice.set(payPrice);
    }
}