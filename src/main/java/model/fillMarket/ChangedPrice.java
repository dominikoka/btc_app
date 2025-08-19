package model.fillMarket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ChangedPrice {
  //{"symbol":"ETHBTC","priceChange":"0.00003000","priceChangePercent":"0.133","weightedAvgPrice":"0.02269024","prevClosePrice":"0.02261000","lastPrice":"0.02264000","lastQty":"0.01100000","bidPrice":"0.02264000","bidQty":"38.52350000","askPrice":"0.02265000","askQty":"39.82790000","openPrice":"0.02261000","highPrice":"0.02277000","lowPrice":"0.02261000","volume":"6697.70180000","quoteVolume":"151.97244367","openTime":1751118756313,"closeTime":1751205156313,"firstId":499411732,"lastId":499433625,"count":21894}
  private String symbol;
  private double priceChange;
  private double  priceChangePercent;
//  private BigDecimal  weightedAvgPrice;
//  private BigDecimal  prevClosePrice;
//  private BigDecimal  lastPrice;
//  private BigDecimal  lastQty;
//  private BigDecimal  bidPrice;
//  private BigDecimal  bidQty;
//  private BigDecimal  askPrice;
//  private BigDecimal  askQty;
//  private BigDecimal  openPrice;
//  private BigDecimal  highPrice;
//  private BigDecimal  lowPrice;
//  private BigDecimal  volume;
//  private BigDecimal  quoteVolume;
//  private Long openTime;
//  private Long closeTime;
//  private Long firstId;
//  private Long lastId;
//  private Long count;



}
