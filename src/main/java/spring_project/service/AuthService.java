package spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_project.dto.UserDTO;
import spring_project.entity.User;
import spring_project.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDTO signUp(UserDTO registrationRequets) {
        UserDTO resp = new UserDTO();
        try {
            User user = new User();
            user.setEmail(registrationRequets.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequets.getPassword()));
            user.setRole("USER");
            user.setName(registrationRequets.getName());
            user.setNumberphone(registrationRequets.getNumberphone());
            User userResult = userRepository.save(user);
            if (userResult != null) {
                resp.setUser(userResult);
                resp.setMessage("User saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public UserDTO signIn(UserDTO signinRequest) {
        UserDTO response = new UserDTO();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            var user = userRepository.findByEmail(signinRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24H");
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setName(user.getName());
            response.setNumberphone(user.getNumberphone());
            response.setRole(user.getRole());
            response.setMessage("Đăng Nhập Thành Công");
        } catch (UsernameNotFoundException e) {
            response.setStatusCode(404);
            response.setError("User not found");
        } catch (BadCredentialsException e) {
            response.setStatusCode(401);
            response.setError("Invalid credentials");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }

        return response;
    }

    public UserDTO refreshToken(UserDTO refreshTokenRequest) {
        UserDTO response = new UserDTO();
        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        User users = userRepository.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24H");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }

    public void forgotPassword(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Email " + email));
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpirationDate(LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        String resetUrl = "https://ticker-car.greenglobal.com.vn/auth/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(), "Password Reset Request",
                "Click the link to reset your password: " + resetUrl);
    }

    public void resetPassword(String token, String password) throws Exception {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invailed Token"));
        if (user.getTokenExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token has expried");
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setResetToken(null);
        user.setTokenExpirationDate(null);
        userRepository.save(user);
    }

    // signup and singin with google
    public UserDTO googleSignUp(String credential) {
        UserDTO response = new UserDTO();
        try {
            var transport = GoogleNetHttpTransport.newTrustedTransport();
            var jsonFactory = JacksonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections
                            .singletonList("475515681122-86685f4mdvcf1m3okno0imobsvmb09p2.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                if (userRepository.findByEmail(email).isPresent()) {
                    response.setStatusCode(409);
                    response.setError("User with this email already exists. Please sign in.");
                    return response;
                }

                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setRole("USER");
                newUser.setPassword(null); // hoặc mã hóa 1 chuỗi ngẫu nhiên
                User savedUser = userRepository.save(newUser);

                response.setUser(savedUser);
                response.setStatusCode(200);
                response.setMessage("Google Sign Up thành công");
            } else {
                response.setStatusCode(401);
                response.setError("Token Google không hợp lệ");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("Lỗi server: " + e.getMessage());
        }
        return response;
    }

    public UserDTO googleSignIn(String credential) {
        UserDTO response = new UserDTO();
        try {
            var transport = GoogleNetHttpTransport.newTrustedTransport();
            var jsonFactory = JacksonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections
                            .singletonList("475515681122-86685f4mdvcf1m3okno0imobsvmb09p2.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken == null) {
                response.setStatusCode(401);
                response.setError("Token Google không hợp lệ");
                return response;
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                response.setStatusCode(404);
                response.setError("User not found. Please sign up first.");
                return response;
            }

            // Tạo token JWT
            String jwt = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            response.setUser(user);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setStatusCode(200);
            response.setMessage("Google Sign In thành công");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("Lỗi server: " + e.getMessage());
        }
        return response;
    }

}
