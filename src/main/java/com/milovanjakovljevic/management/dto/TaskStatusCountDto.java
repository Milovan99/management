package com.milovanjakovljevic.management.dto;

import com.milovanjakovljevic.management.models.TaskStatus;

public class TaskStatusCountDto {
    private TaskStatus status;
    private long count;

    public TaskStatusCountDto(TaskStatus status, long count) {
        this.status = status;
        this.count = count;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
