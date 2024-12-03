package org.example.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Theme{
    private String name;
    private List<Task> tasks;
}