package com.ayilbank.task.controller;

import com.ayilbank.task.dto.TextDto;
import com.ayilbank.task.dto.tasktype.CreateTaskTypeDto;
import com.ayilbank.task.dto.tasktype.TaskTypeDto;
import com.ayilbank.task.service.TaskTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-type")
@Tag(name = "Контроллер Типа Задач")
public class TaskTypeController {

    private final TaskTypeService taskTypeService;

    @GetMapping
    @Operation(summary = "Получить типы задач")
    public ResponseEntity<List<TaskTypeDto>> getTaskTypes() {
        return ResponseEntity.ok(taskTypeService.getTaskTypes());
    }

    @PostMapping
    @Operation(summary = "Создать тип задач")
    public ResponseEntity<TaskTypeDto> createTaskType(@RequestBody CreateTaskTypeDto createTaskTypeDto) {
        return ResponseEntity.ok(taskTypeService.createTaskType(createTaskTypeDto));
    }

    @PutMapping
    @Operation(summary = "Изменить тип задач")
    public ResponseEntity<TaskTypeDto> updateTaskType(@RequestBody TaskTypeDto taskTypeDto) {
        return ResponseEntity.ok(taskTypeService.updateTaskType(taskTypeDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип задач")
    public ResponseEntity<TextDto> deleteTaskType(@PathVariable Long id) {
        return ResponseEntity.ok(taskTypeService.deleteTaskType(id));
    }
}
