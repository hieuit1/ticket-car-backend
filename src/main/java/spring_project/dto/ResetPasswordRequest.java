package spring_project.dto;

public class ResetPasswordRequest {
    private String token;
    private String password; // Đổi từ newPassword thành password

    // Getter và Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}