package com.milovanjakovljevic.management.repositories;

import com.milovanjakovljevic.management.models.Project;
import com.milovanjakovljevic.management.models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByDeadlineAsc();
    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.id = :projectId AND t.status = :status")
    static long countByProjectIdAndStatus(@Param("projectId") Long projectId, @Param("status") TaskStatus status);
    @Query("SELECT COUNT(s) FROM Subtask s WHERE s.task.id = :taskId AND s.status = :status")
    int countByTaskIdAndStatus(@Param("taskId") Long taskId, @Param("status") TaskStatus status);

}
