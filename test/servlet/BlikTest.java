//package servlet;
//
//import com.google.gson.Gson;
//import model.payU.*;
//import org.junit.jupiter.api.Test;
//import service.WebRequest;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpRequest;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BlikTest {
//
//  @Test
//  public void testBlik() {
//    //token
//    String formData = "grant_type=client_credentials&client_id=492350&client_secret=4d147bd6434ae7b9d84886023b43f033";
//    String urlStringAuth = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize";
//
//    HttpRequest reqToken = HttpRequest.newBuilder()
//        .uri(URI.create(urlStringAuth))
//        .header("Content-Type","application/x-www-form-urlencoded")
//        .POST(HttpRequest.BodyPublishers.ofString(formData))
//        .build();
//
//
//
//    String token="";
//    try {
//      token = WebRequest.getRequestPost(reqToken);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//
//    Gson gson = new Gson();
//    var tokenResponse = gson.fromJson(token, Token.class);
//    var accesToken= tokenResponse.getAccess_token();
//    System.out.println("aaa");
//
//    //order
//    String idPOS = "492350";
//    Order order = new Order("127.0.0.1",idPOS,"pay","PLN","21000");
//    Buyer buyer = new Buyer(idPOS,"lenda@o2.pl");
//    //BlikData blikData = new BlikData("bliczek",true,"PL");
//    PayMethod payMethod = new PayMethod("BLIK","123456");
//    List<PayMethod> payMethods = new ArrayList<>();
//    payMethods.add(payMethod);
//    BlikModel blikModel = new BlikModel(order,buyer,payMethod);
//
//    var jsonBlikModel = gson.toJson(blikModel);
//    System.out.println("aaa");
//    String urlStringOrder = "https://secure.snd.payu.com/api/v2_1/orders";
//    HttpRequest sendPay = HttpRequest.newBuilder()
//        .uri(URI.create(urlStringOrder))
//        .header("Content-Type", "application/json")
//        .header("Authorization", "Bearer "+accesToken)
//        .POST(HttpRequest.BodyPublishers.ofString(jsonBlikModel))
//        .build();
//
//    String payLink = "";
//    try {
//      payLink = WebRequest.getRequestPost(sendPay);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//    System.out.println("aaaa");
//
//  }
//}
