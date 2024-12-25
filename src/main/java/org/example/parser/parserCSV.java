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

public class parserCSV {

    private static final int STUDENT_DATA_START_INDEX = 3;
    private static final int TASKS_START_COLUMN = 6;

    public static List<Student> readCSVFile(String file) {
        var parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();
        try (var reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream(file), "windows-1251"))
                .withCSVParser(parser)
                .build()) {
            return parseStudents(reader.readAll());
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }
    }

    private static List<Student> parseStudents(List<String[]> lines) {
        List<Student> students = new ArrayList<>();

        for (int i = STUDENT_DATA_START_INDEX; i < lines.size(); i++) {
            String[] line = lines.get(i);

            String name = line[0];
            String id = line[1];
            String group = line[2];

            int finalScore = parseFinalScore(line);

            List<Theme> scoreForThemes = parseThemes(lines, i);

            Student student = new Student(name, id, group, finalScore, scoreForThemes);
            students.add(student);
        }

        return students;
    }

    private static int parseFinalScore(String[] line) {
        try {
            return Integer.parseInt(line[3]) + Integer.parseInt(line[4]) + Integer.parseInt(line[5]);
        } catch (NumberFormatException e) {
            return 0; // Default score in case of error
        }
    }

    private static List<Theme> parseThemes(List<String[]> lines, int indexStudent) {
        List<Theme> themes = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        String[] headerThemes = lines.get(0);
        String[] headerTasks = lines.get(1);

        String currentChapterName = headerThemes[TASKS_START_COLUMN];

        for (int i = TASKS_START_COLUMN; i < headerThemes.length; i++) {
            if (!headerThemes[i].isEmpty() && i != TASKS_START_COLUMN) {
                themes.add(new Theme(currentChapterName, tasks));
                currentChapterName = headerThemes[i];
                tasks = new ArrayList<>();
            }
            Task task = parseTask(headerTasks[i], lines.get(indexStudent)[i]);
            if (task != null) tasks.add(task);
        }

        if (!tasks.isEmpty()) {
            themes.add(new Theme(currentChapterName, tasks));
        }

        return themes;
    }

    private static Task parseTask(String taskDescription, String scoreStr) {
        TaskType taskType = null;
        if (taskDescription.startsWith("КВ:")) {
            taskType = TaskType.CONTROL_QUESTION;
        } else if (taskDescription.startsWith("УПР:")) {
            taskType = TaskType.EXERCISE;
        } else if (taskDescription.startsWith("ДЗ:")) {
            taskType = TaskType.HOMEWORK;
        }

        if (taskType == null) return null;

        int score = parseScore(scoreStr);
        return new Task(taskDescription, score, taskType);
    }

    private static int parseScore(String scoreStr) {
        try {
            return Integer.parseInt(scoreStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
