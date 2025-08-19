package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
@MultipartConfig
@WebServlet("/screenShoot")
public class ScreenShot extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String time = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    time = time.replace(":","-");
    time = time.replace("+","-");
    time = time.replace(".","-");


    String base64 = request.getParameter("photo");
    if (base64.startsWith("data:image")) {
      base64 = base64.substring(base64.indexOf(",") + 1);
    }

    byte[] data = java.util.Base64.getDecoder().decode(base64);
    String path;
    path = "C:/folderek/" + time + ".png";
    File file = new File(path);

    try(OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
      out.write(data);
      System.out.println("zapisywanie screenshota");
    }catch(Exception e) {
      e.printStackTrace();
    }

  }
}
