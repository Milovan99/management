package com.milovanjakovljevic.management.dto;

import com.milovanjakovljevic.management.models.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public class SubtaskDto {
    private Long id;
    private String name;
    private String taskSpecification;
    private TaskStatus status;
    private LocalDate deadline;
    private List<SubtaskDto> subtasks;

    public SubtaskDto(Long id, String name, String taskSpecification, TaskStatus status, LocalDate deadline, List<SubtaskDto> subtasks) {
        this.id = id;
        this.name = name;
        this.taskSpecification = taskSpecification;
        this.status = status;
        this.deadline = deadline;
        this.subtasks = subtasks;
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

    public String getTaskSpecification() {
        return taskSpecification;
    }

    public void setTaskSpecification(String taskSpecification) {
        this.taskSpecification = taskSpecification;
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
}