package com.ayilbank.task.service.impl;

import com.ayilbank.task.dto.TextDto;
import com.ayilbank.task.dto.tasktype.CreateTaskTypeDto;
import com.ayilbank.task.dto.tasktype.TaskTypeDto;
import com.ayilbank.task.entity.TaskType;
import com.ayilbank.task.exception.AlreadyExistsException;
import com.ayilbank.task.exception.NotFoundException;
import com.ayilbank.task.mapper.LoggerMapper;
import com.ayilbank.task.mapper.TaskTypeMapper;
import com.ayilbank.task.repository.TaskRepository;
import com.ayilbank.task.repository.TaskTypeRepository;
import com.ayilbank.task.service.TaskTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ayilbank.task.dto.enums.Message.TASK_TYPE_EXISTS;
import static com.ayilbank.task.dto.enums.Message.TASK_TYPE_DELETED;
import static com.ayilbank.task.dto.enums.Message.TASK_TYPE_CREATED;
import static com.ayilbank.task.dto.enums.Message.TASK_TYPE_UPDATED;
import static com.ayilbank.task.dto.enums.Message.TASK_TYPE_NOT_FOUND_ID;
import static com.ayilbank.task.dto.enums.ServiceMethod.GET_TASK_TYPES;
import static com.ayilbank.task.dto.enums.ServiceMethod.CREATE_TASK_TYPE;
import static com.ayilbank.task.dto.enums.ServiceMethod.UPDATE_TASK_TYPE;
import static com.ayilbank.task.dto.enums.ServiceMethod.DELETE_TASK_TYPE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskTypeServiceImpl implements TaskTypeService {

    private final TaskTypeRepository taskTypeRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<TaskTypeDto> getTaskTypes() {
        String uuid = UUID.randomUUID().toString();

        log.info(LoggerMapper.getInfo(uuid, GET_TASK_TYPES));

        return taskTypeRepository.findAll().stream()
                .map(TaskTypeMapper::getTaskTypeDto)
                .toList();
    }

    @Override
    public TaskTypeDto createTaskType(CreateTaskTypeDto createTaskTypeDto) {
        String uuid = UUID.randomUUID().toString();

        log.info(LoggerMapper.getInfo(uuid, CREATE_TASK_TYPE));

        boolean taskTypeExists = taskTypeRepository.existsByName(createTaskTypeDto.getName());

        if(taskTypeExists) {
            log.error(LoggerMapper.getInfo(uuid, CREATE_TASK_TYPE, TASK_TYPE_EXISTS.getDescription()));

            throw new AlreadyExistsException(TASK_TYPE_EXISTS.getDescription());
        }

        TaskType taskType = TaskType.builder()
                .name(createTaskTypeDto.getName().toUpperCase())
                .build();

        taskTypeRepository.save(taskType);

        log.info(LoggerMapper.getInfo(uuid, CREATE_TASK_TYPE, TASK_TYPE_CREATED.getDescription()));

        return TaskTypeMapper.getTaskTypeDto(taskType);
    }

    @Override
    public TaskTypeDto updateTaskType(TaskTypeDto taskTypeDto) {
        String uuid = UUID.randomUUID().toString();

        log.info(LoggerMapper.getInfo(uuid, UPDATE_TASK_TYPE, taskTypeDto));

        Optional<TaskType> optionalTaskType = taskTypeRepository.findById(taskTypeDto.getId());
        boolean taskTypeExists = taskTypeRepository.existsByName(taskTypeDto.getName());

        if(optionalTaskType.isEmpty()) {
            log.error(LoggerMapper.getInfo(uuid, UPDATE_TASK_TYPE, TASK_TYPE_NOT_FOUND_ID.getDescription()));

            throw new NotFoundException(TASK_TYPE_NOT_FOUND_ID.getDescription());
        } else if(taskTypeExists) {
            log.error(LoggerMapper.getInfo(uuid, UPDATE_TASK_TYPE, TASK_TYPE_EXISTS.getDescription()));

            throw new AlreadyExistsException(TASK_TYPE_EXISTS.getDescription());
        }

        TaskType taskType = optionalTaskType.get();
        taskType.setName(taskTypeDto.getName());

        taskTypeRepository.save(taskType);

        log.info(LoggerMapper.getInfo(uuid, UPDATE_TASK_TYPE, TASK_TYPE_UPDATED.getDescription()));

        return TaskTypeMapper.getTaskTypeDto(taskType);
    }

    @Override
    public TextDto deleteTaskType(Long id) {
        String uuid = UUID.randomUUID().toString();

        log.info(LoggerMapper.getInfo(uuid, DELETE_TASK_TYPE, id));

        Optional<TaskType> optionalTaskType = taskTypeRepository.findById(id);

        if(optionalTaskType.isEmpty()) {
            log.error(LoggerMapper.getInfo(uuid, DELETE_TASK_TYPE, TASK_TYPE_NOT_FOUND_ID.getDescription()));

            throw new NotFoundException(TASK_TYPE_NOT_FOUND_ID.getDescription());
        }

        TaskType taskType = optionalTaskType.get();

        taskType.getTasks().forEach(
                task -> task.setTaskType(null)
        );

        taskRepository.saveAll(taskType.getTasks());

        taskTypeRepository.delete(taskType);

        log.info(LoggerMapper.getInfo(uuid, DELETE_TASK_TYPE, TASK_TYPE_DELETED.getDescription()));

        return TextDto.builder()
                .text(TASK_TYPE_DELETED.getDescription())
                .build();
    }
}
