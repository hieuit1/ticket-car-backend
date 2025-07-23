package spring_project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_project.entity.Rickshaw;

@Repository
@Transactional
public interface RickShawRepository extends JpaRepository<Rickshaw, Long> {
}
