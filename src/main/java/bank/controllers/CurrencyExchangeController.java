package bank.controllers;

import java.io.*;
import java.math.BigDecimal;

import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.*;
import org.json.JSONObject;

public class CurrencyExchangeController {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.apilayer.com/exchangerates_data/convert?to=RUB&from=EUR&amount=5")
                .addHeader("apikey", "GHkYItBSvsS40n9vrQTPTJ4jQ9UmbBZr")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject object = new JSONObject(response.body().string());
        String info = object.get("info").toString();
        System.out.println(object.get("result"));
        String[] infoString = info.split(":");
        String[] rate = infoString[1].split(",");
        System.out.println(rate[0]);

    }
}
