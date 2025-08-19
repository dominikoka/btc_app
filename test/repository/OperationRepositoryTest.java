package repository;

import com.google.gson.Gson;
import db.DTO.OperationSummaryDTO;
import jakarta.persistence.EntityManager;
import model.fillMarket.Price;
import model.fillMarket.WalletJsonModel;
import org.junit.jupiter.api.Test;
import service.HttpURLConnection;
import service.OperationService;
import servlet.CheckChangeCrypto;
import singeleton.EntityManagerFactorySingeleton;

import java.io.IOException;

public class OperationRepositoryTest {
  @Test
  public void test() {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OperationRepository repo = new OperationRepository(em);

    OperationRepository orepo = new OperationRepository(em);
    var transactions = orepo.findAllTransactions();

    var sortedTransaction = OperationService.operationsSummary(transactions);

    System.out.println("aaa");
  }

  @Test
  public void fillMarkedDataTest() {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OperationRepository repo = new OperationRepository(em);

    OperationRepository orepo = new OperationRepository(em);
    var transactions = orepo.findAllTransactions();

    var sortedTransaction = OperationService.operationsSummary(transactions);
  }

  @Test
  public void getHistory() {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OperationRepository repo = new OperationRepository(em);
    OperationRepository orepo = new OperationRepository(em);
    var allTransactions = repo.findAllTransactions();

    var operations = OperationService.getOperations(allTransactions);

    System.out.println("aaaa");

  }

  @Test
  public void getJsonWallet() {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OperationRepository repo = new OperationRepository(em);
    OperationRepository orepo = new OperationRepository(em);
    var transactions = orepo.findAllTransactions();

    var sortedTransaction = OperationService.operationsSummary(transactions);
    var allOperations = OperationService.getOperations(transactions);
    WalletJsonModel walletJson = new WalletJsonModel(allOperations, sortedTransaction);


    Gson gson = new Gson();
    String json = gson.toJson(walletJson);
    System.out.println("aaa");
  }

  @Test
  public void checkIfCryptoExistAndIsEnoughtAmount() throws IOException {


    var symbolToChange = "BTC";
    var amount = 15;
    var symbolFromChange = "USDT";
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OperationRepository repo = new OperationRepository(em);
    OperationRepository orepo = new OperationRepository(em);
    var transactions = orepo.findAllTransactions();

    String res = "";
    var sortedTransaction = OperationService.operationsSummary(transactions);

    for (Object operation : sortedTransaction) {
      OperationSummaryDTO cryptoSummary = (OperationSummaryDTO) operation;
      var name = cryptoSummary.getNameCurrency();
      if (name.equals(symbolToChange)) {
        var totalAmount = cryptoSummary.getTotalAmount();
        if (totalAmount > amount) {
          var changeCrypto = symbolToChange+symbolFromChange;
          String URL = "https://api.binance.com/api/v3/ticker/price?symbol="+ changeCrypto;
          var price = HttpURLConnection.sendGET(URL);
          Gson gson = new Gson();
          var klinesList = gson.fromJson(price, Price.class);
          double changedDiff = klinesList.getPrice();
          double newAmount = changedDiff*amount;
          res = String.valueOf(newAmount);
          System.out.println("aaa");
        } else {
          res = "you have not enought crypto";
        }
      }

      System.out.println("");
    }

    System.out.println("aaa");
  }
  @Test
  public void changeCrypto()
  {
    var changeFrom = "BTC";
    var amount = 2;
    var changeTo = "ETH";

    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    em.getTransaction().begin();
    OperationRepository repo = new OperationRepository(em);
    OperationRepository orepo = new OperationRepository(em);
    var currency = orepo.findCurrency("BTC");
    //removeCrypto
    //orepo.removeCrypto(currency,amount);


    //add new crypto
    var newCurrency = orepo.findCurrency("BNB");
    if(newCurrency==null)
    {

      var wallet = orepo.getWallet();
      newCurrency = orepo.addNewCurrency("BNB",wallet);
    }
    orepo.addOperation(newCurrency,amount);
    em.getTransaction().commit();
//    System.out.println("aaa");
  }

  @Test
  public void getChangeInfoCurrency()
  {
    CheckChangeCrypto check = new CheckChangeCrypto();
    //check.countDiffCrypto("BTC","ETH");
  }
  @Test
  public void checkTestComprareCrypto()
  {
    var diff = HttpURLConnection.countDiffCrypto("BTC","PLN");

    System.out.println(diff);
  }
  @Test
  public void findCurrency()
  {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    em.getTransaction().begin();
    OperationRepository repo = new OperationRepository(em);
    OperationRepository orepo = new OperationRepository(em);
    var currency = repo.findCurrency("EEE");

    System.out.println("aaa");
  }

}
