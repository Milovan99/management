package com.milovanjakovljevic.management.repositories;

import com.milovanjakovljevic.management.models.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findByTaskIdOrderByDeadlineAsc(Long taskId);
}