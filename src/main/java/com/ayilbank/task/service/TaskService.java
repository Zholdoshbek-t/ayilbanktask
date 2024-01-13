package com.ayilbank.task.service;

import com.ayilbank.task.dto.TextDto;
import com.ayilbank.task.dto.task.CreateTaskDto;
import com.ayilbank.task.dto.task.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto getTask(Long id);
    List<TaskDto> getTasks();
    List<TaskDto> getCompletedTasks();
    List<TaskDto> getUncompletedTasks();
    List<TaskDto> getTasksByTaskType(Long id);
    TaskDto createTask(CreateTaskDto createTaskDto);
    TextDto deleteTask(Long id);
}
