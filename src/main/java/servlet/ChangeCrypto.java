package servlet;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ChangeCurrency;
import repository.OperationRepository;
import service.HttpURLConnection;
import service.OperationService;
import singeleton.EntityManagerFactorySingeleton;
import utils.ParsingJson;

import java.io.IOException;

@WebServlet("/changeCrypto")
public class ChangeCrypto extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String readJson = ParsingJson.readJsonString(req);

    Gson gson = new Gson();
    ChangeCurrency changeCurrency = gson.fromJson(readJson, ChangeCurrency.class);
    var changeFrom = changeCurrency.getSymbolFromChange();
    var amount = changeCurrency.getAmount();
    var changeTo = changeCurrency.getSymbolToChange();

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
      em.getTransaction().begin();
      changeCrypto(changeFrom, newAmount, changeTo, operationRepository, amount);
      em.getTransaction().commit();
    }
  }

  public void changeCrypto(String changeFrom, double howMuchAmountNewCrypto, String changeTo,
                           OperationRepository orepo, int amount) {
    //metoda ktora usuwa starą kryptowalute;
    var currency = orepo.findCurrency(changeFrom);
    //removeCrypto
    orepo.removeCrypto(currency, amount);
    //add new crypto
    var newCurrency = orepo.findCurrency(changeTo);
    if (newCurrency == null) {
      var wallet = orepo.getWallet();
      newCurrency = orepo.addNewCurrency(changeTo, wallet);
    }
    orepo.addOperation(newCurrency, howMuchAmountNewCrypto);
    //metoda ktora dodaje nową kryptowalute
  }
}





