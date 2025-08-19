package service;

import com.google.gson.Gson;
import model.fillMarket.Price;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HttpURLConnection {
  //private static final String GET_URL = "https://api.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1d&limit=50";
  private static final String USER_AGENT = "Mozilla/5.0";

  public static String sendGET(String GET_URL) throws IOException {
    StringBuffer response = new StringBuffer();
    URL obj = new URL(GET_URL);
    java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("User-Agent", USER_AGENT);
    int responseCode = con.getResponseCode();
    //System.out.println("GET Response Code :: " + responseCode);
    if (responseCode == java.net.HttpURLConnection.HTTP_OK) { // success
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      //System.out.println(response.toString());
    } else {
      System.out.println("GET request did not work.");
    }
    return response.toString();
  }

  public static String downloadJsonFromURL(String urlText) {
    try {
      URL myUrl = new URL(urlText);
      StringBuilder jsonText = new StringBuilder();
      try (InputStream myInputStream = myUrl.openStream();
           Scanner scanner = new Scanner(myInputStream)) {
        while (scanner.hasNextLine()) {
          jsonText.append(scanner.nextLine());
        }
        return jsonText.toString();
      }
    } catch (IOException e) {
      //System.err.println(“Failed to get content from URL ” + urlText + ” due to exception: ” + e.getMessage());
      return null;
    }
  }

  public static Double parseDoubleFromString(String string) {

    var value = Double.parseDouble(String.valueOf(string));
    return Math.round(value * 100) / 100.0;
  }

  public static List<String> getTimeRange(long start, long end, String interval) {
    List<String> result = new ArrayList<>();
    long stepMs;

    if ("minute".equalsIgnoreCase(interval)) {
      stepMs = 60 * 1000L;
    } else if ("day".equalsIgnoreCase(interval)) {
      stepMs = 24 * 60 * 60 * 1000L;
    } else if ("week".equalsIgnoreCase(interval)) {
      stepMs = 7 * 24 * 60 * 60 * 1000L;
    } else if ("month".equalsIgnoreCase(interval)) {

      stepMs = 30 * 24 * 60 * 60 * 1000L;
    } else {
      throw new IllegalArgumentException("Nieznany interwał: " + interval);
    }

    long current = start;
    while (current <= end) {
      result.add(String.valueOf(current));
      current += stepMs;
    }
    return result;
  }

  public static double getCurrencyPrice(String fCurr, String sCurr) {
    String URL = "https://api.binance.com/api/v3/ticker/price?symbol=" + fCurr + sCurr;
    var currencyPrice = downloadJsonFromURL(URL);
    if (currencyPrice == null) {
      return 0.0;
    }
    Gson gson = new Gson();
    var price = gson.fromJson(currencyPrice, Price.class);
    return price.getPrice();

  }

  public static Price countDiffCrypto(String changeFrom, String changeTo) {
    var sCurr = "USDT";
    Price price = new Price();
    var symbol = changeFrom + changeTo;

    if (changeFrom.equals(sCurr) || changeTo.equals(sCurr)) {
      var fC = HttpURLConnection.getCurrencyPrice(changeFrom, changeTo);
      var sC = HttpURLConnection.getCurrencyPrice(changeTo, changeFrom);
      if (fC > 0) {
        price.setSymbol(symbol);

        price.setPrice(fC);
        return price;
      } else if (sC > 0) {
        price.setSymbol(symbol);
        price.setPrice(1 / sC);
        return price;
      }
    }
    //get price nr1

    var fC = HttpURLConnection.getCurrencyPrice(changeFrom, sCurr);
    var sC = HttpURLConnection.getCurrencyPrice(changeTo, sCurr);
    if (fC == 0.0) {
      fC = 1 / (HttpURLConnection.getCurrencyPrice(sCurr, changeFrom));
    }
    if (sC == 0.0) {
      sC = 1 / (HttpURLConnection.getCurrencyPrice(sCurr, changeTo));
      if (sCurr.equals("USDT") && changeTo.equals("USDT")) {
        sC = 1;
      }
    }

    double diff = fC / sC;
    String formattedDiff = String.format("%.5f", diff).replace(",", ".");
    System.out.println("aaa");

    price.setSymbol(symbol);
    price.setPrice(diff);
    return price;
  }

}
