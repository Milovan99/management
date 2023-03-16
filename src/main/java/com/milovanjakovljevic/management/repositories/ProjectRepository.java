package com.milovanjakovljevic.management.repositories;

import com.milovanjakovljevic.management.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByDeadlineAsc();
}
