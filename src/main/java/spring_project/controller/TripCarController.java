package spring_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_project.dto.TripCarRequets;
import spring_project.entity.TripCar;
import spring_project.service.TripCarService;
import spring_project.service.EmailService;
import spring_project.dto.PaymentRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
public class TripCarController {

    @Autowired
    private TripCarService tripCarService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/api/useradmin-all-tripcar") // Có thể xem được thông tin các chuyến mà không cần token
    public ResponseEntity<List<TripCarRequets>> getAllTripCar() {
        List<TripCarRequets> listTripCars = tripCarService.getAllTripCars();
        return new ResponseEntity<>(listTripCars, HttpStatus.OK);
    }

    @GetMapping("/api/useradmin-all-tripcar/{tripCarId}") // Có thể xem được thông tin các chuyến mà không cần token
    public ResponseEntity<TripCarRequets> getTripCarById(@PathVariable Long tripCarId) {
        TripCarRequets tripCarById = tripCarService.getTripCarById(tripCarId);
        return new ResponseEntity<>(tripCarById, HttpStatus.OK);
    }

    @PostMapping("/api/api-tripcar/create-tripcar") // Chỉ có token admin mới có thể tạo được chuyến xe
    public ResponseEntity<TripCarRequets> createTripCarByAdmin(@RequestBody TripCarRequets tripCarRequets) {
        TripCar tripCar = tripCarService.createTripCar(tripCarRequets);
        return new ResponseEntity<>(tripCarRequets, HttpStatus.OK);
    }

    @PutMapping("/api/api-tripcar/update-tripcar/{tripCarId}") // Chỉ có token admin mới có thể cập nhật được chuyến xe
     public ResponseEntity<TripCarRequets> updateTripCarByAdmin(@PathVariable Long tripCarId, @RequestBody TripCarRequets tripCarRequets) {
        TripCar tripCar = tripCarService.updateTripCar(tripCarId, tripCarRequets);
        return new ResponseEntity<>(tripCarRequets, HttpStatus.OK);
    }

    @DeleteMapping("/api/api-tripcar/{tripCarId}") // Chỉ có token admin mới có thể xoá được chuyến xe
    public ResponseEntity<String> deleteTripCarByAdmin(@PathVariable Long tripCarId) {
        tripCarService.deleteTripCar(tripCarId);
        return new ResponseEntity<>("Trip Car deleted", HttpStatus.OK);
    }

    @PostMapping("/api/send-email/payment") // Có thể gửi gmail khi có token của USER và ADMIN
    public ResponseEntity<String> paymentAndSendMail(@RequestBody PaymentRequest paymentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (!paymentRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return ResponseEntity.badRequest().body("Email không hợp lệ!");
        }

        // Thêm dòng này để khai báo subject
        String subject = "Xác nhận đặt vé thành công - " + paymentRequest.getTripName();

        String body = "Cảm ơn bạn đã đặt vé tại TicketCar!\n\n"
                + "Thông tin chuyến đi:\n"
                + "-----------------------------------\n"
                + "Nhà xe: " + paymentRequest.getCoachName() + "\n"
                + "Chuyến: " + paymentRequest.getTripName() + "\n"
                + "Biển số xe: " + paymentRequest.getLicensePlateNumberCoach() + "\n"
                + "Thời gian xuất phát: " + paymentRequest.getDepartureTime() + " ngày " + paymentRequest.getDepartureDate() + "\n"
                + "Thời gian đến: " + paymentRequest.getDepartureEndTime() + "\n"
                + "Điểm đi: " + paymentRequest.getPickupPoint() + "\n"
                + "Điểm đến: " + paymentRequest.getPayPonit() + "\n"
                + "Ghế đã đặt: " + paymentRequest.getSeats() + "\n"
                + "Giá mỗi ghế: " + paymentRequest.getPriceSeatNumber() + " đ\n"
                + "Tổng tiền: " + paymentRequest.getTotalPrice() + " đ\n"
                + "-----------------------------------\n"
                + "Thông tin khách hàng:\n"
                + "Họ tên: " + paymentRequest.getName() + "\n"
                + "Số điện thoại: " + paymentRequest.getPhone() + "\n"
                + "Email: " + paymentRequest.getEmail() + "\n"
                + "Phương thức thanh toán: " + paymentRequest.getPaymentMethod() + "\n\n"
                + "Chúc bạn có chuyến đi an toàn và vui vẻ!\n"
                + "TicketCar";

        emailService.sendEmail(paymentRequest.getEmail(), subject, body);

        return ResponseEntity.ok("Đặt vé và gửi email thành công!");
    }
}
