package servlet;

import com.google.gson.Gson;
import model.Kline;
import org.junit.jupiter.api.Test;
import service.HttpURLConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ApiTestt {

  @Test
  public void test() throws IOException {
    Api api = new Api();


    //var klinsesString = HttpURLConnection.sendGET();

    Gson gson = new Gson();

    //Kline kline = gson.fromJson(klinsesString, Kline.class);

    System.out.println("aaa");
  }
  @Test
  public void test2() throws IOException {




    String GET_URL = "https://api.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1d&limit=50";
    var json = HttpURLConnection.downloadJsonFromURL(GET_URL);
    Gson gson = new Gson();
    List<List<Double>> data  = gson.fromJson(json, List.class);
    List<Kline> klines = new ArrayList<>();




//    for(List<Double> d : data){
//      Kline kline = new Kline();
//      kline.setKlineOpenTime(HttpURLConnection.parseString(d.get(0)));
//
//      kline.setOpenPrice(HttpURLConnection.parseString(d.get(1)));
//      kline.setHighPrice(HttpURLConnection.parseString(d.get(2)));
//      kline.setLowPrice(HttpURLConnection.parseString(d.get(3)));
//      kline.setClosePrice(HttpURLConnection.parseString(d.get(4)));
//      kline.setVolume(HttpURLConnection.parseString(d.get(5)));
//      kline.setKlineCloseTime(HttpURLConnection.parseString(d.get(6)));
//      kline.setQuoteAssetVolume(HttpURLConnection.parseString(d.get(7)));
//      kline.setNumberOfTrades(HttpURLConnection.parseString(d.get(8)));
//      kline.setTakerBuyBaseVolume(HttpURLConnection.parseString(d.get(9)));
//      kline.setTakerBuyQuoteVolume(HttpURLConnection.parseString(d.get(10)));
//      kline.setUnusedField(HttpURLConnection.parseString(d.get(11)));
//      klines.add(kline);
//    }

    //  private Double klineOpenTime;
    //  private Double openPrice;
    //  private Double highPrice;
    //  private Double lowPrice;
    //  private Double closePrice;
    //  private Double volume;
    //  private Double klineCloseTime;
    //  private Double quoteAssetVolume;
    //  private Double numberOfTrades;
    //  private Double takerBuyBaseVolume;
    //  private Double takerBuyQuoteVolume;
    //  private Double UnusedField;
    String jsonString = gson.toJson(klines);
    System.out.println("aaa");
  }
}
