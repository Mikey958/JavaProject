package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private final String fullName;  // Фамилия
    private final String ulearnId;  // Ulearn id
    private final String group;     // Группа
}