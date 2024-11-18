package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private String name;                // Название курса
    private List<Student> students;     // Список учеников
    private List<Theme> themes;         // Список тем


}
