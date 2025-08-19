package model.payU;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
  //public String notifyUrl;
  public String customerIp;
  public String merchantPosId;
  public String description;
  public String currencyCode;
  public String totalAmount;
}
