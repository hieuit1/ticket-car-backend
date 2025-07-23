package spring_project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_project.entity.Driver;

@Repository
@Transactional
public interface DriverRepository extends JpaRepository<Driver, Long> {

}
