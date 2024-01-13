package com.ayilbank.task.service.impl;

import com.ayilbank.task.dto.TextDto;
import com.ayilbank.task.dto.task.CreateTaskDto;
import com.ayilbank.task.dto.task.TaskDto;
import com.ayilbank.task.entity.Task;
import com.ayilbank.task.entity.TaskType;
import com.ayilbank.task.exception.AlreadyExistsException;
import com.ayilbank.task.exception.NotFoundException;
import com.ayilbank.task.mapper.LoggerMapper;
import com.ayilbank.task.mapper.TaskMapper;
import com.ayilbank.task.repository.TaskRepository;
import com.ayilbank.task.repository.TaskTypeRepository;
import com.ayilbank.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ayilbank.task.dto.enums.Message.TASK_NOT_FOUND;
import static com.ayilbank.task.dto.enums.Message.TASK_TYPE_NOT_FOUND_ID;
import static com.ayilbank.task.dto.enums.Message.TASK_EXISTS;
import static com.ayilbank.task.dto.enums.Message.TASK_CREATED;
import static com.ayilbank.task.dto.enums.Message.TASK_DELETED;
import static com.ayilbank.task.dto.enums.ServiceMethod.GET_TASK;
import static com.ayilbank.task.dto.enums.ServiceMethod.GET_TASKS;
import static com.ayilbank.task.dto.enums.ServiceMethod.GET_COMPLETED_TASKS;
import static com.ayilbank.task.dto.enums.ServiceMethod.GET_UNCOMPLETED_TASKS;
import static com.ayilbank.task.dto.enums.ServiceMethod.GET_TASKS_BY_TASK_TYPE;
import static com.ayilbank.task.dto.enums.ServiceMethod.CREATE_TASK;
import static com.ayilbank.task.dto.enums.ServiceMethod.DELETE_TASK;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskTypeRepository taskTypeRepository;

    @Override
    public TaskDto getTask(Long id) {
        String uuid = UUID.randomUUID().toString();

        log.info(LoggerMapper.getInfo(uuid, GET_TASK, id));

        Optional<Task> optionalTask = taskRepository.findById(id);

        if(optionalTask.isEmpty()) {
            log.error(LoggerMapper.getInfo(uuid, GET_TASK, TASK_NOT_FOUND.getDescription()));

            throw new NotFoundException(TASK_NOT_FOUND.getDescription());
        }

        log.info(LoggerMapper.getInfo(uuid, GET_TASK, "Task returned"));

        return TaskMapper.getTaskDto(optionalTask.get());
    }

    @Override
    public List<TaskDto> getTasks() {
        String uuid = UUID.randomUUID().toString();

        log.info(uuid, GET_TASKS);

        return taskRepository.findAll().stream()
                .map(TaskMapper::getTaskDto)
                .toList();
    }

    @Override
    public List<TaskDto> getCompletedTasks() {
        String uuid = UUID.randomUUID().toString();

        log.info(uuid, GET_COMPLETED_TASKS);

        return taskRepository.findAllByCompletedTrue().stream()
                .map(TaskMapper::getTaskDto)
                .toList();
    }

    @Override
    public List<TaskDto> getUncompletedTasks() {
        String uuid = UUID.randomUUID().toString();

        log.info(uuid, GET_UNCOMPLETED_TASKS);

        return taskRepository.findAllByCompletedFalse().stream()
                .map(TaskMapper::getTaskDto)
                .toList();
    }

    @Override
    public List<TaskDto> getTasksByTaskType(Long id) {
        String uuid = UUID.randomUUID().toString();

        log.info(LoggerMapper.getInfo(uuid, GET_TASKS_BY_TASK_TYPE, id));

        Optional<TaskType> optionalTaskType = taskTypeRepository.findById(id);

        if(optionalTaskType.isEmpty()) {
            log.error(LoggerMapper.getInfo(uuid, GET_TASKS_BY_TASK_TYPE, TASK_TYPE_NOT_FOUND_ID.getDescription()));

            throw new NotFoundException(TASK_TYPE_NOT_FOUND_ID.getDescription());
        }

        return taskRepository.findAllByTaskTypeName(optionalTaskType.get().getName()).stream()
                .map(TaskMapper::getTaskDto)
                .toList();
    }

    @Override
    public TaskDto createTask(CreateTaskDto createTaskDto) {
        String uuid = UUID.randomUUID().toString();

        log.info(uuid, CREATE_TASK, createTaskDto);

        TaskType taskType = null;

        if(createTaskDto.getType() != null) {
            Optional<TaskType> optionalTaskType = taskTypeRepository.findByName(createTaskDto.getType());

            if(optionalTaskType.isEmpty()) {
                TaskType createType = TaskType.builder()
                        .name(createTaskDto.getType().toUpperCase())
                        .build();

                taskTypeRepository.save(createType);

                taskType = createType;
            } else {
                taskType = optionalTaskType.get();
            }
        }

        if(taskType != null) {
            boolean existsByTaskTypeNameAndDescription =
                    taskRepository.existsByTaskTypeNameAndDescription(taskType.getName(), createTaskDto.getDescription());

            if (existsByTaskTypeNameAndDescription) {
                log.error(LoggerMapper.getInfo(uuid, CREATE_TASK, TASK_EXISTS.getDescription()));

                throw new AlreadyExistsException(TASK_EXISTS.getDescription());
            }
        }

        Task task = Task.builder()
                .taskType(taskType)
                .description(createTaskDto.getDescription())
                .completed(false)
                .build();

        taskRepository.save(task);

        log.info(LoggerMapper.getInfo(uuid, CREATE_TASK, TASK_CREATED.getDescription()));

        return TaskMapper.getTaskDto(task);
    }

    @Override
    public TextDto deleteTask(Long id) {
        String uuid = UUID.randomUUID().toString();

        log.info(LoggerMapper.getInfo(uuid, DELETE_TASK, id));

        Optional<Task> optionalTask = taskRepository.findById(id);

        if(optionalTask.isEmpty()) {
            log.error(LoggerMapper.getInfo(uuid, DELETE_TASK, TASK_NOT_FOUND.getDescription()));

            throw new NotFoundException(TASK_NOT_FOUND.getDescription());
        }

        taskRepository.delete(optionalTask.get());

        log.info(LoggerMapper.getInfo(uuid, DELETE_TASK, TASK_DELETED.getDescription()));

        return TextDto.builder()
                .text(TASK_DELETED.getDescription())
                .build();
    }
}
