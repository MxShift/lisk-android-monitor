package com.vrlc92.liskmonitor.services;

import com.vrlc92.liskmonitor.models.Ticker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by victorlins on 6/30/16.
 */
public class ExchangeService {
    private static ExchangeService instance;
    private final OkHttpClient client;
    private static final String LISK_URL_TICKER = "https://poloniex.com/public?command=returnTicker";
    private static final String LISK_CURRENCY_PAIR = "BTC_LSK";

    private static final String BITCOIN_EUR_URL_TICKER = "https://www.bitstamp.net/api/v2/ticker/btceur/";
    private static final String BITCOIN_USD_URL_TICKER = "https://www.bitstamp.net/api/v2/ticker/btcusd/";

    private ExchangeService() {
        client = new OkHttpClient();
    }

    public static synchronized ExchangeService getInstance() {
        if (instance == null) {
            instance = new ExchangeService();
        }
        return instance;
    }

    public void requestLiskTicker(final RequestListener<Ticker> listener) {
        Request request= new Request.Builder()
                .url(LISK_URL_TICKER)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String jsonData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);

                    JSONObject tickerJson = jsonObject.getJSONObject(LISK_CURRENCY_PAIR);

                    Ticker ticker = null;

                    if (null != tickerJson) {
                        ticker = Ticker.fromJson(tickerJson);
                    }

                    listener.onResponse(ticker);
                } catch (JSONException e) {
                    listener.onFailure(e);
                }
            }
        });
    }

    public void requestBitcoinUSDTicker(final RequestListener<Ticker> listener) {
        requestBitcoinTicker(BITCOIN_USD_URL_TICKER, listener);
    }

    public void requestBitcoinEURTicker(final RequestListener<Ticker> listener) {
        requestBitcoinTicker(BITCOIN_EUR_URL_TICKER, listener);
    }

    private void requestBitcoinTicker(String url , final RequestListener<Ticker> listener) {
        Request request= new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String jsonData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);

                    Ticker ticker = Ticker.fromJson(jsonObject);

                    listener.onResponse(ticker);
                } catch (JSONException e) {
                    listener.onFailure(e);
                }
            }
        });
    }
}
