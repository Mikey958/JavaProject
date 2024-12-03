package org.example.mapper;

import org.example.entity.GroupEntity;
import org.example.entity.StudentEntity;
import org.example.model.Student;

import java.util.Collections;
import java.util.Optional;

public class StudentMapper {
    public static StudentEntity toEntity(Student student) {
        if (student == null) {
            return null;
        }

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(student.getId());
        studentEntity.setName(student.getName());
        studentEntity.setFinalScore(student.getFinalScore());

        studentEntity.setScoreForThemes(
                Optional.ofNullable(student.getScoreForThemes())
                        .map(scoreForThemes -> scoreForThemes.stream()
                                .map(ThemeMapper::toEntity)
                                .toList())
                        .orElse(Collections.emptyList()) // Если список тем пуст или null
        );

        return studentEntity;
    }
}
