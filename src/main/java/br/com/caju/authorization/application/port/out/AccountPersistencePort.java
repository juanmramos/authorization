package br.com.caju.authorization.application.port.out;

import br.com.caju.authorization.domain.model.AccountEntity;
import java.util.Optional;

public interface AccountPersistencePort {
  Optional<AccountEntity> findById(String accountId);
  void save(AccountEntity account);
}

