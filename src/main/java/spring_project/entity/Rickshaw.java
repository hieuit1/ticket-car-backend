package spring_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Table(name = "rickshaw")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rickshaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rickshawId;
    private String fullName;
    private Long phoneNumber;
    private Long yearOfBirth;
    private String descriptions;
    private String gender;
    private String url;
    private String publicId;

    @OneToMany(mappedBy = "rickshaw" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<TripCar> tripCars;

    public List<TripCar> getTripCars() {
        return tripCars;
    }
}
