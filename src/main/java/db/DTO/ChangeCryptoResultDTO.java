package db.DTO;

import lombok.Data;

@Data
public class ChangeCryptoResultDTO {
  private boolean success;
  private String message;
  private String currencyTo;
  private String currencyFrom;
  private double amount;
}
