package servlet;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.payU.Token;
import model.payU.sendMoney.PayuRequest;
import repository.OperationRepository;
import service.WebRequest;
import singeleton.EntityManagerFactorySingeleton;
import utils.ParsingJson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

@WebServlet("/sendMoney")
public class SendMoney extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String readJson = ParsingJson.readJsonString(request);

    //Gson gson = new Gson();
    JsonObject obj = JsonParser.parseString(readJson).getAsJsonObject();
    String accountNb = obj.get("account").getAsString();
    String amount = obj.get("amount").getAsString();
    String currency = "";
    currency = obj.get("currency").getAsString();
    Boolean sendEmail = obj.get("checkEmail").getAsBoolean();


    getPayu();
    removeFromDB(currency,amount);
    System.out.println("aaa");
    String account = obj.get("username").getAsString();

    System.out.println("send money to accoutn");
  }

  private void removeFromDB(String currencyForm,String amount) {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OperationRepository operationRepository = new OperationRepository(em);
    em.getTransaction().begin();
    var wallet = operationRepository.getWallet();
    var currency = operationRepository.findCurrency(currencyForm);
    if (currency == null) {
      var nc = operationRepository.addNewCurrency(currencyForm, wallet);
      operationRepository.removeCrypto(nc, Integer.parseInt(amount));
    } else {
      operationRepository.removeCrypto(currency, Integer.parseInt(amount));
    }
    em.getTransaction().commit();
  }

  private void getPayu() {
    Gson gson = new Gson();
    String formData = "grant_type=client_credentials&client_id=492350&client_secret=4d147bd6434ae7b9d84886023b43f033";
    String urlStringAuth = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize";

    HttpRequest reqToken = HttpRequest.newBuilder()
        .uri(URI.create(urlStringAuth))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(formData))
        .build();
    String token = "";
    try {
      token = WebRequest.getRequestPost(reqToken);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    var tokenResponse = gson.fromJson(token, Token.class);
    var accesToken = tokenResponse.getAccess_token();
    System.out.println("aaa");


    String randomNb = "492350";
    //String notifyURL = "http://localhost:8080/btc/swap";
    PayuRequest.Payout payout = new PayuRequest.Payout("100", "receive");
    PayuRequest.Account account = new PayuRequest.Account("1111222233334444");
    PayuRequest.CustomerAddress customerAddress = new PayuRequest.CustomerAddress("dominike eee");
    PayuRequest request = new PayuRequest("BOokbkJH", payout, account, customerAddress);
    String json = gson.toJson(request);
    String urlStringOrder = "https://secure.snd.payu.com/api/v2_1/orders";
    HttpRequest sendPay = HttpRequest.newBuilder()
        .uri(URI.create(urlStringOrder))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + accesToken)
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

    String payLink = "";
    try {
      payLink = WebRequest.getRequestPost(sendPay);
      JsonObject obj = JsonParser.parseString(payLink).getAsJsonObject();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
