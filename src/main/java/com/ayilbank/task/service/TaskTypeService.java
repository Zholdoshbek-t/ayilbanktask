package com.ayilbank.task.service;

import com.ayilbank.task.dto.TextDto;
import com.ayilbank.task.dto.tasktype.CreateTaskTypeDto;
import com.ayilbank.task.dto.tasktype.TaskTypeDto;

import java.util.List;

public interface TaskTypeService {

    List<TaskTypeDto> getTaskTypes();
    TaskTypeDto createTaskType(CreateTaskTypeDto createTaskTypeDto);
    TaskTypeDto updateTaskType(TaskTypeDto taskTypeDto);
    TextDto deleteTaskType(Long id);
}
