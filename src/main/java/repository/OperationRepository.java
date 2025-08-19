package repository;

import db.model.Currency;
import db.model.Operation;
import db.model.Wallet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import service.HttpURLConnection;

import java.sql.Timestamp;
import java.util.List;

public class OperationRepository {
  private EntityManager em;

  public OperationRepository(EntityManager em) {
    this.em = em;
  }

  public List<Operation> findAllTransactionsWhereCurrency(String currency, Long idWallet) {
//    Query query = em.createQuery(
//        "SELECT o FROM Operation o"
//    );
    Query query2 = em.createQuery(
        "SELECT o FROM Operation o Where o.currency.wallet.id=: walletId and o.currency.name=: nameCurrency"
    );
    query2.setParameter("walletId", idWallet);
    query2.setParameter("nameCurrency", currency);
    return query2.getResultList();
  }
  public List findAllTransactions() {
    Query q = em.createQuery("SELECT o FROM Operation o");
    return q.getResultList();
  }
  public List getCurrencies() {
    Query q = em.createQuery("SELECT c FROM Currency c");
    return q.getResultList();
  }

  public Object findCryptoAndAmount(String crypto, int amount) {
    Query q = em.createQuery("SELECT o FROM Operation  o Where o.currency.name=:currencyId and o.amount=:amount");
    q.setParameter("currencyId", crypto);
    q.setParameter("amount", amount);
    return q.getSingleResult();
  }

  public void removeCrypto(Currency currency, int amount) {
   Operation op = new Operation();
   op.setCurrency(currency);
   op.setAmount(amount);
   op.setCreated_at(new Timestamp(System.currentTimeMillis()));
   op.setAction("REMOVE");
   em.persist(op);
  }
  public Currency findCurrency(String name) {
    Currency currency = null;
    try {
      currency = em.createQuery("SELECT c FROM Currency c WHERE c.name=:name", Currency.class)
          .setParameter("name", name)
          .getSingleResult();
    }catch (NoResultException e)
    {

    }
    return currency;
  }

  public Currency addNewCurrency(String bnb, Wallet wallet) {
    Currency curr = new Currency();
    curr.setName(bnb);
    curr.setCreated_at(new Timestamp(System.currentTimeMillis()));
    curr.setWallet(wallet);
    em.persist(curr);
    return curr;
  }

  public Wallet getWallet() {
    Query q = em.createQuery("SELECT c FROM Wallet c");
    return (Wallet) q.getSingleResult();
  }

  public void addOperation(Currency newCurrency, double amount) {
    Operation op = new Operation();
    op.setCurrency(newCurrency);
    op.setAmount(amount);
    op.setCreated_at(new Timestamp(System.currentTimeMillis()));
    op.setActual_price(HttpURLConnection.countDiffCrypto(newCurrency.getName(),"USDT").getPrice());
    op.setAction("ADD");
    em.persist(op);
  }
}
