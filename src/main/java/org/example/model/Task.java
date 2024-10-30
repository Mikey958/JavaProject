package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {
    private int score;         // Количество баллов
    private TaskType type;     // Тип задания
}
