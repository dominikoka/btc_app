package service;

import com.google.gson.Gson;
import db.DTO.OperationDTO;
import db.DTO.OperationSummaryDTO;
import db.model.ActionType;
import db.model.Operation;
import model.fillMarket.ChangedPrice;
import model.fillMarket.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

//private Long id;
//private String action;
//private double amount;
//private double actual_price;
//private Timestamp created_at;
public class OperationService<T> {
  private static List<String> currency = Arrays.asList("PLN", "USD", "EUR", "GBP");
  public T type;

  public static List<OperationSummaryDTO> operationsSummary(List<Operation> transactions) {
    //jedna do grupowania transakcji po walucie,
    //jedna do liczenia podsumowań dla danej waluty,
    //jedna do zaokrąglania,
    //jedna do tworzenia DTO.
    List<OperationSummaryDTO> operations = new ArrayList<>();
    System.out.println("operationsSummary");
    //var currencyNames = getCurrencyName(transactions);
    var currencyGroup = getCurrencyGroup(transactions);
    var summaryValue = getSummaryGroup(currencyGroup);
    var filledMarkedData = fillMarketData(summaryValue);
    System.out.println("aaa");
    return filledMarkedData;
  }

  public static List<OperationSummaryDTO> fillMarketData(List<OperationSummaryDTO> summaryValue) {
    // pobieram ceny dla wszystkich i zmiane ceny dla wszystkich.
    var prices = HttpURLConnection.downloadJsonFromURL("https://api.binance.com/api/v3/ticker/price");
    var changedPrice = HttpURLConnection.downloadJsonFromURL("https://api.binance.com/api/v3/ticker/24hr");
    var pricesList = deserializeJSON(prices, Price[].class);
    var changedList = deserializeJSON(changedPrice, ChangedPrice[].class);

    var filledAproximatePrice = fillApproximatePrice(summaryValue, pricesList, changedList);


    return filledAproximatePrice;
  }

  private static List<OperationSummaryDTO> fillApproximatePrice(List<OperationSummaryDTO> summaryValue,
                                                                List<Price> pricesList,
                                                                List<ChangedPrice> changedPrice) {
    OperationSummaryDTO operationSummaryDTO = new OperationSummaryDTO();
    var copySummary = operationSummaryDTO.copyList(summaryValue);
    for (var value : copySummary) {
      var reverse = false;
      var nameValue = value.getNameCurrency() + "USDT";
      var amount = value.getTotalAmount();
      if (value.getNameCurrency().equals("USDT")) {
        value.setApproximateSellPrice(amount);
        value.setDayHistory(1.0);
      } else {

        int i = 0;
        for (i = 0; i < pricesList.size(); i++) {
          if (nameValue.equals(pricesList.get(i).getSymbol())) {
            var price = reverse ? (1 / pricesList.get(i).getPrice() * amount) : pricesList.get(i).getPrice() * amount;
            var changedPriced = changedPrice.get(i).getPriceChangePercent();
            value.setApproximateSellPrice(checkIfCryptoIsCurrency(price, value.getNameCurrency(), currency));
            value.setDayHistory(changedPriced);
            //value.setDayHistory(changedPrice.);
            System.out.println("aaa");
            i = pricesList.size();
          }
          if (i == pricesList.size() - 2) {
            System.out.println("aaaa");
          }
          if (i == pricesList.size() - 1 && value.getApproximateSellPrice() == null) {
            nameValue = "USDT" + value.getNameCurrency();
            i = 0;
            reverse = true;
          }
        }
      }

    }
    return copySummary;
  }

  private static Double checkIfCryptoIsCurrency(double price, String nameCurrency, List<String> currency) {
    if (currency.contains(nameCurrency)) {
      return Math.floor(price * 100) / 100;
    } else {
      return price;
    }
  }

  public static List<OperationDTO> getOperations(List<Operation> transactions) {
    List<OperationDTO> operations = new ArrayList<>();
    for (var transaction : transactions) {
      operations.add(mapToDTO(transaction));
    }
    return operations;
  }

  private static <T> List<T> deserializeJSON(String prices, Class<T[]> type) {
    //var pricesList = new ArrayList<>();
    Gson gson = new Gson();
    var list = gson.fromJson(prices, type);

    return Arrays.asList(list);
  }

  private static List<OperationSummaryDTO> getSummaryGroup(Map<String, List<OperationDTO>> currencyGroup) {
    List<OperationSummaryDTO> res = new ArrayList<>();
    var keys = currencyGroup.keySet();
    for (var key : keys) {
      var values = currencyGroup.get(key);
      OperationSummaryDTO operationSummaryDTO = getSummaryForCurrency(key, values);
      res.add(operationSummaryDTO);
    }
    System.out.println("aaa");
    return res;
  }

  private static OperationSummaryDTO getSummaryForCurrency(String key, List<OperationDTO> values) {
    OperationSummaryDTO operationSummaryDTO = new OperationSummaryDTO();
    operationSummaryDTO.setNameCurrency(key);
    int counter = 0;
    double amount = 0;
    double averagePrice = 0;
    for (var value : values) {
      if (value.getAction().equals(ActionType.ADD.toString())) {
        amount += value.getAmount();
        counter++;
      } else {
        amount -= value.getAmount();
      }
      averagePrice += value.getActual_price();
    }
    operationSummaryDTO.setTotalAmount(amount);
    operationSummaryDTO.setAverageBuyPrice(roundOff(averagePrice / counter));
    operationSummaryDTO.getApproximateSellPrice();
    return operationSummaryDTO;
  }

  private static Map<String, List<OperationDTO>> getCurrencyGroup(List<Operation> operationList) {
    Map<String, List<OperationDTO>> operationsGroup = new HashMap<>();
    for (var operation : operationList) {
      var actuallCurrency = operation.getCurrency().getName();
      var operationDTO = mapToDTO(operation);
      var currencyList = operationsGroup.get(actuallCurrency);
      if (currencyList == null) {
        List<OperationDTO> operationDTOS = new ArrayList<>();
        operationDTOS.add(operationDTO);
        operationsGroup.put(actuallCurrency, operationDTOS);
      } else {
        currencyList.add(operationDTO);
      }

    }
    return operationsGroup;
  }

  private static OperationDTO mapToDTO(Operation operation) {
    OperationDTO operationDTO = new OperationDTO();
    operationDTO.setCurrency(operation.getCurrency().getName());
    operationDTO.setAmount(operation.getAmount());
    operationDTO.setActual_price(operation.getActual_price());
    operationDTO.setAction(operation.getAction());
    operationDTO.setCreated_at(operation.getCreated_at());
    return operationDTO;
  }

  private static List<String> getCurrencyName(List<Operation> transactions) {
    List<String> currencyNames = new ArrayList<>();
    for (var transaction : transactions) {
      if (!currencyNames.contains(transaction.getCurrency().getName())) {
        currencyNames.add(transaction.getCurrency().getName());
      }
    }
    return currencyNames;
  }

  private static double roundOff(double value) {
    return new BigDecimal(value).setScale(2, RoundingMode.UP).doubleValue();
  }

  public static OperationSummaryDTO findSummaryForCurrency(List<OperationSummaryDTO> operationsSummary,
                                                           String nameCurrency) {
    OperationSummaryDTO operationSummaryDTO = new OperationSummaryDTO();
    var operationsSummaryDtoCopy = operationSummaryDTO.copyList(operationsSummary);
    for (var operation : operationsSummaryDtoCopy) {
      if (operation.getNameCurrency().equals(nameCurrency)) {
        return operation;
      }
    }
    return null;
  }

  public static Boolean checkIfYouHaveEnoughtCurrency(OperationSummaryDTO operationSummaryDTO, double amount) {
    if (operationSummaryDTO == null) {
      return false;
    }
    return operationSummaryDTO.getTotalAmount() >= amount;
  }
}
