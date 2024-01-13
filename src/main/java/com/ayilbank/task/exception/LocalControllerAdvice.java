package com.ayilbank.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * Глобальный обработчик исключений для контроллеров.
 */
@ControllerAdvice
@RestController
public class LocalControllerAdvice {

    /**
     * Обработка исключения NotFoundException.
     *
     * @param e Исключение типа NotFoundException
     * @return ResponseEntity с сообщением об ошибке и HTTP статусом NOT_FOUND (404)
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка исключения AlreadyExistsException.
     *
     * @param e Исключение типа AlreadyExistsException
     * @return ResponseEntity с сообщением об ошибке и HTTP статусом CONFLICT (409)
     */
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Обработка исключения MethodArgumentNotValidException.
     * Этот обработчик предназначен для валидации параметров методов контроллеров.
     *
     * @param e Исключение типа MethodArgumentNotValidException
     * @return ResponseEntity с сообщением об ошибке и HTTP статусом из исключения
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // Извлекаем сообщение об ошибке из исключения и устанавливаем соответствующий HTTP статус
        String message = e.getMessage();
        message = message.substring(message.lastIndexOf("[") + 1, message.lastIndexOf("]") - 1);
        return new ResponseEntity<>(message, e.getStatusCode());
    }
}
