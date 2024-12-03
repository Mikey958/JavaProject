package org.example;

import org.example.API.CurrencyConverter;
import org.example.model.Student;
import org.example.parser.parserCSV;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        // Чтение CSV и вывод студентов
        var students = parserCSV.readCSVFile("java-rtf.csv");

        try {
            OutputStream out = System.out;
            PrintStream ps = new PrintStream(out, true, StandardCharsets.UTF_8);
            System.setOut(ps);

            // Чтение студентов из CSV и вывод информации
            for (var student : students) {
                System.out.println(student);
            }

            // Выводим строку "Пример конвертации валют"
            System.out.println("\nКонвертация рубля в 10 разных валют:");

            // Конвертируем 100 рублей в 10 разных валют
            double amount = 100.0; // Исходная сумма
            String fromCurrency = "RUB"; // Исходная валюта - рубли

            // Список валют, в которые будем конвертировать
            String[] toCurrencies = {
                    "USD", // Доллар США
                    "EUR", // Евро
                    "GBP", // Фунт стерлингов
                    "JPY", // Японская иена
                    "AUD", // Австралийский доллар
                    "CAD", // Канадский доллар
                    "CHF", // Швейцарский франк
                    "INR", // Индийская рупия
                    "CNY", // Китайский юань
                    "BRL"  // Бразильский реал
            };

            // Создаем объект конвертера валют
            CurrencyConverter converter = new CurrencyConverter();

            // Проходим по каждой валюте и выводим результат конвертации
            for (String toCurrency : toCurrencies) {
                double convertedAmount = converter.convertCurrency(amount, fromCurrency, toCurrency);
                System.out.println(amount + " " + fromCurrency + " is equal to " + convertedAmount + " " + toCurrency);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
