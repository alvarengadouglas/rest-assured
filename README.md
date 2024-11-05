<p align="center">
  <a href="https://unform.dev">
    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTx2BoZf-nQXK6CNdeFsXyl590aWMnnd_x3og&s" alt="Rest-Assured" />
  </a>
</p>

<p align="center">Testar e validar serviços REST em Java é mais difícil do que em linguagens dinâmicas como Ruby e Groovy! 🚀</p>

## Tecnologias
<div align="center">

Esse projeto foi construído com as seguintes tecnologias.

[![Java](https://img.shields.io/badge/Java-11.x.x-blue?style=for-the-badge&logo=Java&color=red)](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)<space><space>
[![RestAssured](https://img.shields.io/badge/Rest_Assured-5.3.0-blue?style=for-the-badge&logo=&color=greenm)](https://rest-assured.io/)<space><space>


</div>

## Sobre o projeto

Esse é um projeto básico de teste de performance integrado a um pipeline de CI do GitHub Actions.

Ao rodar o pipeline você terá as seguintes etapas:
- Instalação do Docker.
- Checkout do código fonte.
- Execução dos tests.
- Extração do relatório.
- Disponibilização do relatório como artefato no GitHub.

## Arquitetura:

```
├── README.md
├── pom.xml
├── ./github/workflows/pipeline.yaml
├── assets
│   └── imagens //Imagens para o README.md
└── src
    └── test
        ├── java
        │   ├── config
        │   │   └── Configuracoes.java
        │   ├── enums
        │   │   └── PayloadPaths.java
        │   ├── factory
        │   │   └── PojoFactory.java
        │   ├── pojo
        │   │   └── Login.java
        │   ├── tests
        │   │   └── RunTest.java
        │   └── util
        │       ├── BaseTest.java
        │       ├── Logs.java
        │       └── RequestInteraction.java
        └── resources
            ├── payloads
            │   └── arquivos json
            └── schemas
                └── Pesquisa.feature
```



## Relatório:
O relatório é disponibilizado após o run da pipeline.
![alt text](./assets/report-in-github.png "Imagem de exemplo")

<div align="center">

#### Análise relatório - Seção Superior

![alt text](./assets/section-up.png "Imagem de exemplo")
</div>

##### Total Requests:
Indica o número total de requisições feitas durante o teste.

##### Failed Requests:
Mostra quantas requisições falharam.

##### Breached Thresholds:
Representa o número de vezes que os thresholds (limites) configurados foram violados. Aqui, não houveram violações.

##### Failed Checks:
Indica o número de "checks" (validações) que falharam. Checks são condições específicas que você pode definir para validar aspectos das respostas.

<div align="center">

#### Análise relatório - Request Metrics

![alt text](./assets/metric-section.png "Imagem de exemplo")
</div>
Essa seção detalha as métricas de desempenho das requisições HTTP, com os seguintes parâmetros:

| Colunas  | Descrição |
| ------------- | ------------- |
| Average  | Indica que, em média, cada requisição demorou para ser concluída. |
| Maximum  | A requisição mais lenta.  |
| Minimum  | A requisição mais rápida.  |
| Median   | Indica a mediana das requisições concluídas   |
| 90th Percentile  | 90% das requisições foram concluídas dentro do tempo esperado  |
| 95th Percentile  | 95% das requisições foram concluídas dentro do tempo esperado  |

| Métricas de linha:    |
| --------------------  | 
##### http_req_duration:
Tempo total de uma requisição HTTP, desde a conexão até o recebimento completo da resposta.

##### http_req_waiting:
Tempo que a requisição passou esperando pelo servidor, desde o envio até o início da resposta. É um indicador do tempo de processamento do servidor!

##### http_req_connecting:
Mede o tempo gasto na conexão inicial.

##### http_req_tls_handshaking:
Refere-se ao tempo gasto na negociação TLS (conexões HTTPS).

##### http_req_sending:
Mede o tempo gasto enviando dados para o servidor.

##### http_req_receiving:
Refere-se ao tempo gasto para receber dados do servidor após o início da resposta.

##### http_req_blocked:
O tempo que a requisição ficou bloqueada antes de ser processada.

##### iteration_duration:
Refere-se ao tempo total de cada iteração do teste, incluindo a execução do código e a espera entre as requisições.
