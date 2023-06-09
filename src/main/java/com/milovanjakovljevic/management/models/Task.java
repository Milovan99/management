package com.milovanjakovljevic.management.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String taskSpecification;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate deadline;

    private int completionPercentage;
    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "parentTask",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Subtask> subtasks=new ArrayList<>();

    public Task(long id, String name, String taskSpecification, TaskStatus status, LocalDate deadline, int completionPercentage, Project project, List<Subtask> subtasks) {
        this.id = id;
        this.name = name;
        this.taskSpecification = taskSpecification;
        this.status = status;
        this.deadline = deadline;
        this.completionPercentage = completionPercentage;
        this.project = project;
        this.subtasks = subtasks;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public int calculateCompletionPercentage() {
        List<Subtask> subtasks = getSubtasks();
        int totalSubtasks = subtasks.size();
        int completedSubtasks = (int) subtasks.stream()
                .filter(subtask -> subtask.getStatus() == TaskStatus.DONE)
                .count();
        if (totalSubtasks == 0) {
            return 0;
        } else {
            return (int) ((completedSubtasks * 100.0) / totalSubtasks);
        }
    }
}
