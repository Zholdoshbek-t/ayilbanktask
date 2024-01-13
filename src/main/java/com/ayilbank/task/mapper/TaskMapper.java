package com.ayilbank.task.mapper;

import com.ayilbank.task.dto.task.TaskDto;
import com.ayilbank.task.entity.Task;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskMapper {

    public static TaskDto getTaskDto(Task task) {
        return TaskDto.builder()
                .taskId(task.getId() != null ? task.getId() : 0L)
                .description(task.getDescription())
                .taskType(task.getTaskType() != null ? task.getTaskType().getName() : "GENERAL")
                .completed(task.isCompleted())
                .build();
    }
}
