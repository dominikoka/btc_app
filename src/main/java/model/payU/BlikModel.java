package model.payU;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BlikModel {
  private Order order;
  private Buyer buyer;
  private PayMethod payMethods;
}
