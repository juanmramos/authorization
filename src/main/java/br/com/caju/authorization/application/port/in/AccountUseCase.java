package br.com.caju.authorization.application.port.in;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public interface  AccountUseCase {

  boolean processTransaction(String accountId, double amount, String merchant, String mcc);
}

