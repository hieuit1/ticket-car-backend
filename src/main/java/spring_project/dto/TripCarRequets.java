package spring_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripCarRequets {
    private Long tripCarId;
    private String tripName;
    private LocalDate departureDate; // ngày khởi hành
    private LocalTime departureTime; // giờ khởi hành
    private LocalTime departureEndTime; // giờ kết thucs chuyến xe
    private String pickupPoint; // điểm đón
    private String payPonit; // điểm trả
    private Long seatNumber; // số ghế
    private Long emptySeatNumber; // số ghế trống
    private Long priceSeatNumber; // giá ghế
    private Long driverId;
    private Long coachId;
    private Long rickshawId;

    private String licensePlateNumberCoach;
    private String coachName;
    private String url;
    private String publicId;

    private String fullName;
    private String phoneNumber;
    private Integer yearOfBirth;
    private String gender;

    private String rickShawfullName;
    private Long rickShawphoneNumber;
    private Long rickShawyearOfBirth;
    private String rickShawgender;
}
