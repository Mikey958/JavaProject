package org.example.mapper;

import org.example.entity.ThemeEntity;
import org.example.model.Theme;
import java.util.Collections;
import java.util.Optional;

/**
 * Маппер для преобразования объектов типа {@link Theme} в сущности {@link ThemeEntity}.
 * Этот класс используется для маппинга данных между моделью и сущностью, чтобы сохранить объект в базу данных.
 */
public class ThemeMapper {

    /**
     * Преобразует объект модели {@link Theme} в сущность {@link ThemeEntity}.
     *
     * @param theme объект модели {@link Theme}, который необходимо преобразовать.
     * @return объект сущности {@link ThemeEntity}, соответствующий переданной теме.
     */
    public static ThemeEntity toEntity(Theme theme) {
        if (theme == null) {
            return null; // Если переданная тема равна null, возвращаем null
        }

        ThemeEntity themeEntity = new ThemeEntity();

        // Преобразуем название темы, если оно null, используем дефолтное значение "Unnamed Theme"
        themeEntity.setName(Optional.ofNullable(theme.getName()).orElse("Unnamed Theme"));

        // Преобразуем список задач, если он существует, иначе используем пустой список
        themeEntity.setTasks(
                Optional.ofNullable(theme.getTasks()) // Проверяем, есть ли задачи
                        .filter(tasks -> !tasks.isEmpty()) // Проверяем, что список задач не пуст
                        .map(tasks -> tasks.stream() // Преобразуем каждую задачу с помощью TaskMapper
                                .map(TaskMapper::toEntity) // Для каждой задачи вызываем маппер
                                .toList()) // Собираем задачи в новый список
                        .orElse(Collections.emptyList()) // Если задач нет, возвращаем пустой список
        );

        return themeEntity;
    }
}
