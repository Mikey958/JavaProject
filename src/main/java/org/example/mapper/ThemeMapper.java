package org.example.mapper;

import org.example.entity.ThemeEntity;
import org.example.model.Theme;
import java.util.Collections;
import java.util.Optional;

/**
 * ������ ��� �������������� �������� ���� {@link Theme} � �������� {@link ThemeEntity}.
 * ���� ����� ������������ ��� �������� ������ ����� ������� � ���������, ����� ��������� ������ � ���� ������.
 */
public class ThemeMapper {

    /**
     * ����������� ������ ������ {@link Theme} � �������� {@link ThemeEntity}.
     *
     * @param theme ������ ������ {@link Theme}, ������� ���������� �������������.
     * @return ������ �������� {@link ThemeEntity}, ��������������� ���������� ����.
     */
    public static ThemeEntity toEntity(Theme theme) {
        if (theme == null) {
            return null; // ���� ���������� ���� ����� null, ���������� null
        }

        ThemeEntity themeEntity = new ThemeEntity();

        // ����������� �������� ����, ���� ��� null, ���������� ��������� �������� "Unnamed Theme"
        themeEntity.setName(Optional.ofNullable(theme.getName()).orElse("Unnamed Theme"));

        // ����������� ������ �����, ���� �� ����������, ����� ���������� ������ ������
        themeEntity.setTasks(
                Optional.ofNullable(theme.getTasks()) // ���������, ���� �� ������
                        .filter(tasks -> !tasks.isEmpty()) // ���������, ��� ������ ����� �� ����
                        .map(tasks -> tasks.stream() // ����������� ������ ������ � ������� TaskMapper
                                .map(TaskMapper::toEntity) // ��� ������ ������ �������� ������
                                .toList()) // �������� ������ � ����� ������
                        .orElse(Collections.emptyList()) // ���� ����� ���, ���������� ������ ������
        );

        return themeEntity;
    }
}
