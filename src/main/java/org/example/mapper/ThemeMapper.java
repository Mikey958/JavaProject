package org.example.mapper;

import org.example.entity.ThemeEntity;
import org.example.model.Theme;
import java.util.Collections;
import java.util.Optional;

public class ThemeMapper {

    public static ThemeEntity toEntity(Theme theme) {
        if (theme == null) {
            return null; // Защита от null значений
        }

        ThemeEntity themeEntity = new ThemeEntity();

        // Преобразуем поле name
        themeEntity.setName(Optional.ofNullable(theme.getName()).orElse("Unnamed Theme")); // если name null, используем "Unnamed Theme"

        // Преобразуем список tasks, проверяя на null или пустоту
        themeEntity.setTasks(
                Optional.ofNullable(theme.getTasks())
                        .filter(tasks -> !tasks.isEmpty()) // Проверка, если список tasks пуст
                        .map(tasks -> tasks.stream()
                                .map(TaskMapper::toEntity)
                                .toList())
                        .orElse(Collections.emptyList()) // Если tasks null или пустой, ставим пустой список
        );

        return themeEntity;
    }
}
