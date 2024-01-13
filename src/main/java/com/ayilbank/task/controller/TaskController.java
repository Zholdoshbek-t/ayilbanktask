package com.ayilbank.task.controller;

import com.ayilbank.task.dto.TextDto;
import com.ayilbank.task.dto.task.CreateTaskDto;
import com.ayilbank.task.dto.task.TaskDto;
import com.ayilbank.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Tag(name = "Контроллер Задач")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить задачу")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Получить все задачи")
    public ResponseEntity<List<TaskDto>> getTasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/completed")
    @Operation(summary = "Получить выполненные задачи")
    public ResponseEntity<List<TaskDto>> getCompletedTasks() {
        return ResponseEntity.ok(taskService.getCompletedTasks());
    }

    @GetMapping("/uncompleted")
    @Operation(summary = "Получить невыполненные задачи")
    public ResponseEntity<List<TaskDto>> getUncompletedTasks() {
        return ResponseEntity.ok(taskService.getUncompletedTasks());
    }

    @GetMapping("/type/{id}")
    @Operation(summary = "Получить задачи по определенному виду")
    public ResponseEntity<List<TaskDto>> getTasksByTaskType(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTasksByTaskType(id));
    }

    @PostMapping
    @Operation(summary = "Создать задачу")
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid CreateTaskDto createTaskDto) {
        return ResponseEntity.ok(taskService.createTask(createTaskDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить задачу")
    public ResponseEntity<TextDto> deleteTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
}
