package br.com.caju.authorization.adapter.in.web;

import br.com.caju.authorization.application.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@Tag(name = "account")
public class AccountController {

  @Autowired
  private AccountService service;

  @Operation(summary = "Processa uma transação de cartão de crédito")
  @PostMapping("/transactions")
  public ResponseEntity<String> processTransaction(
      @RequestParam String accountId,
      @RequestParam double amount,
      @RequestParam String merchant,
      @RequestParam String mcc) {
    boolean approved = service.processTransaction(accountId.toString(), amount, merchant, mcc);
    if (approved) {
      return ResponseEntity.ok("{ \"code\": \"00\" }");
    } else {
      return ResponseEntity.ok("{ \"code\": \"51\" }");
    }
  }
}
