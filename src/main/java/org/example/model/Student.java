package org.example.model;

import lombok.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Student{
    private String name;
    private String id;
    private String group;
    private Integer finalScore;
    private List<Theme> scoreForThemes;


}