package spring_project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_project.entity.TripCar;

@Repository
@Transactional
public interface TripCarRepository extends JpaRepository<TripCar , Long> {

}
