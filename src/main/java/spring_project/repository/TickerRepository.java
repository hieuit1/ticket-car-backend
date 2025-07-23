package spring_project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_project.entity.Ticket;

import java.util.List;

@Repository
@Transactional
public interface TickerRepository extends JpaRepository<Ticket , Long> {
    List<Ticket> findByUserId(Long id);
}
