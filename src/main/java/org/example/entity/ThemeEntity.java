package org.example.entity;

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
@Table(name = "themes")
public class ThemeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "theme_id")
    private List<TaskEntity> tasks = new ArrayList<>(); // Ленивая инициализация списка задач

    /**
     * Метод для добавления задачи в тему. Проверяется, чтобы не добавлять дублирующиеся задачи.
     *
     * @param task задача, которую нужно добавить в тему
     */
    public void addTask(TaskEntity task) {
        if (task != null && !this.tasks.contains(task)) {
            this.tasks.add(task);
        }
    }

    /**
     * Метод для удаления задачи из темы. Проверяется, чтобы не удалить задачу, которой нет в списке.
     *
     * @param task задача, которую нужно удалить из темы
     */
    public void removeTask(TaskEntity task) {
        if (task != null && this.tasks.contains(task)) {
            this.tasks.remove(task);
        }
    }

    /**
     * Метод для инициализации списка задач перед сохранением объекта в базу данных.
     * Это поможет избежать проблем с null, если список задач не был инициализирован.
     */
    @PrePersist
    private void prePersist() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
    }
}
