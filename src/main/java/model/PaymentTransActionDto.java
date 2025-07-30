package model;

public class PaymentTransActionDto {
    private long id;
    private long order_id;
    private long user_id;
    private String method;
    private String status;
    private String date_time;
    private double amount;

    public PaymentTransActionDto(long id, long order_id, long user_id, String method, String status, String date_time, double amount) {
        this.id = id;
        this.order_id = order_id;
        this.user_id = user_id;
        this.method = method;
        this.status = status;
        this.date_time = date_time;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
