package org.example.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    private String ulearnId;  // Ulearn id
    private String fullName;  // Фамилия
    private int score;        // Кол-во баллов
    private String group;     // Группа
}