package org.example.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Theme(String name, List<Task> tasks){

}
