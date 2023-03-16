package com.milovanjakovljevic.management.services;

import com.milovanjakovljevic.management.models.*;
import com.milovanjakovljevic.management.repositories.ProjectRepository;
import com.milovanjakovljevic.management.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
