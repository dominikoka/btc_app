package servlet;

import com.google.gson.Gson;
import db.DTO.ChangeCryptoResultDTO;
import db.DTO.OperationSummaryDTO;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ChangeCurrency;
import model.fillMarket.Price;
import repository.OperationRepository;
import service.HttpURLConnection;
import service.OperationService;
import utils.ParsingJson;
import singeleton.EntityManagerFactorySingeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/changeCheckCrypto")
public class CheckChangeCrypto extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    var changeCryptoResultDTO = new ChangeCryptoResultDTO();
    String readJson = ParsingJson.readJsonString(req);

    Gson gson = new Gson();
    ChangeCurrency changeCurrency = gson.fromJson(readJson, ChangeCurrency.class);
    var changeFrom = changeCurrency.getSymbolFromChange();
    var amount = changeCurrency.getAmount();
    var changeTo = changeCurrency.getSymbolToChange();


    changeCryptoResultDTO.setCurrencyTo(changeTo);
    changeCryptoResultDTO.setCurrencyFrom(changeFrom);
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();

    OperationRepository operationRepository = new OperationRepository(em);
    var transactions = operationRepository.findAllTransactions();

    var sortedCurrency = OperationService.operationsSummary(transactions);
    var currencyFrom = OperationService.findSummaryForCurrency(sortedCurrency, changeFrom);
    var checkIfYouChooseLessAmount = OperationService.checkIfYouHaveEnoughtCurrency(currencyFrom,
        amount);
    if (checkIfYouChooseLessAmount) {
      var diffBetweenTwoCrypto = HttpURLConnection.countDiffCrypto(changeFrom, changeTo).getPrice();
      double newAmount = diffBetweenTwoCrypto * amount;
      changeCryptoResultDTO.setSuccess(true);
      changeCryptoResultDTO.setMessage("YOU CAN CHANGE");
      System.out.println("new amount ===" + newAmount);
      changeCryptoResultDTO.setAmount(newAmount);
    } else {
      changeCryptoResultDTO.setSuccess(false);
      changeCryptoResultDTO.setMessage("YOU CAN'T CHANGE");
      changeCryptoResultDTO.setAmount(amount);
    }

    String jsonInString = gson.toJson(changeCryptoResultDTO);
    em.close();

    PrintWriter out = res.getWriter();
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    out.print(jsonInString);
    out.flush();
  }



}
