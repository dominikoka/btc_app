package db.DTO;

import db.model.Operation;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
@Data
public class CurrencyDTO {
  private Long id;
  private String name;
  private Timestamp created_at;
  private Set<Operation> operations = new HashSet<Operation>();
  public CurrencyDTO() {}
  public CurrencyDTO(Long id, String name, Timestamp created_at, Set<Operation> operations) {
    this.id = id;
    this.name = name;
    this.created_at = created_at;
    this.operations = operations;
  }
}
