package org.example.model;

import java.util.List;

public class Statistics {

    // Метод для расчета среднего балла по списку оценок
    public double calculateAverageScore(List<Grade> grades) {
        return grades.stream()
                .mapToInt(Grade::getScore)
                .average()
                .orElse(0);
    }

    // Метод для расчета максимального балла
    public int calculateMaxScore(List<Grade> grades) {
        return grades.stream()
                .mapToInt(Grade::getScore)
                .max()
                .orElse(0);
    }

    // Метод для расчета минимального балла
    public int calculateMinScore(List<Grade> grades) {
        return grades.stream()
                .mapToInt(Grade::getScore)
                .min()
                .orElse(0);
    }

    // Метод для расчета количества оценок по конкретной теме
    public int countGradesByTheme(List<Grade> grades, Theme theme) {
        return (int) grades.stream()
                .filter(grade -> grade.getTheme().equals(theme))
                .count();
    }

    // Метод для расчета средней оценки по группе
    public double calculateAverageScoreByGroup(List<Grade> grades, String group) {
        return grades.stream()
                .filter(grade -> grade.getStudent().getGroup().equals(group))
                .mapToInt(Grade::getScore)
                .average()
                .orElse(0);
    }

    // Метод для расчета средней оценки по типу задания
    public double calculateAverageScoreByTaskType(List<Grade> grades, TaskType taskType) {
        return grades.stream()
                .filter(grade -> grade.getTask().getType() == taskType)
                .mapToInt(Grade::getScore)
                .average()
                .orElse(0);
    }

    // Метод для подсчета общего количества оценок
    public int countTotalGrades(List<Grade> grades) {
        return grades.size();
    }

    // Метод для подсчета общего количества студентов
    public int countTotalStudents(List<Grade> grades) {
        return (int) grades.stream()
                .map(Grade::getStudent)
                .distinct()
                .count();
    }
}
