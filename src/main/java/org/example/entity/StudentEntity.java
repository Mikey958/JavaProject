package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * ��������, �������������� ��������.
 * ������ ���������� � �������� � ��� �������.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class StudentEntity {

    /**
     * ���������� ������������� ��������.
     */
    @Id
    private String id;

    /**
     * ��� ��������.
     */
    private String name;

    /**
     * ������, � ������� ����������� �������.
     * ����� � ��������� {@link GroupEntity}.
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private GroupEntity group;

    /**
     * ��������� ������ ��������.
     */
    private Integer finalScore;

    /**
     * ������ ��� � ��������, ����������� � ��������.
     * ����� � ��������� {@link ThemeEntity}.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    private List<ThemeEntity> scoreForThemes;

    /**
     * ������ �������������� ������ ���.
     * ������������ �������� ������, ���� �� ��� �� ��� ���������������.
     *
     * @return ������ ���
     */
    public List<ThemeEntity> getScoreForThemes() {
        if (scoreForThemes == null) {
            scoreForThemes = new ArrayList<>();
        }
        return scoreForThemes;
    }
}
