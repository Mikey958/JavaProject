package org.example.model;

import lombok.Builder;

@Builder
public record Task(String name ,Integer score, TaskType type) {

}
