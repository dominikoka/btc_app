package model.payU.sendMoney;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class PayuRequest {
  private String shopId;
  private Payout payout;
  private Account account;
  private CustomerAddress customerAdress;

  public static class Account {
    private String accountNumber;

    public Account(String accountNumber) {
      this.accountNumber = accountNumber;
    }
  }

  public static class Payout {
    private String amount;
    private String description;

    public Payout(String amount, String description) {
      this.amount = amount;
      this.description = description;
    }
  }

  public static class CustomerAddress {
    private String name;
    public CustomerAddress(String name) {
      this.name = name;
    }
  }
  public PayuRequest(String shopId, Payout payout, Account account, CustomerAddress customerAdress) {
    this.shopId = shopId;
    this.payout = payout;
    this.account = account;
    this.customerAdress = customerAdress;
  }
}
