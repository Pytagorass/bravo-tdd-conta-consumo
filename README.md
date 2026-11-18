# BRAVO — Pedidos e conta de consumo

Atividade prática de Teste de Software do IFMT. O projeto aplica Test-Driven
Development (TDD) ao módulo de restaurante e bar do BRAVO, do lançamento do
pedido até a quitação e o fechamento da conta da reserva.

## Requisitos

O [documento de requisitos](docs/requisitos.md) descreve as quatro classes,
os 15 métodos previstos e os cenários de teste.

## Como executar

Pré-requisitos **JDK 17 ou superior** e **Maven 3.6.3 ou superior**.

Para executar os testes:

```sh
mvn test
```

Para executar a verificação completa com cobertura:

```sh
mvn clean verify
```

O relatório JaCoCo ficará em `target/site/jacoco/index.html` após a inclusão
de testes e classes de produção.

## Estrutura

| Caminho | Conteúdo |
|---|---|
| `docs/requisitos.md` | Escopo, regras de negócio e cenários de teste. |
| `src/main/java` | Código do domínio. |
| `src/test/java` | Testes automatizados com JUnit 5. |

## Situação inicial

Estrutura Maven configurada. O primeiro ciclo começa com um teste que falha
pelo comportamento esperado ainda não existir. Os resultados de testes e
cobertura serão registrados à medida que o trabalho avançar.

## Integrante

- Sergio Pytagoras Constantini
