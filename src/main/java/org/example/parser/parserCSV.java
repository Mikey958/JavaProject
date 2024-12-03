package org.example.parser;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.example.model.Theme;
import org.example.model.Student;
import org.example.model.Task;
import org.example.model.TaskType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class parserCSV {

    public static List<Student> readCSVFile(String file){
        var parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();
        try (var reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream(file), "windows-1251"))
                .withCSVParser(parser)
                .build()) {
            return parseStudents(reader.readAll());
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Student> parseStudents(List<String[]> lines) {
        List<Student> students = new ArrayList<>();

        // Начинаем с 3-й строки (0, 1, 2 - это заголовки)
        for (int i = 3; i < lines.size(); i++) {
            String[] line = lines.get(i);

            String name = line[0];  // Имя
            String id = line[1];     // ID
            String group = line[2];  // Группа

            // Сумма оценок с безопасным парсингом
            int finalScore = 0;
            try {
                finalScore = Integer.parseInt(line[3]) + Integer.parseInt(line[4]) + Integer.parseInt(line[5]);
            } catch (NumberFormatException e) {
                // Обработка ошибки, если оценка не может быть преобразована в число
                finalScore = 0;  // Можно установить значение по умолчанию, если произошла ошибка
            }

            // Парсим главы (темы) для студента
            var scoreForThemes = parseThemes(lines, i);

            // Создаем студента и добавляем его в список
            Student student = new Student(name, id, group, finalScore, scoreForThemes);
            students.add(student);
        }

        return students;
    }


    private static List<Theme> parseThemes(List<String[]> lines, int indexStudent) {
        List<Theme> themes = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        // Получаем строки для глав и задач
        String[] lineChapters = lines.get(0);  // Заголовок глав
        String[] lineTasks = lines.get(1);     // Заголовок задач

        String currentThemeName = lineChapters[6];  // Начальное имя темы

        // Итерируем по строкам начиная с 6-го индекса
        for (int i = 6; i < lineChapters.length; i++) {
            // Если встретили новую тему, добавляем текущую в список
            if (!Objects.equals(lineChapters[i], "") && i != 6) {
                Theme theme = new Theme(currentThemeName, tasks);
                themes.add(theme);
                currentThemeName = lineChapters[i];  // Обновляем название темы
                tasks = new ArrayList<>();  // Сбрасываем список задач
            }

            // Обрабатываем задачи
            Task task = null;
            if (lineTasks[i].startsWith("КВ:")) {
                task = createTask(lineTasks[i], lines.get(indexStudent)[i], TaskType.CONTROL_QUESTION);
            } else if (lineTasks[i].startsWith("УПР:")) {
                task = createTask(lineTasks[i], lines.get(indexStudent)[i], TaskType.EXERCISE);
            } else if (lineTasks[i].startsWith("ДЗ:")) {
                task = createTask(lineTasks[i], lines.get(indexStudent)[i], TaskType.HOMEWORK);
            }

            if (task != null) {
                tasks.add(task);  // Добавляем задачу в список
            }
        }

        // Добавляем последнюю тему
        if (!tasks.isEmpty()) {
            Theme theme = new Theme(currentThemeName, tasks);
            themes.add(theme);
        }

        return themes;
    }

    // Вспомогательный метод для создания задач
    private static Task createTask(String taskName, String scoreStr, TaskType taskType) {
        int score = 0;
        try {
            score = Integer.parseInt(scoreStr);  // Преобразуем строку в число
        } catch (NumberFormatException e) {
            score = 0;  // Если произошла ошибка, ставим 0 баллов
        }
        return new Task(taskName, score, taskType);
    }

}