package com.milovanjakovljevic.management.Requests;

import com.milovanjakovljevic.management.models.TaskStatus;

public class UpdateTaskStatusRequest {
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
