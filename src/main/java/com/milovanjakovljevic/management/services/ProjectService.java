package com.milovanjakovljevic.management.services;

import com.milovanjakovljevic.management.dto.ProjectTaskCountDto;
import com.milovanjakovljevic.management.dto.TaskStatusCountDto;
import com.milovanjakovljevic.management.models.*;
import com.milovanjakovljevic.management.repositories.ProjectRepository;
import com.milovanjakovljevic.management.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private static ProjectRepository projectRepository;

    @Autowired
    private static TaskRepository taskRepository;

    public static void updateProjectStatus(Project project) {
        List<Task> tasks = taskRepository.findByProjectId(project.getId());
        boolean allTasksDone = tasks.stream().allMatch(task -> task.getStatus() == TaskStatus.DONE);
        boolean allTasksToDo = tasks.stream().allMatch(task -> task.getStatus() == TaskStatus.TO_DO);

        if (allTasksDone) {
            project.setStatus(ProjectStatus.FINISHED);
        } else if (allTasksToDo) {
            project.setStatus(ProjectStatus.PLANNING);
        } else {
            project.setStatus(ProjectStatus.DEVELOPMENT);
        }

        projectRepository.save(project);
    }

    public void updateTaskStatus(Task task) {
        List<Subtask> subtasks = task.getSubtasks();
        boolean allSubtasksDone = subtasks.stream().allMatch(subtask -> subtask.getStatus() == TaskStatus.DONE.DONE);
        boolean allSubtasksToDo = subtasks.stream().allMatch(subtask -> subtask.getStatus() == TaskStatus.TO_DO.TO_DO);

        if (allSubtasksDone) {
            task.setStatus(TaskStatus.DONE);
        } else if (allSubtasksToDo) {
            task.setStatus(TaskStatus.TO_DO);
        } else {
            task.setStatus(TaskStatus.IN_PROGRESS);
        }

        taskRepository.save(task);
        updateProjectStatus(task.getProject());
    }

    public int calculateProjectCompletionPercentage(Project project) {
        List<Task> tasks = taskRepository.findByProjectId(project.getId());
        int totalTasks = tasks.size();
        int completedTasks = 0;

        for (Task task : tasks) {
            List<Subtask> subtasks = task.getSubtasks();
            int totalSubtasks = subtasks.size();
            int completedSubtasks = 0;

            for (Subtask subtask : subtasks) {
                if (subtask.getStatus() == TaskStatus.DONE) {
                    completedSubtasks++;
                }
            }

            if (totalSubtasks > 0) {
                double subtaskCompletionPercentage = (double) completedSubtasks / totalSubtasks * 100;
                double taskCompletionPercentage = subtaskCompletionPercentage / 100;
                completedTasks += taskCompletionPercentage;
            } else {
                if (task.getStatus() == TaskStatus.DONE) {
                    completedTasks++;
                }
            }
        }

        if (totalTasks > 0) {
            return (int) (completedTasks / totalTasks * 100);
        } else {
            return 0;
        }
    }
    public List<ProjectTaskCountDto> getProjectTaskCounts(int pageNumber, int pageSize) {
        Pageable pageRequest = (Pageable) PageRequest.of(pageNumber, pageSize, Sort.by("deadline").ascending());
        Page<Project> projects = projectRepository.findAll((org.springframework.data.domain.Pageable) pageRequest);

        List<ProjectTaskCountDto> projectTaskCounts = new ArrayList<>();
        for (Project project : projects.getContent()) {
            List<TaskStatusCountDto> taskStatusCounts = new ArrayList<>();
            taskStatusCounts.add(new TaskStatusCountDto(TaskStatus.TO_DO, taskRepository.countByProjectIdAndStatus(project.getId(), TaskStatus.TO_DO)));
            taskStatusCounts.add(new TaskStatusCountDto(TaskStatus.IN_PROGRESS, taskRepository.countByProjectIdAndStatus(project.getId(), TaskStatus.IN_PROGRESS)));
            taskStatusCounts.add(new TaskStatusCountDto(TaskStatus.DONE, taskRepository.countByProjectIdAndStatus(project.getId(), TaskStatus.DONE)));
            projectTaskCounts.add(new ProjectTaskCountDto(project, taskStatusCounts));
        }
        return projectTaskCounts;
    }
}
