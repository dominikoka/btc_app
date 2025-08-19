package model.payU.sendMoney;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payout {
  private String amount;
  private String description;
}
