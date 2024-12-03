package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.TaskType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tasks")

public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

}
