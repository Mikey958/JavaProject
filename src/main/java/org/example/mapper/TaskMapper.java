package org.example.mapper;

import org.example.entity.TaskEntity;
import org.example.model.Task;
import java.util.Optional;

/**
 * Класс для преобразования объектов {@link Task} в сущности {@link TaskEntity}.
 * Он выполняет маппинг данных между моделью и сущностью для работы с базой данных.
 */
public class TaskMapper {

    /**
     * Преобразует объект модели {@link Task} в сущность {@link TaskEntity}.
     * Если какие-либо поля модели имеют значение {@code null}, то для них
     * устанавливаются значения по умолчанию.
     *
     * @param task объект модели {@link Task}, который необходимо преобразовать в сущность
     * @return преобразованная сущность {@link TaskEntity}, либо {@code null}, если входной объект {@link Task} равен {@code null}
     */
    public static TaskEntity toEntity(Task task) {
        // Если входной объект task равен null, возвращаем null
        if (task == null) {
            return null;
        }

        // Создаем новую сущность TaskEntity
        TaskEntity taskEntity = new TaskEntity();

        // Устанавливаем значения для полей, используя Optional для предотвращения null-значений
        taskEntity.setName(Optional.ofNullable(task.getName()).orElse("Unknown"));  // Название задания, если null, устанавливаем "Unknown"
        taskEntity.setScore(Optional.ofNullable(task.getScore()).orElse(0));         // Балл, если null, устанавливаем 0
        taskEntity.setType(task.getType());                                           // Тип задания

        return taskEntity;  // Возвращаем преобразованную сущность
    }
}
