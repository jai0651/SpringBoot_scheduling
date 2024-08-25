package CronScheduling.scheduling.schedules;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cron-schedules")
public class CronScheduleController {


    private final DynamicCronSchedulerService dynamicCronSchedulerService;

    public CronScheduleController(DynamicCronSchedulerService dynamicCronSchedulerService) {
        this.dynamicCronSchedulerService = dynamicCronSchedulerService;
    }

    @GetMapping
    public List<CronScheduleDto> getAllCronSchedules() {
        return dynamicCronSchedulerService.getAllActiveCronSchedules();
    }
    @GetMapping("/{id}")
    public ResponseEntity<CronScheduleDto> getCronScheduleById(@PathVariable Long id) {
        return dynamicCronSchedulerService.getCronScheduleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)  // Return 404 Not Found
                        .body(null));
    }

    @PostMapping
    public CronScheduleDto createCronSchedule(@RequestBody CronScheduleDto cronSchedule) {
         return dynamicCronSchedulerService.saveCronSchedule(cronSchedule);
    }

    @PutMapping("/{id}")
    public CronScheduleDto updateCronSchedule(@PathVariable Long id, @RequestBody CronScheduleDto cronScheduleDTO) {
        cronScheduleDTO.setId(id);
        return dynamicCronSchedulerService.saveCronSchedule(cronScheduleDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCronSchedule(@PathVariable Long id) {
        dynamicCronSchedulerService.deleteCronSchedule(id);
    }
}
