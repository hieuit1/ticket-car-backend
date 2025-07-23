package spring_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring_project.dto.DriverRequets;
import spring_project.entity.Driver;
import spring_project.service.DriverService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @GetMapping("/api/useradmin-all-driver") // Cần TOKEN ADMIN và USER để có thể xem
    public ResponseEntity<List<DriverRequets>> getAllDrivers() {
        List<DriverRequets> allDrivers = driverService.getListDrivers();
        return new ResponseEntity<>(allDrivers, HttpStatus.OK);
    }

    @GetMapping("/api/useradmin-all-driver/{driverId}") // Cần TOKEN ADMIN và USER để có thể xem
    public ResponseEntity<DriverRequets> getDriverById(@PathVariable Long driverId) {
        DriverRequets driverById = driverService.getDriverById(driverId);
        return new ResponseEntity<>(driverById, HttpStatus.OK);
    }

    @PostMapping("/api/api-driver/create-driver") // Chỉ có TOKEN ADMIN mới có thể tạo
    public ResponseEntity<Map<String,Object>> createFood(
            @RequestParam(value = "image" , required = false) MultipartFile file,
            @RequestParam(value = "fullName" , required = false) String fullName,
            @RequestParam(value = "phoneNumber" , required = false) Long phoneNumber,
            @RequestParam(value = "yearOfBirth" , required = false) Long yearOfBirth,
            @RequestParam(value = "descriptions" , required = false) String descriptions,
            @RequestParam(value = "gender" , required = false) String gender) {
        try {
            DriverRequets driverRequets = new DriverRequets( fullName , phoneNumber , yearOfBirth , descriptions , gender);
            Map<String , Object> dataDrivers = driverService.createDriver(file , driverRequets);
            return new ResponseEntity<>(dataDrivers, HttpStatus.OK);
        }  catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/api-driver/update-driver/{driverId}") // chỉ có TOKEN ADMIN mới có thể cập nhật
    public ResponseEntity<Map<String,Object>> updateDriver(
            @PathVariable Long driverId,
            @RequestParam(value = "image" , required = false) MultipartFile file,
            @RequestParam(value = "fullName" , required = false) String fullName,
            @RequestParam(value = "phoneNumber" , required = false) Long phoneNumber,
            @RequestParam(value = "yearOfBirth" , required = false) Long yearOfBirth,
            @RequestParam(value = "descriptions" , required = false) String descriptions,
            @RequestParam(value = "gender" , required = false) String gender) {
        try {
            DriverRequets driverRequets = new DriverRequets( fullName , phoneNumber , yearOfBirth , descriptions , gender);
            driverService.updateDriver(driverId , file , driverRequets);
            return ResponseEntity.ok(Map.of("message", "Driver updated successfully"));
        }  catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update image: " + e.getMessage()));
        }
    }

    @DeleteMapping("/api/api-driver/delete-driver/{driverId}") // Chỉ có token ADMIN mới có thể xoá
    public ResponseEntity<Map<String,Object>> deleteDriver(@PathVariable Long driverId) {
        try {
            driverService.deleteDriver(driverId);
            return new ResponseEntity<>(Map.of("message", "Driver deleted successfully"), HttpStatus.OK);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
