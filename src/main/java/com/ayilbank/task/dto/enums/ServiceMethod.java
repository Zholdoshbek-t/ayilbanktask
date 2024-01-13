package com.ayilbank.task.dto.enums;

import lombok.Getter;

@Getter
public enum ServiceMethod {

    GET_TASK("Get Task by ID"),
    GET_TASKS("Get Tasks"),
    GET_COMPLETED_TASKS("Get Completed Tasks"),
    GET_UNCOMPLETED_TASKS("Get Uncompleted Tasks"),
    GET_TASKS_BY_TASK_TYPE("Get Tasks by Task Type"),
    CREATE_TASK("Create Task"),
    DELETE_TASK("Delete Task"),

    GET_TASK_TYPES("Get Task Types"),
    CREATE_TASK_TYPE("Create Task Type"),
    UPDATE_TASK_TYPE("Update Task Type"),
    DELETE_TASK_TYPE("Delete Task Type");

    private final String method;

    ServiceMethod(String method) {
        this.method = method;
    }
}
