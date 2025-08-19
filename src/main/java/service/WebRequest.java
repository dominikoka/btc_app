package service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.jasper.tagplugins.jstl.core.Url;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

public class WebRequest {
  public static String getRequest(String urlString) throws IOException {
    String URLlink = "";
    URL url = new URL(urlString);
    URLConnection con = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String line;
    StringBuilder response = new StringBuilder();
    while ((line = in.readLine()) != null) {
      response.append(line);
    }
    in.close();

    return response.toString();
  }
  public static String getRequestPost(HttpRequest request) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest req = request;

    HttpResponse<String> response = client.send(req,BodyHandlers.ofString());
    int statusCode = response.statusCode();
    System.out.println(statusCode);
    System.out.println("Status: " + response.statusCode());
    System.out.println("Headers: " + response.headers());
    System.out.println("Body: " + response.body());
    String responseBody = "";
    if(statusCode == 200){
      responseBody = response.body();

    }
    else
    {
      return response.body();
    }
    return responseBody;
  }
}
