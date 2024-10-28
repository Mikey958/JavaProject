package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Course {
    private String name;                // Название курса
    private List<Theme> themes;         // Список тем
    private List<Student> students;     // Список учеников

}
