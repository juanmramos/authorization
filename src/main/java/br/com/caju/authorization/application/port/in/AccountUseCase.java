package br.com.caju.authorization.application.port.in;

public interface AccountUseCase {
  boolean processTransaction(String accountId, double amount, String merchant, String mcc);
}

