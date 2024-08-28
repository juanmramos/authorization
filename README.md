# Sistema de Autorização de Transações de Cartão de Crédito

## Objetivo
O projeto visa desenvolver um sistema de autorização de transações de cartão de crédito, implementando diversas regras de negócio para garantir que as transações sejam processadas de forma correta e segura. O sistema deve verificar se uma transação pode ser autorizada com base nos saldos disponíveis em diferentes categorias (como FOOD, MEAL, e CASH) e deve fornecer respostas adequadas para cada transação.

## Requisitos Funcionais

1. **Autorização de Transações Simples:**
   - Receber uma transação no formato JSON via uma requisição HTTP.
   - Identificar a categoria de saldo a ser utilizada com base no código MCC (Merchant Category Code).
   - Aprovar ou rejeitar a transação com base no saldo disponível na categoria correspondente.
   - Se aprovado, debitar o valor correspondente da categoria de saldo.

2. **Autorização com Fallback:**
   - Se a transação não puder ser coberta pelo saldo da categoria principal, verificar se há saldo suficiente na categoria CASH.
   - Debitar o saldo da categoria CASH se o saldo principal for insuficiente, mas o saldo de CASH for suficiente.

3. **Dependência do Comerciante:**
   - Substituir o MCC por uma categoria específica com base no nome do comerciante.
   - Exemplo: transações de "UBER EATS" devem ser categorizadas como FOOD, independentemente do MCC.

4. **Resposta às Transações:**
   - Responder com `{ "code": "00" }` para transações aprovadas.
   - Responder com `{ "code": "51" }` para transações rejeitadas por saldo insuficiente.
   - Responder com `{ "code": "07" }` para qualquer outro erro.

5. **Documentação da API:**
   - Documentar automaticamente os endpoints da API usando Swagger (via Springdoc OpenAPI).

6. **Testes Unitários:**
   - Garantir a cobertura de testes para os principais componentes, incluindo o controlador que processa as transações.

## Arquitetura
Foi adotada uma **Arquitetura Hexagonal (Ports and Adapters)** para promover a separação de preocupações e facilitar a manutenção e evolução do sistema. Esta arquitetura é composta pelos seguintes componentes principais:

- **Domínio (Core):**
  - Contém as regras de negócio e as entidades centrais, como `AccountEntity`, `TransactionEntity`, e `MerchantRuleEntity`.
  - Implementa a lógica de autorização de transações.

- **Aplicação (Application):**
  - Define os casos de uso, como o processamento de transações (`AccountUseCase`).
  - Gerencia a interação entre o domínio e os adaptadores externos.

- **Adaptadores (Adapters):**
  - **Entrada (Inbound):** Controladores REST que recebem as requisições HTTP e invocam os casos de uso.
  - **Saída (Outbound):** Repositórios que interagem com o banco de dados PostgreSQL.

- **Configuração:**
  - Configurações de infraestrutura, como a integração com o banco de dados PostgreSQL e a configuração do Swagger.

## Entidades Principais

1. **AccountEntity:**
   - Representa uma conta de usuário com saldos em diferentes categorias.
   - Possui os campos `id`, `uuid`, `agency`, `accountNumber`, e `balances`.

2. **TransactionEntity:**
   - Representa uma transação de cartão de crédito.
   - Contém informações como `id`, `uuid`, `accountId`, `amount`, `merchant`, e `mcc`.

3. **MerchantRuleEntity:**
   - Contém as regras de mapeamento entre comerciantes e categorias.
   - Possui um método estático para determinar a categoria com base no nome do comerciante.

## Fluxo de Processamento

1. **Recepção da Transação:**
   - A transação é recebida pelo controlador (`AccountController`) via um endpoint REST.

2. **Processamento da Transação:**
   - O controlador invoca o caso de uso responsável por verificar a categoria apropriada e o saldo disponível.
   - Se o saldo for suficiente, a transação é aprovada e o saldo é debitado.
   - Se o saldo não for suficiente, o sistema tenta debitar o saldo da categoria CASH.

3. **Resposta:**
   - O sistema retorna uma resposta JSON com o código de autorização ou rejeição.

## Tecnologias Utilizadas

- **Linguagem:** Java
- **Framework:** Spring Boot (versão 2.5.x ou superior)
- **Banco de Dados:** PostgreSQL
- **Documentação da API:** Swagger (via Springdoc OpenAPI)
- **Testes Unitários:** JUnit 5, Mockito
- **Docker:** Contêinerização do banco de dados PostgreSQL para facilitar a configuração e a execução do ambiente de desenvolvimento.

## Deploy e Execução

- **Docker:** Um `Dockerfile` e um `docker-compose.yml` foram fornecidos para contêinerizar a aplicação e o banco de dados PostgreSQL.
- **Swagger:** A documentação da API pode ser acessada via o endpoint `/swagger-ui.html` após a execução da aplicação.

## Testes
Testes foram implementados para garantir o correto funcionamento dos controladores e das regras de negócio principais, incluindo a verificação de autorização de transações e a aplicação de regras de fallback.

## Considerações Finais
O sistema foi projetado para ser extensível, permitindo a adição de novas regras de negócio, categorias, ou comerciantes sem alterar significativamente a estrutura existente. A arquitetura hexagonal adotada facilita a manutenção e a adaptação do sistema para novas exigências de negócio ou tecnologias.


## Desenho de solução

![WhatsApp Image 2024-08-09 at 21 18 40](https://github.com/user-attachments/assets/616c636f-ee9d-41aa-abc4-e083c6ea5868)
