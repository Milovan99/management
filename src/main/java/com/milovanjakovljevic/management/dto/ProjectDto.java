package com.milovanjakovljevic.management.dto;

import com.milovanjakovljevic.management.models.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public class ProjectDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate deadline;
    private String clientName;
    private String shortDescription;
    private ProjectStatus status;
    private int completionPercentage;

    private List<TaskDto> tasks;

    // Constructor

    public ProjectDto(Long id, String name, LocalDate startDate, LocalDate deadline, String clientName, String shortDescription, ProjectStatus status, int completionPercentage, List<TaskDto> tasks) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
        this.clientName = clientName;
        this.shortDescription = shortDescription;
        this.status = status;
        this.completionPercentage = completionPercentage;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}

