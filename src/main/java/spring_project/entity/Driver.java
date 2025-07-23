package spring_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Table(name = "driver")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;
    private String fullName;
    private Long phoneNumber;
    private Long yearOfBirth;
    private String descriptions;
    private String gender;
    private String url;
    private String publicId;

    @OneToMany(mappedBy = "driver" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<TripCar> tripCars;

    public List<TripCar> getTripCars() {
        return tripCars;
    }
}
