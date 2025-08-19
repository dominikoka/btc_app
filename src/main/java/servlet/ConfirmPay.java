package servlet;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/confirmPay")
public class ConfirmPay extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response)
  {
    System.out.println("zaplaciles");
    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/example.jsp");
    try {
      rd.forward(request, response);
    } catch (ServletException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
