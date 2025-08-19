package db.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OperationSummaryDTO {
  private String nameCurrency;
  private Double averageBuyPrice;
  private Double totalAmount;
  private Double approximateSellPrice;
  private Double dayHistory;

  public List<OperationSummaryDTO> copyList(List<OperationSummaryDTO> o){
    List<OperationSummaryDTO> copy = new ArrayList<OperationSummaryDTO>();
    for(OperationSummaryDTO op : o){
      OperationSummaryDTO copyOp = new OperationSummaryDTO();
      copyOp.setNameCurrency(op.nameCurrency);
      copyOp.setAverageBuyPrice(op.averageBuyPrice);
      copyOp.setTotalAmount(op.totalAmount);
      copyOp.setApproximateSellPrice(op.approximateSellPrice);
      copyOp.setDayHistory(op.dayHistory);
      copy.add(copyOp);
    }

    return copy;
  }



}
