package org.example;

import org.example.parser.parserCSV;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        // Настроим вывод в консоль с кодировкой UTF-8
        try {
            // Создаем новый PrintStream с кодировкой UTF-8
            OutputStream out = System.out;
            PrintStream ps = new PrintStream(out, true, StandardCharsets.UTF_8);
            System.setOut(ps);  // Перенаправляем System.out на новый PrintStream

            // Печать "Hello World!" в UTF-8
            System.out.println("Hello World!");

            // Выполняем парсинг CSV
            parserCSV.parseStudents();
            parserCSV.parseThemes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
