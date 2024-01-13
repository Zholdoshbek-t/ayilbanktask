package com.ayilbank.task.repository;

import com.ayilbank.task.entity.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

    Optional<TaskType> findByName(String name);

    boolean existsByName(String name);
}
