package repository;

import com.google.gson.Gson;
import db.DTO.ChangeCryptoResultDTO;
import jakarta.persistence.EntityManager;
import model.ChangeCurrency;
import org.junit.jupiter.api.Test;
import service.HttpURLConnection;
import service.OperationService;
import singeleton.EntityManagerFactorySingeleton;
import utils.ParsingJson;

import java.io.IOException;

public class CheckChangeCryptoTest {

  @Test
  public void test() throws IOException {
//    var changeCryptoResultDTO = new ChangeCryptoResultDTO();
//    var reversePrice = false;
//    String readJson = ParsingJson.readJsonString(req);
//
//    Gson gson = new Gson();
//    ChangeCurrency changeCurrency = gson.fromJson(readJson, ChangeCurrency.class);
//    var changeFrom = changeCurrency.getSymbolFromChange();
//    var amount = changeCurrency.getAmount();
//    var changeTo = changeCurrency.getSymbolToChange();
//
//    changeCryptoResultDTO.setAmount(5);
//    changeCryptoResultDTO.setCurrencyTo(changeTo);
//    changeCryptoResultDTO.setCurrencyFrom(changeFrom);
//    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
//
//    OperationRepository operationRepository = new OperationRepository(em);
//    var transactions = operationRepository.findAllTransactions();
//
//    String result = "";
//    var sortedCurrency = OperationService.operationsSummary(transactions);
//    var currencyFrom = OperationService.findSummaryForCurrency(sortedCurrency, changeFrom);
//    var checkIfYouChooseLessAmount = OperationService.checkIfYouHaveEnoughtCurrency(currencyFrom,
//        amount);
//    if (checkIfYouChooseLessAmount) {
//      var diffBetweenTwoCrypto = HttpURLConnection.countDiffCrypto(changeFrom, changeTo).getPrice();
//      double newAmount = diffBetweenTwoCrypto * amount;
//      changeCryptoResultDTO.setSuccess(true);
//      changeCryptoResultDTO.setMessage("YOU CAN CHANGE");
//      System.out.println("new amount ===" + newAmount);
//
//    } else {
//      changeCryptoResultDTO.setSuccess(false);
//      changeCryptoResultDTO.setMessage("YOU CAN'T CHANGE");
//    }
//
//    System.out.println(result);
//    String jsonInString = gson.toJson(changeCryptoResultDTO);
//    em.close();
  }
}
