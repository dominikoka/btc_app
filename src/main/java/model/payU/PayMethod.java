package model.payU;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class PayMethod {
  private String type;
  private String value;
  //private BlikData blikData;
}
