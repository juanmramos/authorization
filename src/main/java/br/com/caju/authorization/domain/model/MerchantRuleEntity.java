package br.com.caju.authorization.domain.model;

import br.com.caju.authorization.domain.enums.CategoryEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@Entity
@Table(name = "MERCHANT_RULE")
public class MerchantRuleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UUID uuid;

  @Transient
  private static final Map<String, CategoryEnum> merchantCategoryOverrides = new HashMap<>();

  static {
    merchantCategoryOverrides.put("UBER TRIP", CategoryEnum.CASH);
    merchantCategoryOverrides.put("UBER EATS", CategoryEnum.FOOD);
  }

  public static CategoryEnum getCategoryForMerchant(String merchant) {
    return merchantCategoryOverrides.getOrDefault(merchant.trim(), null);
  }
}
