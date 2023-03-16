package com.milovanjakovljevic.management.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate deadlineDate;
    private String clientName;
    private String shortDescription;

    private int completionPercentage;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)

    private List<Task> task=new ArrayList<>();

    public Project(Long id, String name, LocalDate startDate, LocalDate deadlineDate, String clientName, String shortDescription, int completionPercentage, ProjectStatus status, List<Task> task) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.deadlineDate = deadlineDate;
        this.clientName = clientName;
        this.shortDescription = shortDescription;
        this.completionPercentage = completionPercentage;
        this.status = status;
        this.task = task;
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

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
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

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }
    public double calculateCompletionPercentage() {
        List<Task> tasks = this.task;
        int totalSubtasks = 0;
        int completedSubtasks = 0;

        for (Task task : tasks) {
            List<Subtask> subtasks = task.getSubtasks();
            totalSubtasks += subtasks.size();

            for (Subtask subtask : subtasks) {
                if (subtask.getStatus() == TaskStatus.DONE) {
                    completedSubtasks++;
                }
            }
        }

        if (totalSubtasks == 0) {
            return 0.0;
        }

        return (completedSubtasks * 100.0) / totalSubtasks;
    }
}
