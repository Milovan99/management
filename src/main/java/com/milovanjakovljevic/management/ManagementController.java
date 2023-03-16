package com.milovanjakovljevic.management;

import com.milovanjakovljevic.management.Requests.UpdateTaskStatusRequest;
import com.milovanjakovljevic.management.models.Task;
import com.milovanjakovljevic.management.services.ProjectService;
import com.milovanjakovljevic.management.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
