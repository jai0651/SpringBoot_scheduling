package CronScheduling.scheduling.schedules;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cron_schedule")
public class CronSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cron_expression", nullable = false)
    private String cronExpression;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "last_executed")
    private LocalDateTime lastExecuted;

    // Convert Entity to DTO
    public CronScheduleDto toDto() {
        return CronScheduleDto.builder()
                .id(this.id)
                .name(this.name)
                .cronExpression(this.cronExpression)
                .isActive(this.isActive)
                .lastExecuted(this.lastExecuted)
                .build();
    }

    // Convert DTO to Entity
    public static CronSchedule fromDto(CronScheduleDto dto) {
        return CronSchedule.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cronExpression(dto.getCronExpression())
                .isActive(dto.getIsActive())
                .lastExecuted(dto.getLastExecuted())
                .build();
    }
}
