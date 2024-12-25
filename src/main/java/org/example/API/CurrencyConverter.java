package org.example.API;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

public class CurrencyConverter {
    private static final String API_KEY = "3cb749e7d295d82d89b9ce4c";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    private final OkHttpClient client;

    public CurrencyConverter() {
        this.client = new OkHttpClient();
    }
    public double convertCurrency(double amount, String fromCurrency, String toCurrency) throws IOException {

        String url = API_URL + fromCurrency;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            JSONObject jsonResponse = new JSONObject(response.body().string());
            JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");

            double rate = conversionRates.getDouble(toCurrency);
            return amount * rate;
        }
    }
}

