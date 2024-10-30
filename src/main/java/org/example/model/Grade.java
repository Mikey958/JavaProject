package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Grade {
    private Student student; // Ссылка на объект студента
    private Course course;   // Ссылка на объект курса
    private Theme theme;     // Ссылка на объект темы
    private Task task;       // Ссылка на объект задания
    private int score;       // Количество баллов, полученных за задание

    

}
