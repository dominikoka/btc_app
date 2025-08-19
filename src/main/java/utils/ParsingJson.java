package utils;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class ParsingJson {
  public static String readJsonString(HttpServletRequest req) throws IOException
  {
    BufferedReader br = req.getReader();
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line);
    }
    return sb.toString();
  }
}
