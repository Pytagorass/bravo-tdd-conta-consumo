# BRAVO — Requisitos do módulo de pedidos e conta de consumo

**Disciplina** · Teste de Software  
**Atividade** · Desenvolvimento Orientado a Testes (TDD)  
**Situação do tema** · Proposta para aprovação prévia do professor

## 1. Contexto e escopo

O BRAVO é um sistema de gestão de operações de barco-hotel. Neste trabalho, o módulo de restaurante e bar permitirá lançar pratos e bebidas em um pedido, confirmar o consumo e registrar o pedido na conta vinculada à reserva do hóspede. A conta calcula o total consumido, recebe pagamentos parciais ou integrais e pode ser fechada quando o saldo chega a zero. O exercício modela essas regras em Java 17, com dados fictícios em memória e testes automatizados com JUnit 5. Os pagamentos representam lançamentos de valores na conta.

## 2. Classes e responsabilidades

| Classe | Responsabilidade | Métodos |
|---|---|---:|
| `Produto` | Manter o preço vigente de um prato ou bebida e sua disponibilidade no cardápio. | 3 |
| `ItemPedido` | Manter a quantidade e o preço unitário aplicado no lançamento, além de calcular o subtotal. | 2 |
| `Pedido` | Controlar a inclusão, a edição e a confirmação dos itens consumidos. | 5 |
| `ContaConsumo` | Vincular o consumo à reserva e controlar os pedidos confirmados, os pagamentos e o fechamento. | 5 |
| **Total** | | **15** |

Os 15 métodos abaixo têm comportamento de negócio verificável. Construtores e métodos de consulta de atributos ficam fora da contagem.

## 3. Assinaturas e comportamentos esperados

| ID | Classe | Assinatura | Comportamento esperado |
|---|---|---|---|
| M01 | `Produto` | `void alterarPreco(BigDecimal novoPreco)` | Atualiza o preço para lançamentos futuros. Rejeita um preço negativo. |
| M02 | `Produto` | `void ativar()` | Disponibiliza o produto para novos lançamentos. |
| M03 | `Produto` | `void desativar()` | Indisponibiliza o produto para novos lançamentos. |
| M04 | `ItemPedido` | `void alterarQuantidade(int quantidade)` | Atualiza a quantidade do item. Rejeita valores menores que um. |
| M05 | `ItemPedido` | `BigDecimal calcularSubtotal()` | Multiplica o preço unitário aplicado no lançamento pela quantidade atual. |
| M06 | `Pedido` | `void adicionarItem(String idItem, Produto produto, int quantidade)` | Inclui um produto ativo com quantidade positiva e grava o preço vigente. Rejeita identificador de item já presente no pedido. |
| M07 | `Pedido` | `void removerItem(String idItem)` | Remove um item existente de um pedido em edição. |
| M08 | `Pedido` | `void alterarQuantidadeDoItem(String idItem, int quantidade)` | Altera a quantidade de um item existente em um pedido em edição, mantendo seu preço unitário. |
| M09 | `Pedido` | `BigDecimal calcularTotal()` | Soma os subtotais dos itens. Um pedido sem itens totaliza zero. |
| M10 | `Pedido` | `void confirmar()` | Confirma um pedido em edição com pelo menos um item. A confirmação bloqueia inclusões, remoções e alterações de quantidade. |
| M11 | `ContaConsumo` | `void registrarPedido(Pedido pedido)` | Inclui um pedido confirmado em uma conta aberta. Rejeita um pedido cujo identificador já consta na conta. |
| M12 | `ContaConsumo` | `BigDecimal calcularTotal()` | Soma os valores dos pedidos registrados, antes de descontar os pagamentos. |
| M13 | `ContaConsumo` | `void registrarPagamento(BigDecimal valor)` | Em uma conta aberta, aceita pagamentos positivos até o saldo devido, incluindo o valor exato do saldo. |
| M14 | `ContaConsumo` | `BigDecimal calcularSaldo()` | Retorna o total consumido menos o total pago. |
| M15 | `ContaConsumo` | `void fechar()` | Fecha uma conta aberta com saldo zero. A conta fechada bloqueia novos pedidos e pagamentos. |

## 4. Regras de negócio e cenários de teste planejados

| Regra | Métodos | Cenários previstos |
|---|---|---|
| RN01 — Preço e disponibilidade | M01–M03, M06 | Atualizar o preço e aplicá-lo ao próximo lançamento. Rejeitar preço negativo. Impedir a inclusão de produto desativado e permitir após a reativação. |
| RN02 — Quantidade e preço do lançamento | M04–M06, M08–M09 | Calcular subtotal com múltiplas unidades. Rejeitar quantidade zero e negativa na inclusão ou edição. Após reajustar o cardápio, conferir o preço original do item já lançado e o novo preço em um lançamento posterior. |
| RN03 — Edição dos itens | M06–M09 | Somar dois itens. Alterar quantidade e recalcular o total. Remover item e recalcular. Rejeitar item duplicado e alteração ou remoção de item inexistente. |
| RN04 — Confirmação do pedido | M06–M08, M10 | Confirmar pedido com itens. Rejeitar confirmação de pedido vazio e tentativas de alterar o pedido após confirmar. |
| RN05 — Pedidos na conta | M11–M12 | Registrar pedidos confirmados e somar os valores. Rejeitar pedido em edição e registro duplicado na mesma conta. |
| RN06 — Quitação | M12–M14 | Registrar pagamento parcial e conferir o saldo restante. Registrar o valor exato do saldo e conferir saldo zero. Rejeitar pagamento zero, negativo ou acima do saldo. |
| RN07 — Fechamento | M11, M13, M15 | Bloquear fechamento com saldo pendente. Aceitar fechamento com saldo zero. Rejeitar novos pedidos e pagamentos depois de fechar. |

### Exceções por classe

| Classe | Cenário | Exceção prevista |
|---|---|---|
| `Produto` | Alterar o preço para um valor negativo. | `IllegalArgumentException` |
| `ItemPedido` | Alterar a quantidade para zero. | `IllegalArgumentException` |
| `Pedido` | Confirmar um pedido sem itens. | `IllegalStateException` |
| `ContaConsumo` | Fechar enquanto há saldo pendente. | `IllegalStateException` |

Quando uma operação for rejeitada, o teste deve verificar a preservação do estado sempre que o efeito puder ser observado. Por exemplo, um pagamento acima do saldo deve deixar o saldo anterior intacto.

## 5. Cenário de aceitação

1. Lançar duas bebidas a R$ 8,00 em um pedido.
2. Atualizar o preço de cardápio para R$ 10,00 e conferir que o pedido já lançado continua totalizando R$ 16,00.
3. Confirmar o pedido e registrá-lo na conta da reserva. Conferir o saldo de R$ 16,00.
4. Registrar um pagamento de R$ 10,00. Conferir o saldo de R$ 6,00 e a rejeição do fechamento.
5. Registrar um pagamento de R$ 6,00, conferir o saldo zero e fechar a conta.
6. Verificar que a conta fechada rejeita novos pedidos e pagamentos.
