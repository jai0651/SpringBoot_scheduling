package CronScheduling.scheduling.schedules;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CronScheduleRepository extends JpaRepository<CronSchedule, Long> {
    List<CronSchedule> findByIsActiveTrue();
}
