package org.example.mapper;

import org.example.model.Theme;
import org.example.model.Group;
import org.example.model.Student;
import org.example.model.Task;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.HashMap;
import java.util.List;

public class ChartDataMapper {

        public static CategoryDataset createScoresByGroups(List<Group> groups) {

        HashMap<String, Double> scores = new HashMap<>();
        for (Group group : groups) {
            double score = 0;
            for(Student student : group.getStudents()) {
                for(Theme theme : student.getScoreForThemes())
                    for (Task task : theme.getTasks()) {
                        score += task.getScore();
                    }
            }
            scores.put(group.getName(), score);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        scores.forEach((name, score) -> dataset.addValue(score, "Score", name));
        return dataset;
    }
    public static CategoryDataset createScoresByThemes(List<Group> groups) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Group group : groups) {
            for (Theme theme : group.getStudents().get(0).getScoreForThemes()) { // Предполагается, что темы одинаковые у всех студентов
                double totalScore = 0;
                for (Student student : group.getStudents()) {
                    for (Theme studentTheme : student.getScoreForThemes()) {
                        if (studentTheme.getName().equals(theme.getName())) {
                            for (Task task : studentTheme.getTasks()) {
                                totalScore += task.getScore();
                            }
                        }
                    }
                }
                dataset.addValue(totalScore, group.getName(), theme.getName()); // Группа — серия, Тема — категория
            }
        }

        return dataset;
    }


    public static CategoryDataset createScoresThemesByGroups(List<Group> groups, String themeNumber) {

        HashMap<String, Double> scoresTheme = new HashMap<>();
        Theme currentTheme = null;
        for (Group group : groups) {
            double score = 0;
            for (Student student : group.getStudents()) {
                for (Theme theme : student.getScoreForThemes())
                    if (theme.getName().startsWith(themeNumber)) {
                        for (Task task : theme.getTasks()) {
                            score += task.getScore();
                        }
                        if (currentTheme == null) {
                            currentTheme = theme;
                        }
                    }
            }
            if (currentTheme == null) {
                throw new RuntimeException("No theme found for Theme number " + themeNumber);
            }
            scoresTheme.put(group.getName(), score);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Theme finalCurrentTheme = currentTheme;
        scoresTheme.forEach((name, score) -> dataset.addValue(score, "Score by " + finalCurrentTheme.getName(), name));
        return dataset;
    }
}