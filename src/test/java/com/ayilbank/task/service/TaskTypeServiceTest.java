package com.ayilbank.task.service;

import com.ayilbank.task.dto.TextDto;
import com.ayilbank.task.dto.tasktype.CreateTaskTypeDto;
import com.ayilbank.task.dto.tasktype.TaskTypeDto;
import com.ayilbank.task.entity.Task;
import com.ayilbank.task.entity.TaskType;
import com.ayilbank.task.exception.AlreadyExistsException;
import com.ayilbank.task.exception.NotFoundException;
import com.ayilbank.task.repository.TaskRepository;
import com.ayilbank.task.repository.TaskTypeRepository;
import com.ayilbank.task.service.impl.TaskTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ayilbank.task.dto.enums.Message.TASK_TYPE_DELETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskTypeServiceTest {

    private TaskTypeServiceImpl taskTypeService;
    private TaskTypeRepository taskTypeRepository;

    @BeforeEach
    void setUp() {
        taskTypeRepository = mock(TaskTypeRepository.class);
        TaskRepository taskRepository = mock(TaskRepository.class);
        taskTypeService = new TaskTypeServiceImpl(taskTypeRepository, taskRepository);
    }

    @Test
    void getTaskTypesSuccessful() {
        List<TaskType> taskTypeList = taskTypeList();

        when(taskTypeRepository.findAll()).thenReturn(taskTypeList);

        List<TaskTypeDto> taskTypeDtos = taskTypeService.getTaskTypes();

        assertEquals(4, taskTypeDtos.size());
    }

    @Test
    void createTaskTypeSuccessful() {
        when(taskTypeRepository.existsByName("WORK")).thenReturn(false);

        CreateTaskTypeDto createTaskTypeDto = createTaskTypeDto();

        TaskTypeDto taskTypeDto = taskTypeService.createTaskType(createTaskTypeDto);

        assertNotNull(taskTypeDto);
        assertNotNull(taskTypeDto.getName());
        assertEquals("WORK", taskTypeDto.getName());
    }

    @Test
    void createTaskTypeAlreadyExistsException() {
        when(taskTypeRepository.existsByName("WORK")).thenReturn(true);

        CreateTaskTypeDto createTaskTypeDto = createTaskTypeDto();

        assertThrows(AlreadyExistsException.class, () -> taskTypeService.createTaskType(createTaskTypeDto));
    }

    @Test
    void updateTaskTypeSuccessful() {
        TaskTypeDto taskTypeDto = taskTypeDto();
        TaskType taskType = taskType();

        when(taskTypeRepository.findById(taskTypeDto.getId())).thenReturn(Optional.of(taskType));
        when(taskTypeRepository.existsByName(taskTypeDto.getName())).thenReturn(false);

        TaskTypeDto result = taskTypeService.updateTaskType(taskTypeDto);

        assertNotNull(result);
        assertNotNull(result.getName());
        assertEquals(taskTypeDto.getName(), result.getName());
    }

    @Test
    void updateTaskTypeNotFoundException() {
        TaskTypeDto taskTypeDto = taskTypeDto();

        when(taskTypeRepository.findById(taskTypeDto.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskTypeService.updateTaskType(taskTypeDto));
    }

    @Test
    void updateTaskTypeAlreadyExistsException() {
        TaskTypeDto taskTypeDto = taskTypeDto();
        TaskType taskType = taskType();

        when(taskTypeRepository.findById(taskTypeDto.getId())).thenReturn(Optional.of(taskType));
        when(taskTypeRepository.existsByName(taskTypeDto.getName())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> taskTypeService.updateTaskType(taskTypeDto));
    }

    @Test
    void deleteTaskTypeSuccessful() {
        TaskType taskType = taskType();

        when(taskTypeRepository.findById(1L)).thenReturn(Optional.of(taskType));

        TextDto result = taskTypeService.deleteTaskType(1L);

        assertNotNull(result);
        assertNotNull(result.getText());
        assertEquals(TASK_TYPE_DELETED.getDescription(), result.getText());
    }

    @Test
    void deleteTaskTypeNotFoundException() {
        when(taskTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskTypeService.deleteTaskType(1L));
    }

    private List<TaskType> taskTypeList() {
        TaskType taskType1 = TaskType.builder().id(1L).name("HOME").build();
        TaskType taskType2 = TaskType.builder().id(1L).name("SCHOOL").build();
        TaskType taskType3 = TaskType.builder().id(1L).name("UNIVERSITY").build();
        TaskType taskType4 = TaskType.builder().id(1L).name("GENERAL").build();

        return List.of(taskType1, taskType2, taskType3, taskType4);
    }

    private CreateTaskTypeDto createTaskTypeDto() {
        return CreateTaskTypeDto.builder()
                .name("WORK")
                .build();
    }

    private TaskTypeDto taskTypeDto() {
        return TaskTypeDto.builder()
                .id(1L)
                .name("GARAGE")
                .build();
    }

    private TaskType taskType() {
        TaskType taskType = TaskType.builder()
                .id(1L)
                .name("KITCHEN")
                .build();

        Task task1 = Task.builder()
                .id(1L)
                .taskType(taskType)
                .description("Clean room")
                .completed(false)
                .build();

        Task task2 = Task.builder()
                .id(1L)
                .taskType(taskType)
                .description("Wash dishes")
                .completed(false)
                .build();

        taskType.setTasks(Set.of(task1, task2));

        return taskType;
    }
}
