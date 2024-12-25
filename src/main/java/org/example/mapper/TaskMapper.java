package org.example.mapper;

import org.example.entity.TaskEntity;
import org.example.model.Task;
import java.util.Optional;

/**
 * ����� ��� �������������� �������� {@link Task} � �������� {@link TaskEntity}.
 * �� ��������� ������� ������ ����� ������� � ��������� ��� ������ � ����� ������.
 */
public class TaskMapper {

    /**
     * ����������� ������ ������ {@link Task} � �������� {@link TaskEntity}.
     * ���� �����-���� ���� ������ ����� �������� {@code null}, �� ��� ���
     * ��������������� �������� �� ���������.
     *
     * @param task ������ ������ {@link Task}, ������� ���������� ������������� � ��������
     * @return ��������������� �������� {@link TaskEntity}, ���� {@code null}, ���� ������� ������ {@link Task} ����� {@code null}
     */
    public static TaskEntity toEntity(Task task) {
        // ���� ������� ������ task ����� null, ���������� null
        if (task == null) {
            return null;
        }

        // ������� ����� �������� TaskEntity
        TaskEntity taskEntity = new TaskEntity();

        // ������������� �������� ��� �����, ��������� Optional ��� �������������� null-��������
        taskEntity.setName(Optional.ofNullable(task.getName()).orElse("Unknown"));  // �������� �������, ���� null, ������������� "Unknown"
        taskEntity.setScore(Optional.ofNullable(task.getScore()).orElse(0));         // ����, ���� null, ������������� 0
        taskEntity.setType(task.getType());                                           // ��� �������

        return taskEntity;  // ���������� ��������������� ��������
    }
}
