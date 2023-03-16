package com.milovanjakovljevic.management;

import com.milovanjakovljevic.management.Requests.UpdateTaskStatusRequest;
import com.milovanjakovljevic.management.dto.ProjectDto;
import com.milovanjakovljevic.management.models.Project;
import com.milovanjakovljevic.management.models.Task;
import com.milovanjakovljevic.management.services.ProjectService;
import com.milovanjakovljevic.management.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ManagementController {

    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Long taskId, @RequestBody UpdateTaskStatusRequest request){
        Task task = TaskService.getTaskById(taskId);
        task.setStatus(request.getStatus());
        TaskService.saveTask(task);
        ProjectService.updateProjectStatus(task.getProject());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDto>> getProjects(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable paging = (Pageable) PageRequest.of(page, size, Sort.by("deadline").ascending());
        Page<Project> projectPage =ProjectService.getPaginatedProjects(paging);

        ProjectMapper projectMapper = new ProjectMapper();
        List<ProjectDto> projectDtos = projectPage.getContent().stream()
                .map(project -> ProjectMapper.toProjectDto(project))
                .collect(Collectors.toList());
        return ResponseEntity.ok(projectDtos);
    }

}
