package br.com.caju.authorization.adapter.in.web.impl;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.caju.authorization.adapter.in.web.AccountController;
import br.com.caju.authorization.application.port.in.AccountUseCase;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountUseCase accountUseCase;

  private UUID accountId;

  @BeforeEach
  public void setup() {
    accountId = UUID.randomUUID();
  }

  @Test
  public void processTransaction_Approved_ReturnsCode00() throws Exception {
    // Arrange
    Mockito.when(accountUseCase.processTransaction(anyString(), anyDouble(), anyString(), anyString()))
        .thenReturn(true);

    // Act & Assert
    mockMvc.perform(post("/accounts/transactions")
            .param("accountId", accountId.toString())
            .param("amount", "100.00")
            .param("merchant", "PADARIA DO ZE")
            .param("mcc", "5811")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{ \"code\": \"00\" }"));
  }

  @Test
  public void processTransaction_InsufficientFunds_ReturnsCode51() throws Exception {
    // Arrange
    Mockito.when(accountUseCase.processTransaction(anyString(), anyDouble(), anyString(), anyString()))
        .thenReturn(false);

    // Act & Assert
    mockMvc.perform(post("/accounts/transactions")
            .param("accountId", accountId.toString())
            .param("amount", "100.00")
            .param("merchant", "PADARIA DO ZE")
            .param("mcc", "5811")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{ \"code\": \"51\" }"));
  }

  @Test
  public void processTransaction_InvalidInput_ReturnsCode07() throws Exception {
    // Arrange
    Mockito.when(accountUseCase.processTransaction(anyString(), anyDouble(), anyString(), anyString()))
        .thenThrow(new IllegalArgumentException());

    // Act & Assert
    mockMvc.perform(post("/accounts/transactions")
            .param("accountId", accountId.toString())
            .param("amount", "100.00")
            .param("merchant", "PADARIA DO ZE")
            .param("mcc", "5811")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{ \"code\": \"07\" }"));
  }
}