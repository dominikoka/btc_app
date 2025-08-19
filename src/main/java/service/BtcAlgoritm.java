package service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BtcAlgoritm {
  private static final DecimalFormat df = new DecimalFormat("0.00");

  public static List<String> getDesc(List<List<Double>> klinesList, String interwal) {
    List<String> desc = new ArrayList<String>();
    Double sum = 0.0;
    int count = 0;
    for (List<Double> kline : klinesList) {
      Double actualPrice = HttpURLConnection.parseDoubleFromString(String.valueOf(kline.get(4)));
      String days = "from last " + count + interwal;
      String buyOrNot = "";

      sum += actualPrice;
      count++;
      double avg = sum / count;
      if (actualPrice < avg) {
        buyOrNot = "IS A GOOD IDEA";
      } else {
        buyOrNot = "IS NOT A GOOD IDEA";
      }
      Double avg3D = 0.0;
      Double volume3D = 0.0;
      if (count > 3) {
        Double price3DaysAgo =
            HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(count - 3).get(4)));
        Double price2DaysAgo =
            HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(count - 2).get(4)));
        Double price1DaysAgo =
            HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(count - 1).get(4)));
        Double volume1DaysAgo =
            HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(count - 1).get(5)));
        Double volume2DaysAgo =
            HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(count - 2).get(5)));
        Double volume3DaysAgo =
            HttpURLConnection.parseDoubleFromString(String.valueOf(klinesList.get(count - 3).get(5)));
        Double actuallVolume = HttpURLConnection.parseDoubleFromString(String.valueOf(kline.get(5)));
        volume3D = ((volume3DaysAgo + volume1DaysAgo + volume2DaysAgo) / 3) - actuallVolume;
        avg3D = ((price1DaysAgo + price2DaysAgo + price3DaysAgo) / 3) / actualPrice;
      }
      String warning = "";
      if (avg3D > 1.04 || (avg3D < 0.94 && avg != 0)) {
        warning = "\n\n" + "in the last 3 days the price has changed a lot. be very careful.";
        if (volume3D < 0) {
          warning = "\ninvestors are selling their assets in panic. Maybe is a good time to buy?";
          buyOrNot = "IS A GOOD IDEA";
        }
      }
      String res = days + "\n average price is" + df.format(avg) + "\n" + buyOrNot + warning;

      desc.add(res);
    }
    return desc;
  }
  // take day and price
  // get 5 last days and check if avverage price is bigger


}
