package db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.*;



//Table wallet {
//  id integer [primary key]
//  userName varchar
//  created_at timestamp
//}
@Entity
@Table(name = "wallet")
@Data
public class Wallet {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String userName;
  private Timestamp created_at;

  @OneToMany(mappedBy = "wallet", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<Currency> currencies = new HashSet<>();

  public Wallet() {

  }
  public Wallet(String userName, Timestamp created_at, Set<Currency> currencies) {
    this.userName = userName;
    this.created_at = created_at;
    this.currencies = currencies;
  }
  // Getters and setters
}