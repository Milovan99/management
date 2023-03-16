package com.milovanjakovljevic.management.repositories;

import com.milovanjakovljevic.management.models.Task;
import com.milovanjakovljevic.management.models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    long countByProjectIdAndStatus(Long projectId, TaskStatus status);
}