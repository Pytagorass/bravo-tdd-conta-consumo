# Análise dos benefícios do TDD no BRAVO

**Disciplina** — Teste de Software · IFMT  
**Integrante** — Sergio Pytagoras Constantini

## 1. Evolução do código orientada pelos testes

A atividade aplicou o ciclo Red, Green e Refactor às regras de pedidos e conta de consumo de um barco-hotel. Os testes começaram pelas operações de produto e item, avançaram para a confirmação do pedido e terminaram com os pagamentos e o fechamento da conta da reserva.

| Necessidade verificada | Decisão de implementação |
|---|---|
| Um reajuste no cardápio deve preservar o preço de itens já lançados. | `ItemPedido` guarda o preço unitário recebido no lançamento. Quando a quantidade muda, o subtotal usa esse mesmo preço. |
| Um pedido confirmado deve preservar seu total. | `Pedido` controla a confirmação e verifica esse estado antes de incluir, remover ou alterar itens. |
| Pedidos com o mesmo identificador devem gerar uma única cobrança na conta. | `ContaConsumo` passou de uma lista para um mapa indexado pelo identificador do pedido. A tentativa de duplicar preserva o lançamento original. |
| Pagamento parcial deve reduzir o saldo sem alterar o consumo. | `ContaConsumo` calcula o saldo pela diferença entre o total consumido e o total pago. O fechamento exige saldo zero. |

## 2. Falhas identificadas antes da integração

- **Quantidade inicial zero.** O construtor aceitava zero, embora a alteração de quantidade já rejeitasse esse valor. O novo teste expôs a ausência da validação na criação do item. Veja o [Red da quantidade inicial](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/a2b64fd5585a8bd19b425642866b8818302dfd1c).
- **Cobrança duplicada.** A primeira implementação de `registrarPedido()` permitia incluir dois pedidos com o mesmo identificador. O teste usou dois objetos distintos com o mesmo código e verificou que o total deveria continuar em R$ 16,00 após a rejeição. A correção passou a indexar os pedidos pelo identificador. Veja o [teste de duplicidade](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/d5432404a9d1f1f7b4fe3065d8f6dfe73db527c6) e a [implementação](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/423155ffa1a1b586b9e1921c3b786e178c259fcd).
- **Import desatualizado.** Depois da mudança de pacote de `ItemPedido`, a versão publicada do teste manteve o import antigo. O problema impedia a compilação da suíte e foi corrigido removendo o import, pois a classe e o teste já estavam no mesmo pacote. Veja a [correção do import](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/380e9c1bdd696b3eb24e8886bf43465077590791).

Os dois primeiros casos foram falhas esperadas da fase Red. O terceiro foi um problema de compilação, identificado ao conferir a versão publicada. Essa distinção ajuda a verificar se um teste falhou pelo comportamento que se pretendia exercitar.

## 3. Ciclo completo de referência

| Etapa | O que aconteceu | Resultado |
|---|---|---|
| [Red](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/a2b64fd5585a8bd19b425642866b8818302dfd1c) | Um teste exigiu `IllegalArgumentException` ao criar `ItemPedido` com quantidade zero. | 8 testes, 1 falha esperada. |
| [Green](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/3f46a5677e5a363754f41cce29c12b1af9c084ef) | O construtor passou a rejeitar quantidades menores que um. | 8 testes, 0 falhas. |
| [Refactor](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/7582666729987fc3c2506769fc7c825ec3d02fc1) | O construtor e `alterarQuantidade()` passaram a chamar o método privado `validarQuantidade()`. | 8 testes, 0 falhas após a refatoração. |

## 4. Comparação entre as abordagens

A comparação com testes escritos depois é hipotética. Nesta atividade, foi executada uma única implementação, desenvolvida com TDD.

| Aspecto | Com TDD no BRAVO | Com testes escritos após a implementação |
|---|---|---|
| Regras de negócio | O teste definiu primeiro o comportamento esperado, como rejeitar quantidade inicial zero. | A regra poderia ser verificada na revisão ou na elaboração posterior dos casos de teste. |
| Mudanças de design | O teste de pedido duplicado motivou a escolha de um mapa indexado pelo identificador. | A estrutura poderia ser escolhida no início e revista ao aparecer o cenário de duplicidade. |
| Refatoração | A mesma suíte foi executada antes e depois de centralizar a validação. | A mesma comparação seria possível quando já existisse uma suíte para os comportamentos afetados. |

## 5. Lições aprendidas

- **Uma falha precisa ser interpretável.** Antes de implementar a correção, a mensagem do JUnit deve indicar qual regra ainda falta.
- **Vale verificar o estado após uma rejeição.** Os testes conferem, por exemplo, que um pagamento inválido preserva o saldo e que um pedido duplicado preserva o total.
- **O diff ajuda a auditar o histórico.** Dois commits com mensagem de Red continham implementações Green. A inspeção do conteúdo permitiu reconstituir a sequência e mostrou a importância de conferir a mensagem antes do envio. Veja o [Green do preço negativo](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/58b60b5) e o [Green da desativação](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/98099bd).
- **Cobertura e asserções respondem a perguntas diferentes.** A cobertura indica o código executado. As asserções verificam o resultado esperado para cada cenário.

## 6. Resultado final

A execução final registrou **34 testes, 0 falhas e 0 erros**. O relatório JaCoCo registrou **100% de cobertura de instruções (313 de 313)** e **100% de cobertura de ramos (32 de 32)** nas quatro classes Java desta atividade. Os números se referem ao módulo acadêmico de pedidos e conta de consumo do repositório.

Para reproduzir a verificação, execute `mvn clean verify` na raiz do projeto e abra `target/site/jacoco/index.html`.

[Documento de requisitos](requisitos.md) · [README do projeto](../README.md)
