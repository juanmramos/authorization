package br.com.caju.authorization.application.port.out.impl;

import br.com.caju.authorization.application.port.out.AccountPersistencePort;
import br.com.caju.authorization.domain.model.AccountEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountJpaAdapter implements AccountPersistencePort {

  private final AccountRepository repository;
  /**
   * @param accountId
   * @return
   */
  @Override
  public Optional<AccountEntity> findById(String accountId) {
    return Optional.of(repository.findByAccountId(accountId));
  }

  /**
   * @param account
   */
  @Override
  public void save(AccountEntity account) {
    repository.save(account);
  }

  public interface AccountRepository extends JpaRepository<AccountEntity, Long>,
      JpaSpecificationExecutor<AccountEntity> {

    AccountEntity findByAccountId(@Param("accountId") String accountId);
  }
}

