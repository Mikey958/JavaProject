package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Theme {
    private String name;                   // Название темы
    private List<Task> tasks;              // Список заданий
}
