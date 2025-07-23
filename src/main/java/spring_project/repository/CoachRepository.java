package spring_project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_project.entity.Coach;

@Repository
@Transactional
public interface CoachRepository extends JpaRepository<Coach, Long> {

}
