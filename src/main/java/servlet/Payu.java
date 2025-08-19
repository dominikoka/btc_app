package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.payU.Order;
import model.payU.PayModel;
import model.payU.Token;
import repository.OperationRepository;
import service.EmailSender;
import service.WebRequest;
import singeleton.EntityManagerFactorySingeleton;
import utils.ParsingJson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpRequest;

@WebServlet("/payu")
public class Payu extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
    String redirectLink = "";
    System.out.println("uruchomiles payU");
    String readJson = ParsingJson.readJsonString(req);

    Gson gson = new Gson();
    PayModel payModel = gson.fromJson(readJson, PayModel.class);
    String amount = payModel.getStringAmount();
    if (!payModel.getCurrency().equals("PLN")) {
      int newAmount = Integer.parseInt(amount);
      newAmount = newAmount / 4 * 100;
      amount = String.valueOf(newAmount);
    } else {
      int newAmount = Integer.parseInt(amount);
      newAmount = newAmount * 100;
      amount = String.valueOf(newAmount);
    }


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
    Order order = new Order("127.0.0.1", randomNb, "pay", "PLN", amount);
    String json = gson.toJson(order);
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
      redirectLink = obj.get("redirectUri").getAsString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println("aaaa");

    //save curr in db
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OperationRepository operationRepository = new OperationRepository(em);
    em.getTransaction().begin();
    var wallet = operationRepository.getWallet();
    var currency = operationRepository.findCurrency(payModel.getCurrency());
    if (currency == null) {
      var nc = operationRepository.addNewCurrency(payModel.getCurrency(), wallet);
      operationRepository.addOperation(nc, Double.parseDouble(amount));
    } else {
      operationRepository.addOperation(currency, Double.parseDouble(amount));
    }
    em.getTransaction().commit();

    if (payModel.getCheckEmail()) {
      EmailSender emailSender = new EmailSender("", "");
      emailSender.sendEmail(payModel.getEmail(), "add Moden", "You added " + amount + " " + payModel.getCurrency());
    }

    PrintWriter out = res.getWriter();
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    out.print("{\"redirectLink\":\"" + redirectLink + "\"}");
    out.flush();
  }

}
