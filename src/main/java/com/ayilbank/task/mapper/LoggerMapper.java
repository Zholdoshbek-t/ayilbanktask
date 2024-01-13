package com.ayilbank.task.mapper;

import com.ayilbank.task.dto.enums.ServiceMethod;
import lombok.experimental.UtilityClass;

/**
 * Утилитарный класс для маппинга логгера.
 */
@UtilityClass
public class LoggerMapper {

    /**
     * Получение информации для логгера с указанием UUID, метода и тела запроса.
     *
     * @param uuid  Уникальный идентификатор запроса
     * @param method  Метод запроса (GET_TASK, GET_TASKS, GET_TASK_TYPES и т.д.)
     * @param body  Тело запроса
     * @return Строка с информацией для логгера
     */
    public static String getInfo(String uuid, ServiceMethod method, Object body) {
        return String.format("UUID: %s | Method: %s | Body: %s", uuid, method.getMethod(), body.toString());
    }

    /**
     * Получение информации для логгера с указанием UUID и метода без тела запроса.
     *
     * @param uuid  Уникальный идентификатор запроса
     * @param method  Метод запроса (GET_TASK, GET_TASKS, GET_TASK_TYPES и т.д.)
     * @return Строка с информацией для логгера
     */
    public static String getInfo(String uuid, ServiceMethod method) {
        return String.format("UUID: %s | Method: %s", uuid, method.getMethod());
    }
}
