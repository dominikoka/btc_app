package db.DTO;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;
@Data
public class WalletDTO {
  private Long id;
  private String userName;
  private Timestamp created_at;
  private Set<CurrencyDTO> currencies;
  public WalletDTO() {}
  public WalletDTO(Long id, String userName, Timestamp created_at, Set<CurrencyDTO> currencies) {
    this.id = id;
    this.userName = userName;
    this.created_at = created_at;
    this.currencies = currencies;
  }
}
