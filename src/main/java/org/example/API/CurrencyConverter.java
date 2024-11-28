package org.example.API;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

public class CurrencyConverter {
    private static final String API_KEY = "3cb749e7d295d82d89b9ce4c"; // Замените на ваш реальный API-ключ
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    private final OkHttpClient client;

    public CurrencyConverter() {
        this.client = new OkHttpClient();
    }
    public double convertCurrency(double amount, String fromCurrency, String toCurrency) throws IOException {
        // Формируем URL для запроса
        String url = API_URL + fromCurrency;

        // Создаем запрос
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Отправляем запрос и получаем ответ
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Парсим JSON-ответ
            JSONObject jsonResponse = new JSONObject(response.body().string());
            JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");

            // Получаем курс для нужной валюты
            double rate = conversionRates.getDouble(toCurrency);
            return amount * rate;
        }
    }
}

