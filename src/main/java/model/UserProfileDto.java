package model;

public class UserProfileDto {
    public long id;
    public String full_name;
    public String phone;
    public String email;
    public String role;
    public String address;
    public String profileImageBase64;
    public BankInfo bank_info;

    public static class BankInfo {
        public String bank_name;
        public String account_number;
        public double walletBalance;


        public BankInfo(String bank_name, String account_number, double walletBalance) {
            this.bank_name = bank_name;
            this.account_number = account_number;
            this.walletBalance = walletBalance;
        }
    }

    public UserProfileDto(String full_name, String phone, String email, String role, String address, String profileImageBase64, BankInfo bank_info) {

        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        this.profileImageBase64 = profileImageBase64;
        this.bank_info = bank_info;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImageBase64() {
        return profileImageBase64;
    }

    public void setProfileImageBase64(String profileImageBase64) {
        this.profileImageBase64 = profileImageBase64;
    }

    public BankInfo getBank_info() {
        return bank_info;
    }

    public void setBank_info(BankInfo bank_info) {
        this.bank_info = bank_info;
    }
}
