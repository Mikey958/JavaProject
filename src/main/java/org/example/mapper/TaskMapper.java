package org.example.mapper;

import org.example.entity.TaskEntity;
import org.example.model.Task;
import java.util.Optional;

public class TaskMapper {

    public static TaskEntity toEntity(Task task) {
        if (task == null) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setName(Optional.ofNullable(task.getName()).orElse("Unknown"));
        taskEntity.setScore(Optional.ofNullable(task.getScore()).orElse(0));
        taskEntity.setType(task.getType());

        return taskEntity;
    }
}
