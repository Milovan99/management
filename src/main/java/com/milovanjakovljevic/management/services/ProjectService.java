package com.milovanjakovljevic.management.services;

import com.milovanjakovljevic.management.dto.*;
import com.milovanjakovljevic.management.models.*;
import com.milovanjakovljevic.management.repositories.ProjectRepository;
import com.milovanjakovljevic.management.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public static Page<ProjectDto> getPaginatedProjects(int pageNo, int pageSize) {
        // Sort by deadline in ascending order
        Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize, Sort.by("deadline").ascending());
        Page<Project> projects = projectRepository.findAll((org.springframework.data.domain.Pageable) pageable);
        List<ProjectDto> projectDtos = projects.getContent().stream().map(this::convertToDto).collect(Collectors.toList());
        return new PageImpl<>(projectDtos, (org.springframework.data.domain.Pageable) pageable, projects.getTotalElements());
    }
    public List<TaskStatusCountDto> countByTaskIdAndStatus(Long taskId) {
        List<TaskStatusCountDto> counts = new ArrayList<>();
        for (TaskStatus status : TaskStatus.values()) {
            long count = projectRepository.countByTaskIdAndStatus(taskId, status);
            counts.add(new TaskStatusCountDto(status, count));
        }
        return counts;
    }
    private ProjectDto convertToDto(Project project) {
        int toDoCount = (int) ProjectRepository.countByProjectIdAndStatus(project.getId(), TaskStatus.TO_DO);
        int inProgressCount = (int) ProjectRepository.countByProjectIdAndStatus(project.getId(), TaskStatus.IN_PROGRESS);
        int doneCount = (int) ProjectRepository.countByProjectIdAndStatus(project.getId(), TaskStatus.DONE);
        double completionPercentage = project.calculateCompletionPercentage();

        List<TaskDto> taskDtos = project.getTask()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new ProjectDto(
                project.getId(),
                project.getName(),
                project.getStartDate(),
                project.getDeadlineDate(),
                project.getClientName(),
                project.getShortDescription(),
                project.getStatus(),
                toDoCount,
                taskDtos
        );
    }
    public static Map<TaskStatus, Integer> countByParentIdAndStatus(Long parentId, TaskStatus toDo) {
        Map<TaskStatus, Integer> counts = new HashMap<>();
        counts.put(TaskStatus.TO_DO, 0);
        counts.put(TaskStatus.IN_PROGRESS, 0);
        counts.put(TaskStatus.DONE, 0);

        List<Task> tasks = taskRepository.findByParentId(parentId);
        for (Task task : tasks) {
            Map<TaskStatus, Integer> taskCounts = (Map<TaskStatus, Integer>) countByTaskIdAndStatus(task.getId());
            for (Map.Entry<TaskStatus, Integer> entry : taskCounts.entrySet()) {
                TaskStatus status = entry.getKey();
                Integer count = entry.getValue();
                counts.put(status, counts.get(status) + count);
            }
        }

        return counts;
    }

    private TaskDto convertToDto(Task task) {
        int toDoCount = countByParentIdAndStatus(task.getId(), TaskStatus.TO_DO).size();
        int inProgressCount = countByParentIdAndStatus(task.getId(), TaskStatus.IN_PROGRESS).size();
        int doneCount = countByParentIdAndStatus(task.getId(), TaskStatus.DONE).size();
        double completionPercentage = task.calculateCompletionPercentage();

        List<SubtaskDto> subtaskDtos = task.getSubtasks()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new TaskDto(task.getId(), task.getName(), task.getTaskSpecification(), task.getStatus(),
                task.getDeadline(), subtaskDtos, completionPercentage);

    }

    private SubtaskDto convertToDto(Subtask subtask) {
        return new SubtaskDto(
                subtask.getId(),
                subtask.getName(),
                subtask.getTaskSpecification(),
                subtask.getStatus(),
                subtask.getDeadline()



        );
    }
}
