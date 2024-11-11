package org.example.parser;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.example.model.Student;
import org.example.model.Theme;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class parserCSV {

    // Путь к CSV файлу
    protected static Path path = Paths.get("java-rtf.csv");

    // Списки для хранения студентов и тем
    protected static List<Student> studentList = new ArrayList<>();
    protected static List<Theme> themeList = new ArrayList<>();

    // Метод для парсинга студентов из CSV файла
    public static void parseStudents() {
        try (CSVReader reader = new CSVReaderBuilder(
                // Чтение файла с указанием кодировки Windows-1251 для корректного отображения русских букв
                new InputStreamReader(new FileInputStream(path.toString()), "Windows-1251"))
                // Установка разделителя в файле как точка с запятой
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            // Пропуск первых 3 строк
            reader.skip(3);

            // Чтение всех оставшихся строк
            List<String[]> records = reader.readAll();

            // Обрабатываем каждую строку и добавляем студента в список
            for (String[] record : records) {
                Student student = parseRecord(record);  // Преобразуем строку в объект Student
                studentList.add(student);  // Добавляем студента в список
            }

            // Выводим всех студентов на экран
            studentList.forEach(System.out::println);

        } catch (IOException | CsvException e) {
            // В случае ошибки выводим стек исключения
            e.printStackTrace();
        }
    }

    // Метод для преобразования строки данных в объект Student
    private static Student parseRecord(String[] record) {
        String fullName = record[0];  // Имя студента (первый столбец)
        String ulearnId = record[1];  // ID студента (второй столбец)
        String group = record[2];     // Группа студента (третий столбец)

        // Считаем общий балл студента как сумму из 3-х оценок (4, 5 и 6 столбцы)
        int score = Integer.parseInt(record[3])
                + Integer.parseInt(record[4])
                + Integer.parseInt(record[5]);

        // Возвращаем объект Student с данными
        return new Student(ulearnId, fullName, score, group);
    }

    // Метод для парсинга тем из CSV файла
    public static void parseThemes() {
        try (CSVReader reader = new CSVReaderBuilder(
                // Чтение файла с кодировкой Windows-1251
                new InputStreamReader(new FileInputStream(path.toString()), "Windows-1251"))
                // Установка разделителя как точка с запятой
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            // Чтение первой строки, которая может содержать названия тем
            String[] firstRow = reader.readNext();
            if (firstRow == null) {
                System.out.println("Первая строка пуста");
                return;
            }

            // Убираем пустые строки из первой строки
            firstRow = removeEmptyStrings(firstRow);

            long idCounter = 1;  // Счетчик для уникальных ID тем

            // Начинаем с индекса 1, чтобы пропустить заголовок или ненужное поле в первой ячейке
            for (int i = 1; i < firstRow.length; i++) {
                String themeName = firstRow[i].trim();  // Убираем пробелы вокруг названия темы
                if (!themeName.isEmpty()) {  // Если тема не пуста, добавляем ее в список
                    Theme theme = new Theme(idCounter++, themeName);  // Создаем объект Theme с уникальным ID
                    themeList.add(theme);  // Добавляем тему в список
                }
            }

            // Выводим все темы на экран
            themeList.forEach(System.out::println);

        } catch (IOException | CsvException e) {
            // В случае ошибки выводим стек исключения
            e.printStackTrace();
        }
    }

    // Метод для удаления пустых строк из массива
    private static String[] removeEmptyStrings(String[] input) {
        // Используем stream для фильтрации пустых строк
        return Arrays.stream(input)
                .filter(s -> s != null && !s.trim().isEmpty())  // Убираем null и пустые строки
                .toArray(String[]::new);  // Возвращаем отфильтрованный массив
    }
}
