package service;

import com.google.gson.Gson;
import model.payU.Order;
import model.payU.Token;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

public class PayuTest {

  @Test
  public void addMoney() {

    String formData = "grant_type=client_credentials&client_id=492350&client_secret=4d147bd6434ae7b9d84886023b43f033";
    String urlStringAuth = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize";

    HttpRequest reqToken = HttpRequest.newBuilder()
        .uri(URI.create(urlStringAuth))
        .header("Content-Type","application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(formData))
        .build();



    String token="";
    try {
       token = WebRequest.getRequestPost(reqToken);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    Gson gson = new Gson();
    var tokenResponse = gson.fromJson(token, Token.class);
    var accesToken= tokenResponse.getAccess_token();
    System.out.println("aaa");



    String randomNb = "492350";
    //String notifyURL = "http://localhost:8080/btc/swap";
    Order order = new Order("127.0.0.1",randomNb,"pay","USD","21000");
    String json = gson.toJson(order);
    String urlStringOrder = "https://secure.snd.payu.com/api/v2_1/orders";
    HttpRequest sendPay = HttpRequest.newBuilder()
        .uri(URI.create(urlStringOrder))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer "+accesToken)
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

    String payLink = "";
    try {
      payLink = WebRequest.getRequestPost(sendPay);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println("aaaa");
  }

}
