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
 * Сущность, представляющая студента.
 * Хранит информацию о студенте и его оценках.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class StudentEntity {

    /**
     * Уникальный идентификатор студента.
     */
    @Id
    private String id;

    /**
     * Имя студента.
     */
    private String name;

    /**
     * Группа, к которой принадлежит студент.
     * Связь с сущностью {@link GroupEntity}.
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private GroupEntity group;

    /**
     * Финальная оценка студента.
     */
    private Integer finalScore;

    /**
     * Список тем с оценками, относящихся к студенту.
     * Связь с сущностью {@link ThemeEntity}.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    private List<ThemeEntity> scoreForThemes;

    /**
     * Лениво инициализирует список тем.
     * Обеспечивает создание списка, если он еще не был инициализирован.
     *
     * @return список тем
     */
    public List<ThemeEntity> getScoreForThemes() {
        if (scoreForThemes == null) {
            scoreForThemes = new ArrayList<>();
        }
        return scoreForThemes;
    }
}
