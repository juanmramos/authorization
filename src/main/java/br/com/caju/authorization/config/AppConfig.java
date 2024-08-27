package br.com.caju.authorization.config;

import br.com.caju.authorization.application.port.out.AccountPersistencePort;
import br.com.caju.authorization.application.service.AccountService;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;

@Configuration
public class AppConfig {

  @Bean
  public AccountService accountService(AccountPersistencePort accountPersistencePort) {
    return new AccountService(accountPersistencePort);
  }
}

