package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.TaskType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Сущность, представляющая задачу.
 * Хранит информацию о задаче, ее типе и баллах.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class TaskEntity {

    /**
     * Уникальный идентификатор задачи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название задачи.
     * Это поле не может быть пустым или null.
     */
    @NotBlank(message = "Name of the task cannot be blank")
    private String name;

    /**
     * Баллы за выполнение задачи.
     * Это поле должно быть положительным числом.
     */
    @Min(value = 0, message = "Score must be a non-negative number")
    private Integer score;

    /**
     * Тип задачи.
     * Использует перечисление {@link TaskType}.
     * Это поле не может быть null.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

}
