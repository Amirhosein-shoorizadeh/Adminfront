package model;

public class LoginDto {
    private String phone;
    private String password;

    public LoginDto(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}