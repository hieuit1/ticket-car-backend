package spring_project.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_project.dto.TripCarRequets;
import spring_project.entity.Coach;
import spring_project.entity.Driver;
import spring_project.entity.Rickshaw;
import spring_project.entity.TripCar;
import spring_project.mapper.TripCarMapper;
import spring_project.repository.TripCarRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripCarService {

    @Autowired
    private TripCarRepository tripCarRepository;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CoachSevice coachSevice;

    @Autowired
    private RickShawService rickShawService;

    @Autowired
    private TripCarMapper tripCarMapper;

    public List<TripCarRequets> getAllTripCars() {
        List<TripCar> ListTripCars = tripCarRepository.findAll();
        return ListTripCars.stream()
                .map(tripCarMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TripCarRequets getTripCarById(Long tripCarId) {
        TripCar tripCar = tripCarRepository.findById(tripCarId).orElse(null);
        return tripCarMapper.toDTO(tripCar);
    }

    public TripCar createTripCar(TripCarRequets tripCarRequets) {
        TripCar newTripCar = new TripCar();
        newTripCar.setTripName(tripCarRequets.getTripName());
        newTripCar.setEmptySeatNumber(tripCarRequets.getEmptySeatNumber());
        newTripCar.setPriceSeatNumber(tripCarRequets.getPriceSeatNumber());
        newTripCar.setSeatNumber(tripCarRequets.getSeatNumber());
        newTripCar.setPayPonit(tripCarRequets.getPayPonit());
        newTripCar.setPickupPoint(tripCarRequets.getPickupPoint());
        newTripCar.setDepartureTime(tripCarRequets.getDepartureTime());
        newTripCar.setDepartureEndTime(tripCarRequets.getDepartureEndTime());
        newTripCar.setDepartureDate(tripCarRequets.getDepartureDate());
        Driver driver = driverService.findDriverById(tripCarRequets.getDriverId());
        Coach coach = coachSevice.findCoachById(tripCarRequets.getCoachId());
        Rickshaw rickshaw = rickShawService.getRickShaw(tripCarRequets.getRickshawId());
        newTripCar.setDriver(driver);
        newTripCar.setCoach(coach);
        newTripCar.setRickshaw(rickshaw);
        return tripCarRepository.save(newTripCar);
    }

    public TripCar updateTripCar(Long tripCarId, TripCarRequets tripCar) {
        TripCar updatetripCar = tripCarRepository.findById(tripCarId).orElse(null);
        if (tripCar != null) {
            if (tripCar.getTripName() != null) {
                updatetripCar.setTripName(tripCar.getTripName());
            }

            if (tripCar.getEmptySeatNumber() != null) {
                updatetripCar.setEmptySeatNumber(tripCar.getEmptySeatNumber());
            }

            if (tripCar.getPriceSeatNumber() != null) {
                updatetripCar.setPriceSeatNumber(tripCar.getPriceSeatNumber());
            }

            if (tripCar.getSeatNumber() != null) {
                updatetripCar.setSeatNumber(tripCar.getSeatNumber());
            }

            if (tripCar.getPayPonit() != null) {
                updatetripCar.setPayPonit(tripCar.getPayPonit());
            }

            if (tripCar.getPickupPoint() != null) {
                updatetripCar.setPickupPoint(tripCar.getPickupPoint());
            }

            if (tripCar.getDepartureTime() != null) {
                updatetripCar.setDepartureTime(tripCar.getDepartureTime());
            }

            if (tripCar.getDepartureEndTime() != null) {
                updatetripCar.setDepartureEndTime(tripCar.getDepartureEndTime());
            }

            if (tripCar.getDepartureDate() != null) {
                updatetripCar.setDepartureDate(tripCar.getDepartureDate());
            }
        }
        return tripCarRepository.save(updatetripCar);
    }

    public boolean deleteTripCar(Long tripCarId) {
        TripCar tripCar = tripCarRepository.findById(tripCarId).orElse(null);
        tripCarRepository.delete(tripCar);
        return true;
    }

    public TripCar findTripCarById(Long tripCarId) {
        Optional<TripCar> tripCar = tripCarRepository.findById(tripCarId);
        return tripCar.orElse(null);
    }

    public TripCar save(TripCar tripCar) {
        return tripCarRepository.save(tripCar);
    }

}
