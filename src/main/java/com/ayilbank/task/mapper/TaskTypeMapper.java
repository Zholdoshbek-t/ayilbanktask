package com.ayilbank.task.mapper;

import com.ayilbank.task.dto.tasktype.TaskTypeDto;
import com.ayilbank.task.entity.TaskType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskTypeMapper {

    public static TaskTypeDto getTaskTypeDto(TaskType taskType) {
        return TaskTypeDto.builder()
                .id(taskType.getId() != null ? taskType.getId() : 0L)
                .name(taskType.getName())
                .build();
    }
}
