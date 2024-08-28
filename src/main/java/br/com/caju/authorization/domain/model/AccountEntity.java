package br.com.caju.authorization.domain.model;

import br.com.caju.authorization.domain.enums.CategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuperBuilder
@Setter
@Getter
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UUID uuid;

  private String AccountId;

  private String agency;

  private String accountNumber;

  @ElementCollection
  @MapKeyEnumerated(EnumType.STRING)
  @Column(name = "balance")
  private Map<CategoryEnum, Double> balances = new HashMap<>();

  public AccountEntity() {
    this.uuid = UUID.randomUUID();
    this.balances.put(CategoryEnum.FOOD, 100.0);
    this.balances.put(CategoryEnum.MEAL, 100.0);
    this.balances.put(CategoryEnum.CASH, 100.0);
  }

  public boolean hasSufficientBalance(CategoryEnum category, double amount) {
    return balances.get(category) >= amount;
  }

  public void debit(CategoryEnum category, double amount) {
    balances.put(category, balances.get(category) - amount);
  }
}

