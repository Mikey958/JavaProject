package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, представляющая группу студентов.
 * Хранит информацию о группе и связанных с ней студентах.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class GroupEntity {

    /**
     * Уникальный идентификатор группы.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название группы. Это поле уникально и не может быть пустым.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Список студентов, принадлежащих к этой группе.
     * Связь с сущностью {@link StudentEntity} через поле group.
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Скрытие списка студентов в API
    private List<StudentEntity> students = new ArrayList<>();

    /**
     * Возвращает список студентов группы с ленивой инициализацией.
     * Если список студентов еще не инициализирован, то создается новый список.
     *
     * @return список студентов
     */
    public List<StudentEntity> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return students;
    }

    /**
     * Добавляет студента в группу, если он еще не добавлен.
     * Обновляет связь между студентом и группой.
     *
     * @param student студент, которого нужно добавить в группу
     */
    public void addStudent(StudentEntity student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
            student.setGroup(this); // Обновление связи
        }
    }

    /**
     * Удаляет студента из группы, если он присутствует.
     * Обновляет связь между студентом и группой.
     *
     * @param student студент, которого нужно удалить из группы
     */
    public void removeStudent(StudentEntity student) {
        if (student != null && students.contains(student)) {
            students.remove(student);
            student.setGroup(null); // Обновление связи
        }
    }

    /**
     * Метод, вызываемый перед сохранением объекта в базу данных.
     * Инициализирует список студентов, если он равен null.
     */
    @PrePersist
    private void prePersist() {
        if (students == null) {
            students = new ArrayList<>();
        }
    }
}
