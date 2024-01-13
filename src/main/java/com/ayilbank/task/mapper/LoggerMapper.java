package com.ayilbank.task.mapper;

import com.ayilbank.task.dto.enums.ServiceMethod;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggerMapper {

    public static String getInfo(String uuid, ServiceMethod method, Object body) {
        return String.format("UUID: %s | Method: %s | Body: %s", uuid, method.getMethod(), body.toString());
    }

    public static String getInfo(String uuid, ServiceMethod method) {
        return String.format("UUID: %s | Method: %s", uuid, method.getMethod());
    }
}
