package spring_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Trip_Car")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripCarId;
    private String tripName;
    private LocalDate departureDate; // ngày khởi hành
    private LocalTime departureTime; // giờ khởi hành
    private LocalTime departureEndTime; // giờ kết thúc chuyến xe
    private String pickupPoint; // điểm đón
    private String payPonit; // điểm trả
    private Long seatNumber; // số ghế
    private Long emptySeatNumber; // số ghế còn lại
    private Long priceSeatNumber; // giá ghế

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id" , referencedColumnName = "driverId")
    @JsonIgnore
    private Driver driver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_id" , referencedColumnName = "coachId")
    @JsonIgnore
    private Coach coach;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rickshaw_id" , referencedColumnName = "rickshawId")
    @JsonIgnore
    private Rickshaw rickshaw;

    @OneToMany(mappedBy = "tripCar" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

}
