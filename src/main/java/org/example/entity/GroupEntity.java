package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Добавлено ограничение для имени группы
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Скрытие данных студентов в API
    private List<StudentEntity> students = new ArrayList<>();

    /**
     * Добавление студента в группу.
     * @param student студент, которого нужно добавить.
     */
    public void addStudent(StudentEntity student) {
        students.add(student);
        student.setGroup(this);
    }

    /**
     * Удаление студента из группы.
     * @param student студент, которого нужно удалить.
     */
    public void removeStudent(StudentEntity student) {
        students.remove(student);
        student.setGroup(null);
    }
}
