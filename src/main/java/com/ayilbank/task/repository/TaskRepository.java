package com.ayilbank.task.repository;

import com.ayilbank.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByTaskTypeNameAndDescription(String taskTypeName, String description);

    List<Task> findAllByTaskTypeName(String taskTypeName);

    List<Task> findAllByCompletedTrue();

    List<Task> findAllByCompletedFalse();
}
