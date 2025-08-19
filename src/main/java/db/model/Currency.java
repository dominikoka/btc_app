package db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

//Table currency {
//  id integer [primary key]
//  wallet_id integer
//  name varchar
//  created_at timestamp
//}

@Entity
@Table(name = "currency")
@Data
public class Currency {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String name;
  private Timestamp created_at;
  public Currency() {}
  public Currency(String name, Timestamp created_at, Set<Operation> operations) {
    this.name = name;
    this.created_at = created_at;
    this.operations = operations;
  }
  @ManyToOne
  @JoinColumn(name = "wallet_id", nullable = false)
  private Wallet wallet;

  @OneToMany(mappedBy = "currency",cascade = CascadeType.MERGE,orphanRemoval = true, fetch = FetchType.LAZY )
  private Set<Operation> operations = new HashSet<Operation>();


}
