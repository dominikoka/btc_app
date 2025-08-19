package servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DateRange;
import model.Kline;
import service.BtcAlgoritm;
import service.HttpURLConnection;
import utils.ParsingJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/api")
public class Api extends HttpServlet {

  List<Kline> klines = new ArrayList<>();

  public static Double parseDoubleFromString(String string) {

    var value = Double.parseDouble(String.valueOf(string));
    return Math.round(value * 100) / 100.0;
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Gson gson = new Gson();
    String json = ParsingJson.readJsonString(request);

    DateRange dateRange = gson.fromJson(json, DateRange.class);

    String default_url = "https://api.binance.com/api/v3/klines?symbol=" + dateRange.getSymbol();
    String GET_URL = getUrl(default_url, dateRange);

    var klinsesString = HttpURLConnection.sendGET(GET_URL);
    List<List<Double>> klinesList = gson.fromJson(klinsesString, List.class);
    klines = extracted(klinesList, dateRange, dateRange.getInterwal());
    String jsonInString = gson.toJson(klines);

    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(jsonInString);
    out.flush();
  }

  private String getUrl(String defaultUrl, DateRange dateRange) {
    String GET_URL = "";
    if (dateRange.getInterwal().equals("day")) {
      System.out.println("dla dni");
      GET_URL = getUrlInterwal(dateRange, defaultUrl, "1d");
      //System.out.println("aaa");
    } else if (dateRange.getInterwal().equals("minute")) {
      System.out.println("dla minut");
      GET_URL = getUrlInterwal(dateRange, defaultUrl, "1m");
    } else if (dateRange.getInterwal().equals("week")) {
      GET_URL = getUrlInterwal(dateRange, defaultUrl, "1w");
    } else {
      GET_URL = getUrlInterwal(dateRange, defaultUrl, "1M");
    }
    return GET_URL;
  }

  private String getUrlInterwal(DateRange dateRange, String GET_URL, String interwal) {
    var startDate = dateRange.getStartDateStamp();
    var endDate = dateRange.getEndDateStamp();
    String res = GET_URL;
    res += "&interval=" + interwal + "&" + "startTime=" + startDate + "&" + "endTime=" + endDate;
    return res;
  }

  private List<Kline> extracted(List<List<Double>> klinesList, DateRange dateRange, String interwal) {
    List<String> time = HttpURLConnection.getTimeRange(dateRange.getStartDateStamp(), dateRange.getEndDateStamp(),
        interwal);
    List<String> description = BtcAlgoritm.getDesc(klinesList, interwal);
    List<Kline> res = new ArrayList<>();
    for (int i = 0; i < klinesList.size(); i++) {
      Kline kline1 = new Kline();
      kline1.setDate(String.valueOf(time.get(i)));
      kline1.setOpen((String.valueOf(HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(i).get(1))))));
      kline1.setHigh(String.valueOf(HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(i).get(2)))));
      kline1.setLow(String.valueOf(HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(i).get(3)))));
      kline1.setClose(String.valueOf(HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(i).get(4)))));
      kline1.setVolume(String.valueOf(HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(i).get(5)))));
      kline1.setDescription(description.get(i));
      res.add(kline1);
    }
    return res;
  }

}
