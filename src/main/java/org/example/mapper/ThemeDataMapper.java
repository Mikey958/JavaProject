package org.example.mapper;

import org.example.model.Theme;
import org.example.model.Group;
import org.example.model.Student;
import org.example.model.Task;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.HashMap;
import java.util.List;

/**
 * Класс для преобразования данных групп и их студентов в формат,
 * подходящий для отображения на графиках с помощью библиотеки JFreeChart.
 */
public class ThemeDataMapper {

    /**
     * Создает набор данных для графика, отображающего баллы по группам.
     * Для каждой группы суммируются баллы всех студентов и их заданий.
     *
     * @param groups список групп
     * @return CategoryDataset с данными для графика
     */
    public static CategoryDataset createScoresByGroups(List<Group> groups) {

        HashMap<String, Double> scores = new HashMap<>();
        for (Group group : groups) {
            double score = 0;
            // Проходим по студентам группы и их заданиям
            for(Student student : group.getStudents()) {
                for(Theme theme : student.getScoreForThemes()) {
                    for (Task task : theme.getTasks()) {
                        score += task.getScore();
                    }
                }
            }
            // Добавляем результат в набор данных
            scores.put(group.getName(), score);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Добавляем данные в график
        scores.forEach((name, score) -> dataset.addValue(score, "Score", name));
        return dataset;
    }

    /**
     * Создает набор данных для графика, отображающего баллы по темам для всех групп.
     * Для каждой группы и каждой темы суммируются баллы всех студентов.
     *
     * @param groups список групп
     * @return CategoryDataset с данными для графика
     */
    public static CategoryDataset createScoresByThemes(List<Group> groups) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Для каждой группы проходим по всем темам
        for (Group group : groups) {
            // Предполагается, что все студенты группы имеют одинаковые темы
            for (Theme theme : group.getStudents().get(0).getScoreForThemes()) {
                double totalScore = 0;
                // Для каждого студента проверяем баллы по каждой теме
                for (Student student : group.getStudents()) {
                    for (Theme studentTheme : student.getScoreForThemes()) {
                        if (studentTheme.getName().equals(theme.getName())) {
                            for (Task task : studentTheme.getTasks()) {
                                totalScore += task.getScore();
                            }
                        }
                    }
                }
                // Добавляем данные по теме для группы
                dataset.addValue(totalScore, group.getName(), theme.getName());
            }
        }

        return dataset;
    }

    /**
     * Создает набор данных для графика, отображающего баллы по группам для конкретной темы.
     * Фильтрует темы, начиная с указанного номера, и суммирует баллы для каждой группы.
     *
     * @param groups список групп
     * @param themeNumber номер темы для фильтрации
     * @return CategoryDataset с данными для графика
     */
    public static CategoryDataset createScoresThemesByGroups(List<Group> groups, String themeNumber) {

        HashMap<String, Double> scoresTheme = new HashMap<>();
        Theme currentTheme = null;

        // Для каждой группы суммируем баллы по теме, начинающейся с указанного номера
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

            // Проверка, что тема найдена
            if (currentTheme == null) {
                throw new RuntimeException("No theme found for Theme number " + themeNumber);
            }
            // Добавляем результат по группе
            scoresTheme.put(group.getName(), score);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Добавляем данные по теме и группе
        Theme finalCurrentTheme = currentTheme;
        scoresTheme.forEach((name, score) -> dataset.addValue(score, "Score by " + finalCurrentTheme.getName(), name));
        return dataset;
    }
}
