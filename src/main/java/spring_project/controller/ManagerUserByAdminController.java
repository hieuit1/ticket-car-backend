package spring_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_project.dto.UserDTO;
import spring_project.entity.User;
import spring_project.service.ManageUserByAdminService;

import java.util.List;

@RestController
@RequestMapping("/api/manager-user") // Toàn bộ API này dùng cho TOKEN ADMIN
public class ManagerUserByAdminController {

    @Autowired
    private ManageUserByAdminService manageUserByAdminService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = manageUserByAdminService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userById = manageUserByAdminService.getUserById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        boolean updateUser = manageUserByAdminService.updateUserById(id, user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        boolean deleteUser = manageUserByAdminService.deleteUserById(id);
        return new ResponseEntity<>("Delete User Successfully", HttpStatus.OK);
    }
}
