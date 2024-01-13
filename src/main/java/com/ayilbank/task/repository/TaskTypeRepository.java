package com.ayilbank.task.repository;

import com.ayilbank.task.entity.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью TaskType в базе данных.
 */
@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

    /**
     * Находит тип задачи по его названию.
     *
     * @param name  Название типа задачи
     * @return Optional с объектом TaskType, если такой тип задачи найден, иначе - пустой Optional
     */
    Optional<TaskType> findByName(String name);

    /**
     * Проверяет существование типа задачи по его названию.
     *
     * @param name  Название типа задачи
     * @return true, если тип задачи с указанным названием существует, в противном случае - false
     */
    boolean existsByName(String name);
}
