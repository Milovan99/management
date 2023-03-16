package com.milovanjakovljevic.management.dto;

import com.milovanjakovljevic.management.models.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public class TaskDto {
    private Long id;
    private String name;
    private String taskSpec;
    private TaskStatus status;
    private LocalDate deadline;
    private List<SubtaskDto> subtasks;
    private double completionPercentage;

    public TaskDto(Long id, String name, String taskSpec, TaskStatus status, LocalDate deadline, List<SubtaskDto> subtasks, double completionPercentage) {
        this.id = id;
        this.name = name;
        this.taskSpec = taskSpec;
        this.status = status;
        this.deadline = deadline;
        this.subtasks = subtasks;
        this.completionPercentage = completionPercentage;
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

    public String getTaskSpec() {
        return taskSpec;
    }

    public void setTaskSpec(String taskSpec) {
        this.taskSpec = taskSpec;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public List<SubtaskDto> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubtaskDto> subtasks) {
        this.subtasks = subtasks;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}

