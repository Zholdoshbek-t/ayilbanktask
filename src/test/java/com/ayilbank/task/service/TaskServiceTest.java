package com.ayilbank.task.service;

import com.ayilbank.task.dto.TextDto;
import com.ayilbank.task.dto.task.CreateTaskDto;
import com.ayilbank.task.dto.task.TaskDto;
import com.ayilbank.task.entity.Task;
import com.ayilbank.task.entity.TaskType;
import com.ayilbank.task.exception.AlreadyExistsException;
import com.ayilbank.task.exception.NotFoundException;
import com.ayilbank.task.repository.TaskRepository;
import com.ayilbank.task.repository.TaskTypeRepository;
import com.ayilbank.task.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.ayilbank.task.dto.enums.Message.TASK_DELETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    private TaskServiceImpl taskService;
    private TaskRepository taskRepository;
    private TaskTypeRepository taskTypeRepository;

    @BeforeEach
    void setUp() {
        taskTypeRepository = mock(TaskTypeRepository.class);
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskServiceImpl(taskRepository, taskTypeRepository);
    }

    @Test
    void getTaskSuccessful() {
        Task task = task();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDto taskDto = taskService.getTask(1L);

        assertNotNull(taskDto);
        assertNotNull(taskDto.getDescription());
        assertEquals("Wash dishes", taskDto.getDescription());
    }

    @Test
    void getTaskNotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.getTask(1L));
    }

    @Test
    void getTasksSuccessful() {
        List<Task> taskList = taskList();

        when(taskRepository.findAll()).thenReturn(taskList);

        List<TaskDto> result = taskService.getTasks();

        assertEquals(taskList.size(), result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(0).getDescription());
    }

    @Test
    void getCompletedTasksSuccessful() {
        List<Task> taskList = taskList();

        when(taskRepository.findAllByCompletedTrue()).thenReturn(taskList);

        List<TaskDto> result = taskService.getCompletedTasks();

        assertEquals(taskList.size(), result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(0).getDescription());
    }

    @Test
    void getUncompletedTasksSuccessful() {
        List<Task> taskList = taskList();

        when(taskRepository.findAllByCompletedFalse()).thenReturn(taskList);

        List<TaskDto> result = taskService.getUncompletedTasks();

        assertEquals(taskList.size(), result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(0).getDescription());
    }

    @Test
    void getTasksByTaskTypeSuccessful() {
        List<Task> taskList = taskList();
        TaskType taskType = taskType();

        when(taskTypeRepository.findById(1L)).thenReturn(Optional.of(taskType));
        when(taskRepository.findAllByTaskTypeName(taskType.getName())).thenReturn(taskList);

        List<TaskDto> result = taskService.getTasksByTaskType(1L);

        assertEquals(taskList.size(), result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(0).getDescription());
    }

    @Test
    void getTasksByTaskTypeNotFoundException() {
        when(taskTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.getTasksByTaskType(1L));
    }

    @Test
    void createTaskSuccessful() {
        CreateTaskDto createTaskDto = createTaskDto();

        when(taskRepository.existsByTaskTypeNameAndDescription(createTaskDto.getType(), createTaskDto().getDescription()))
                .thenReturn(false);

        TaskDto result = taskService.createTask(createTaskDto);

        assertNotNull(result);
        assertNotNull(result.getDescription());
        assertEquals(createTaskDto.getDescription(), result.getDescription());
    }

    @Test
    void createTaskAlreadyExistsException() {
        CreateTaskDto createTaskDto = createTaskDto();

        when(taskRepository.existsByTaskTypeNameAndDescription(createTaskDto.getType(), createTaskDto().getDescription()))
                .thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> taskService.createTask(createTaskDto));
    }

    @Test
    void deleteTaskSuccessful() {
        Task task = task();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TextDto result = taskService.deleteTask(1L);

        assertNotNull(result);
        assertNotNull(result.getText());
        assertEquals(TASK_DELETED.getDescription(), result.getText());
    }

    @Test
    void deleteTaskNotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.deleteTask(1L));
    }

    private Task task() {
        return Task.builder()
                .id(1L)
                .description("Wash dishes")
                .completed(true)
                .build();
    }

    private List<Task> taskList() {
        Task task1 = Task.builder()
                .id(1L)
                .description("Do math homework")
                .completed(true)
                .build();

        Task task2 = Task.builder()
                .id(2L)
                .description("Do physic homework")
                .completed(true)
                .build();

        Task task3 = Task.builder()
                .id(3L)
                .description("Do chemistry homework")
                .completed(true)
                .build();

        return List.of(task1, task2, task3);
    }

    private TaskType taskType() {
        return TaskType.builder()
                .id(1L)
                .name("EVERYTHING")
                .build();
    }

    private CreateTaskDto createTaskDto() {
        return CreateTaskDto.builder()
                .type("SPORT")
                .description("Go to play the football")
                .build();
    }
}
