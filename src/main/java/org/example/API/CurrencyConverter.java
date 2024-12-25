package org.example.API;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class CurrencyConverter {

    private static final String API_KEY = "3cb749e7d295d82d89b9ce4c";

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    /**
     * HTTP-клиент для выполнения запросов к API.
     */
    private final OkHttpClient client;

    /**
     * Конструктор класса CurrencyConverter.
     * Инициализирует HTTP-клиент.
     */
    public CurrencyConverter() {
        this.client = new OkHttpClient();
    }

    /**
     * Метод для конвертации валюты.
     * Выполняет запрос к API для получения курсов валют, затем вычисляет сумму в целевой валюте.
     *
     * @param amount       Сумма, которую нужно конвертировать.
     * @param fromCurrency Код исходной валюты (например, "USD").
     * @param toCurrency   Код целевой валюты (например, "EUR").
     * @return Конвертированная сумма.
     * @throws IOException Если произошла ошибка при выполнении запроса.
     */
    public double convertCurrency(double amount, String fromCurrency, String toCurrency) throws IOException {

        // Формирование URL для запроса
        String url = API_URL + fromCurrency;

        // Создание HTTP-запроса
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Выполнение запроса и обработка ответа
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Разбор JSON-ответа
            JSONObject jsonResponse = new JSONObject(response.body().string());
            JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");

            // Получение курса целевой валюты и расчет результата
            double rate = conversionRates.getDouble(toCurrency);
            return amount * rate;
        }
    }
}
