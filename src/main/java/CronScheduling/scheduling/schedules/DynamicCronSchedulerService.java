package CronScheduling.scheduling.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicCronSchedulerService {

    @Autowired
    private CronScheduleRepository cronScheduleRepository;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    private ScheduledFuture<?> scheduledTask;

    @PostConstruct
    public void scheduleTasksFromDB() {
        List<CronSchedule> schedules = cronScheduleRepository.findByIsActiveTrue();
        for (CronSchedule schedule : schedules) {
            scheduleTask(schedule);
        }
    }

    public void scheduleTask(CronSchedule cronSchedule) {
        scheduledTask = taskScheduler.schedule(
                () -> executeTask(cronSchedule),
                new CronTrigger(cronSchedule.getCronExpression())
        );
    }

    private void executeTask(CronSchedule cronSchedule) {
        System.out.println("Executing task: " + cronSchedule.getName() + " at " + LocalDateTime.now());
        System.out.println("HI Jai!");
        cronSchedule.setLastExecuted(LocalDateTime.now());
        cronScheduleRepository.save(cronSchedule);
    }
    public List<CronScheduleDto> getAllActiveCronSchedules() {
        return cronScheduleRepository.findByIsActiveTrue().stream()
                .map(CronSchedule::toDto)  // Convert each entity to DTO
                .toList();  // Collect the results into a list
    }

    public Optional<CronScheduleDto> getCronScheduleById(Long id) {
        return cronScheduleRepository.findById(id).map(CronSchedule::toDto);
    }

    public CronScheduleDto saveCronSchedule(CronScheduleDto cronScheduleDto) {
        return Optional.of(cronScheduleDto)
                .map(CronSchedule::fromDto)  // Convert DTO to Entity
                .map(cronScheduleRepository::save)  // Save the entity to the database
                .map(CronSchedule::toDto)  // Convert the saved entity back to DTO
                .orElseThrow(() -> new RuntimeException("Failed to save CronSchedule"));  // Handle any potential error
    }


    public void deleteCronSchedule(Long id) {
        cronScheduleRepository.deleteById(id);
    }


    @Scheduled(fixedDelay = 60000) // Check every 1 minute for new/updated schedules
    public void rescheduleTasks() {
        if (scheduledTask != null && !scheduledTask.isCancelled()) {
            scheduledTask.cancel(true);
        }
        scheduleTasksFromDB();
    }
}
