package CronScheduling.scheduling.schedules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CronScheduleDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("cron_expression")
    private String cronExpression;
    @JsonProperty("is_active")
    private Boolean isActive;
    @JsonProperty("last_executed")
    private LocalDateTime lastExecuted;
}
