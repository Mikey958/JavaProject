package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "themes")
public class ThemeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "theme_id")
    private List<TaskEntity> tasks = new ArrayList<>(); // ������� ������������� ������ �����

    /**
     * ����� ��� ���������� ������ � ����. �����������, ����� �� ��������� ������������� ������.
     *
     * @param task ������, ������� ����� �������� � ����
     */
    public void addTask(TaskEntity task) {
        if (task != null && !this.tasks.contains(task)) {
            this.tasks.add(task);
        }
    }

    /**
     * ����� ��� �������� ������ �� ����. �����������, ����� �� ������� ������, ������� ��� � ������.
     *
     * @param task ������, ������� ����� ������� �� ����
     */
    public void removeTask(TaskEntity task) {
        if (task != null && this.tasks.contains(task)) {
            this.tasks.remove(task);
        }
    }

    /**
     * ����� ��� ������������� ������ ����� ����� ����������� ������� � ���� ������.
     * ��� ������� �������� ������� � null, ���� ������ ����� �� ��� ���������������.
     */
    @PrePersist
    private void prePersist() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
    }
}
