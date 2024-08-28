package br.com.caju.authorization.application.service;

import br.com.caju.authorization.application.port.out.AccountPersistencePort;
import br.com.caju.authorization.domain.enums.CategoryEnum;
import br.com.caju.authorization.domain.model.MerchantRuleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

  private final AccountPersistencePort accountPersistencePort;

  public boolean processTransaction(String accountId, double amount, String merchant, String mcc) {
    var accountOptional = accountPersistencePort.findById(accountId);
    if (accountOptional.isPresent()) {
      var account = accountOptional.get();
      CategoryEnum category = determineCategory(merchant, mcc);
      if (account.hasSufficientBalance(category, amount)) {
        account.debit(category, amount);
        accountPersistencePort.save(account);
        return true;
      }
    }
    return false;
  }

  private CategoryEnum determineCategory(String merchant, String mcc) {
    CategoryEnum category = MerchantRuleEntity.getCategoryForMerchant(merchant);
    if (category == null) {
      if (mcc.equals("5411") || mcc.equals("5412")) {
        category = CategoryEnum.FOOD;
      } else if (mcc.equals("5811") || mcc.equals("5812")) {
        category = CategoryEnum.MEAL;
      } else {
        category = CategoryEnum.CASH;
      }
    }
    return category;
  }
}

