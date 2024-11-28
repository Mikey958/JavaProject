package org.example.model;

import lombok.*;
import java.util.List;

@Builder

public record Group(String name, List<Student> students) {

}
