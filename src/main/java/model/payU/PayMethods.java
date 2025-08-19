package model.payU;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class PayMethods {
  List<PayMethod> payMethods = new ArrayList<>();

  public void addPayMethod(PayMethod payMethod)
  {
    payMethods.add(payMethod);
  }
}
