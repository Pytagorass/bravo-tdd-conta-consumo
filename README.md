# BRAVO — Pedidos e conta de consumo

Projeto da disciplina de Teste de Software do IFMT. O BRAVO é um sistema de gestão de barco-hotel. Esta atividade aplica desenvolvimento orientado a testes (TDD) às regras do restaurante e do bar, desde o lançamento de itens até a quitação e o fechamento da conta vinculada à reserva.

O exercício concentra as regras de negócio em Java e usa dados fictícios em memória. Os pagamentos registram valores na conta de consumo.

## Regras de negócio

- Cada item guarda o preço aplicado no lançamento. Alterações no cardápio afetam lançamentos futuros.
- O pedido aceita produtos ativos e quantidades positivas. A confirmação exige pelo menos um item e bloqueia edições posteriores.
- A conta aceita pedidos confirmados e rejeita identificadores de pedido duplicados.
- Pagamentos devem ser positivos e podem ser parciais ou integrais, até o limite do saldo devido.
- O fechamento exige saldo zerado. Após fechar, a conta bloqueia novos pedidos e pagamentos.

O [documento de requisitos](docs/requisitos.md) detalha as assinaturas dos métodos, as regras e os cenários de teste.

## Classes do domínio

| Classe | Responsabilidade | Métodos de negócio |
|---|---|---:|
| `Produto` | Mantém o preço vigente e a disponibilidade no cardápio. | 3 |
| `ItemPedido` | Guarda o preço de lançamento, controla a quantidade e calcula o subtotal. | 2 |
| `Pedido` | Gerencia os itens, calcula o total e controla a confirmação. | 5 |
| `ContaConsumo` | Registra pedidos e pagamentos, calcula o saldo e controla o fechamento. | 5 |
| **Total** | | **15** |

## Como executar

Requisitos de ambiente: **JDK 17 ou superior**, **Maven 3.6.3 ou superior** e **Git** para clonar o projeto. Os testes usam JUnit 5 e a cobertura é medida pelo JaCoCo.

```bash
git clone https://github.com/Pytagorass/bravo-tdd-conta-consumo.git
cd bravo-tdd-conta-consumo
mvn test
```

Para executar a suíte e gerar o relatório de cobertura, rode:

```bash
mvn clean verify
```

Abra `target/site/jacoco/index.html` no navegador e consulte a linha **Total**.

## Resultados

| Indicador | Resultado da execução final |
|---|---:|
| Testes executados | 34 |
| Falhas | 0 |
| Erros | 0 |
| Testes ignorados | 0 |
| Cobertura de instruções | 100% (313 de 313) |
| Cobertura de ramos | 100% (32 de 32) |

A cobertura se refere às quatro classes Java do domínio desta atividade. O JaCoCo contabiliza construtores, consultas e métodos auxiliares, enquanto os 15 métodos da tabela correspondem às operações de negócio previstas nos requisitos.

## Ciclo TDD de exemplo

O cenário escolhido verifica a regra de quantidade inicial em `ItemPedido`. É possível acompanhar as três etapas pelos diffs dos commits.

| Etapa | Evidência | Commit |
|---|---|---|
| **Red** | O teste espera `IllegalArgumentException` ao criar um item com quantidade zero. A execução registra uma falha entre oito testes. | [`a2b64fd`](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/a2b64fd5585a8bd19b425642866b8818302dfd1c) |
| **Green** | O construtor passa a rejeitar quantidades menores que um. Os oito testes passam. | [`3f46a56`](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/3f46a5677e5a363754f41cce29c12b1af9c084ef) |
| **Refactor** | O construtor e `alterarQuantidade()` passam a usar a mesma validação privada. Os oito testes continuam passando. | [`7582666`](https://github.com/Pytagorass/bravo-tdd-conta-consumo/commit/7582666729987fc3c2506769fc7c825ec3d02fc1) |

## Estrutura do projeto

| Caminho | Conteúdo |
|---|---|
| `pom.xml` | Configuração do Maven, do JUnit 5 e da geração de cobertura com JaCoCo. |
| `docs/requisitos.md` | Contexto, assinaturas, regras de negócio e cenários de teste. |
| `src/main/java/br/edu/ifmt/bravo/consumo/` | Implementação das quatro classes do domínio. |
| `src/test/java/` | Testes automatizados de produtos, itens, pedidos e contas. |
| `target/site/jacoco/` | Relatório de cobertura gerado localmente pelo Maven. |

## Integrante

Sergio Pytagoras Constantini
