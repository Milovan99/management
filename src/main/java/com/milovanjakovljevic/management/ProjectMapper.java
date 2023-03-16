package com.milovanjakovljevic.management;

import com.milovanjakovljevic.management.dto.ProjectDto;
import com.milovanjakovljevic.management.dto.SubtaskDto;
import com.milovanjakovljevic.management.dto.TaskDto;
import com.milovanjakovljevic.management.models.Project;
import com.milovanjakovljevic.management.models.Subtask;
import com.milovanjakovljevic.management.models.Task;
import com.milovanjakovljevic.management.models.TaskStatus;
import com.milovanjakovljevic.management.services.ProjectService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public static ProjectDto toProjectDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDeadline(project.getDeadlineDate());
        projectDto.setCompletionPercentage((int) project.calculateCompletionPercentage());
        return projectDto;
    }


}
