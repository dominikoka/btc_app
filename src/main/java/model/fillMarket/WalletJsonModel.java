package model.fillMarket;

import db.DTO.OperationDTO;
import db.DTO.OperationSummaryDTO;
import lombok.AllArgsConstructor;

import java.util.List;
//@Data
@AllArgsConstructor
public class WalletJsonModel {
  List<OperationSummaryDTO> summary;
  List<OperationDTO> history;

}
