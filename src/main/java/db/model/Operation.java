package db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

//Table operation {
//  id integer [primary key]
//  currency_id integer
//  action varchar
//  amount double
//  actual_price double
//  created_at timestamp
//}
@Entity
@Table(name = "operation")
@Data
public class Operation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String action;
  private double amount;
  private double actual_price;
  private Timestamp created_at;
  public Operation() {}
  public Operation(String action, double amount, double actual_price, Timestamp created_at) {
    this.action = action;
    this.amount = amount;
    this.actual_price = actual_price;
    this.created_at = created_at;
  }

  @ManyToOne
  @JoinColumn(name = "currency_id", nullable = false)
  private Currency currency;


}
