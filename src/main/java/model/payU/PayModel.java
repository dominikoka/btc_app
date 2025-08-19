package model.payU;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayModel {
  //{"name":{},"email":{},"amount":{},"currency":"USD","checkEmail":false}
  private String name;
  private String email;
  private Double amount;
  private String currency;
  private Boolean checkEmail;
  public String getStringAmount()
  {
    String res = amount.toString();
    return res.substring(0, res.length()-2);

  }}
