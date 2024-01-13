package com.ayilbank.task.repository;

import com.ayilbank.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью Task в базе данных.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Проверяет существование задачи по названию типа задачи и описанию.
     *
     * @param taskTypeName  Название типа задачи
     * @param description  Описание задачи
     * @return true, если задача с указанным типом и описанием существует, в противном случае - false
     */
    boolean existsByTaskTypeNameAndDescription(String taskTypeName, String description);

    /**
     * Получает список задач по названию типа задачи.
     *
     * @param taskTypeName  Название типа задачи
     * @return Список задач с указанным типом
     */
    List<Task> findAllByTaskTypeName(String taskTypeName);

    /**
     * Получает список всех завершенных задач.
     *
     * @return Список завершенных задач
     */
    List<Task> findAllByCompletedTrue();

    /**
     * Получает список всех незавершенных задач.
     *
     * @return Список незавершенных задач
     */
    List<Task> findAllByCompletedFalse();
}
