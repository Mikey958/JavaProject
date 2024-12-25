package org.example.mapper;

import org.example.entity.StudentEntity;
import org.example.model.Student;
import java.util.Collections;
import java.util.Optional;

/**
 * Маппер для преобразования объектов типа {@link Student} в сущности {@link StudentEntity} и наоборот.
 * Этот класс используется для маппинга данных, чтобы сохранить объект в базу данных или наоборот.
 */
public class StudentMapper {

    /**
     * Преобразует объект модели {@link Student} в сущность {@link StudentEntity}.
     *
     * @param student объект модели {@link Student}, который необходимо преобразовать.
     * @return объект сущности {@link StudentEntity}, соответствующий переданному студенту.
     */
    public static StudentEntity toEntity(Student student) {
        if (student == null) {
            return null; // Если студент равен null, возвращаем null.
        }

        StudentEntity studentEntity = new StudentEntity();

        // Маппинг полей
        studentEntity.setId(student.getId()); // Идентификатор студента
        studentEntity.setName(student.getName()); // Имя студента
        studentEntity.setFinalScore(student.getFinalScore()); // Итоговый балл студента

        // Маппинг списка тем и их оценок
        studentEntity.setScoreForThemes(
                Optional.ofNullable(student.getScoreForThemes()) // Если темы существуют
                        .map(scoreForThemes -> scoreForThemes.stream() // Преобразуем список тем
                                .map(ThemeMapper::toEntity) // Для каждой темы вызываем маппер
                                .toList()) // Собираем в новый список
                        .orElse(Collections.emptyList()) // Если список тем пуст или null, то возвращаем пустой список
        );

        return studentEntity;
    }
}
