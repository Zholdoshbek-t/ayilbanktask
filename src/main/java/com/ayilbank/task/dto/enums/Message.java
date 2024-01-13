package com.ayilbank.task.dto.enums;

import lombok.Getter;

@Getter
public enum Message {

    TASK_NOT_FOUND("Task with the given ID was not found."),
    TASK_EXISTS("Task with the given task type and description already exists."),
    TASK_CREATED("Task was created."),
    TASK_DELETED("Task was deleted."),

    TASK_TYPE_EXISTS("Task Type with the given name already exists."),
    TASK_TYPE_NOT_FOUND_ID("Task Type with the given ID was not found."),
    TASK_TYPE_CREATED("Task Type was created."),
    TASK_TYPE_UPDATED("Task Type was updated."),
    TASK_TYPE_DELETED("Task Type was deleted.")
    ;
    private final String description;

    Message(String description) {
        this.description = description;
    }
}
