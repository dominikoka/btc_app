package servlet;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import repository.OperationRepository;
import service.OperationService;
import singeleton.EntityManagerFactorySingeleton;

import java.io.IOException;

@WebServlet("/wallet")
public class Wallet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public void doGet(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("uruchomiles wallet");
    RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/wallet.jsp");
    try {
      rd.forward(req,res);
    } catch (ServletException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
