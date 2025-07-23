package spring_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_project.dto.ForgotPasswordRequest;
import spring_project.dto.ResetPasswordRequest;
import spring_project.dto.UserDTO;
import spring_project.service.AuthService;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signIn(@RequestBody UserDTO signinRequest) {
        return ResponseEntity.ok(authService.signIn(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserDTO> refreshToken(@RequestBody UserDTO refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            authService.forgotPassword(request.getEmail());
            return ResponseEntity.ok("Password reset link sent to your email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending email.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request , @RequestParam String token) {
        try {
            authService.resetPassword(token , request.getPassword());
            return ResponseEntity.ok("Password successfully reset.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while resetting password.");
        }
    }

    @PostMapping("/google-signup")
    public ResponseEntity<?> googleSignUp(@RequestBody Map<String, String> request) {
        String credential = request.get("credential");
        UserDTO response = authService.googleSignUp(credential);

        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            return ResponseEntity.ok(response); // trả UserDTO khi thành công
        } else if (statusCode == 409) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", response.getError() != null ? response.getError() : "User already exists"));
        } else if (statusCode == 401) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", response.getError() != null ? response.getError() : "Invalid token"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message",
                            response.getError() != null ? response.getError() : "Internal server error"));
        }
    }

    @PostMapping("/google-signin")
    public ResponseEntity<?> googleSignIn(@RequestBody Map<String, String> request) {
        String credential = request.get("credential");
        UserDTO response = authService.googleSignIn(credential);

        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            return ResponseEntity.ok(response);
        } else if (statusCode == 404) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", response.getError() != null ? response.getError() : "User not found"));
        } else if (statusCode == 401) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", response.getError() != null ? response.getError() : "Invalid token"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message",
                            response.getError() != null ? response.getError() : "Internal server error"));
        }
    }

}
