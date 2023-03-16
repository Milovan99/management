package com.milovanjakovljevic.management.dto;

import com.milovanjakovljevic.management.models.Project;

import java.util.List;

public class ProjectTaskCountDto {
    private Project project;
    private List<TaskStatusCountDto> taskStatusCounts;

    public ProjectTaskCountDto(Project project, List<TaskStatusCountDto> taskStatusCounts) {
        this.project = project;
        this.taskStatusCounts = taskStatusCounts;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<TaskStatusCountDto> getTaskStatusCounts() {
        return taskStatusCounts;
    }

    public void setTaskStatusCounts(List<TaskStatusCountDto> taskStatusCounts) {
        this.taskStatusCounts = taskStatusCounts;
    }
}
