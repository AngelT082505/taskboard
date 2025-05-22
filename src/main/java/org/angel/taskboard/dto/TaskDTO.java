package org.angel.taskboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.angel.taskboard.enums.StatusEnums;
import org.angel.taskboard.enums.UrgencyLevelEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private LocalDateTime dateInserted;
    private LocalDateTime dateEnd;
    private String title;
    private String description;
    private StatusEnums status;
    private UrgencyLevelEnum urgency;
}
