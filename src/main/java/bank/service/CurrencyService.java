package bank.service;

import bank.models.Currency;
import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class CurrencyService {
    public static BigDecimal CurrencyExchange(Currency currencyTo, Currency currencyFrom, BigDecimal money) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.apilayer.com/exchangerates_data/convert?to=" + currencyTo + "&from=" + currencyFrom + "&amount=" + money)
                .addHeader("apikey", "GHkYItBSvsS40n9vrQTPTJ4jQ9UmbBZr")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject object = new JSONObject(response.body().string());
        String info = object.get("info").toString();
        String result = object.get("result").toString();
        String[] infoString = info.split(":");
        String[] rate = infoString[1].split(",");
        BigDecimal a = new BigDecimal(result);
        return a;
    }
}
