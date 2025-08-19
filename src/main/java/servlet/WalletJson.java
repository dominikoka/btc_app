package servlet;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.fillMarket.WalletJsonModel;
import repository.OperationRepository;
import service.OperationService;
import singeleton.EntityManagerFactorySingeleton;

import java.io.IOException;

@WebServlet("/walletJSON")
public class WalletJson extends HttpServlet {
  private static final long serialVersionUID = 1L;
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    EntityManager em = EntityManagerFactorySingeleton.getEntityManagerFactory().createEntityManager();
    OperationRepository operationRepository = new OperationRepository(em);
    var transactions = operationRepository.findAllTransactions();

    var sortedTransaction = OperationService.operationsSummary(transactions);
    var allOperations = OperationService.getOperations(transactions);
    WalletJsonModel walletJson = new WalletJsonModel(sortedTransaction,allOperations);


    Gson gson = new Gson();
    String json = gson.toJson(walletJson);

    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    res.getWriter().write(json);
  }
}
