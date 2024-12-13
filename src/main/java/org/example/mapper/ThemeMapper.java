package org.example.mapper;

import org.example.entity.ThemeEntity;
import org.example.model.Theme;
import java.util.Collections;
import java.util.Optional;

public class ThemeMapper {

    public static ThemeEntity toEntity(Theme theme) {
        if (theme == null) {
            return null; // ������ �� null ��������
        }

        ThemeEntity themeEntity = new ThemeEntity();

        // ����������� ���� name
        themeEntity.setName(Optional.ofNullable(theme.getName()).orElse("Unnamed Theme")); // ���� name null, ���������� "Unnamed Theme"

        // ����������� ������ tasks, �������� �� null ��� �������
        themeEntity.setTasks(
                Optional.ofNullable(theme.getTasks())
                        .filter(tasks -> !tasks.isEmpty()) // ��������, ���� ������ tasks ����
                        .map(tasks -> tasks.stream()
                                .map(TaskMapper::toEntity)
                                .toList())
                        .orElse(Collections.emptyList()) // ���� tasks null ��� ������, ������ ������ ������
        );

        return themeEntity;
    }
}
