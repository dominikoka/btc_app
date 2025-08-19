package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeCurrency {
  private String symbolToChange;
  private String symbolFromChange;
  private int amount;


}
