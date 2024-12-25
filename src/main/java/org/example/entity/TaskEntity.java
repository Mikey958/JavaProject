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
 * ��������, �������������� ������.
 * ������ ���������� � ������, �� ���� � ������.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class TaskEntity {

    /**
     * ���������� ������������� ������.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * �������� ������.
     * ��� ���� �� ����� ���� ������ ��� null.
     */
    @NotBlank(message = "Name of the task cannot be blank")
    private String name;

    /**
     * ����� �� ���������� ������.
     * ��� ���� ������ ���� ������������� ������.
     */
    @Min(value = 0, message = "Score must be a non-negative number")
    private Integer score;

    /**
     * ��� ������.
     * ���������� ������������ {@link TaskType}.
     * ��� ���� �� ����� ���� null.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

}
