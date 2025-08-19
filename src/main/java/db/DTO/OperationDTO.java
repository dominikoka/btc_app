package db.DTO;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class OperationDTO {
  private String Currency;
  private double amount;
  private double actual_price;
  private Timestamp created_at;
  private String action;
  //private CurrencyDTO currencyDTO;
  public OperationDTO() {}
  public OperationDTO(String action, double amount, double actual_price, Timestamp created_at, CurrencyDTO currencyDTO) {
    this.amount = amount;
    this.actual_price = actual_price;
    this.created_at = created_at;
    //this.currencyDTO = currencyDTO;
  }
}
