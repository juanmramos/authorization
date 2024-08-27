package br.com.caju.authorization.adapter.in.web;

import br.com.caju.authorization.application.port.in.AccountUseCase;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private AccountUseCase accountUseCase;

  @PostMapping("/transactions")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<String> processTransaction(UUID accountId,
                                                  double amount,
                                                  String merchant,
                                                  String mcc) {
    boolean approved = accountUseCase.processTransaction(accountId.toString(), amount, merchant, mcc);
    if (approved) {
      return ResponseEntity.ok("{ \"code\": \"00\" }");
    } else {
      return ResponseEntity.ok("{ \"code\": \"51\" }");
    }
  }
}
