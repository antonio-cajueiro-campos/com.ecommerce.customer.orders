# E-commerce Order Processing System

Sistema de processamento de pedidos para a empresa "Ecommerce".
Projeto criado utilizando a arquitetura hexagonal em conjunto com os conceitos DDD, SOLID e DRY

## Definições
* **Application:** Contém os fluxos de caso de uso da aplicação.
* **Domain:** Contém as regras de negócio do projeto.
* **Adapters:** Contém os adaptadores externos da aplicação, como Web, Kafka, Repositories entre outros.

<p align="center">
    <img src="https://github.com/antonio-cajueiro-campos/com.ecommerce.customer.orders/blob/main/docs/hexagon-archtecture.drawio.png?raw=true" alt="Project Architecture">
</p>

## Tecnologias Empregadas
* **Linguagem:** Java 21.
* **Conteinerização:** Docker.
* **Framework:** SpringBoot 3.3.4 | Framework usado para o desenvolvimento da aplicação Java.
* **Database:** MongoDB | Banco de dados não-relacional para armazenar os dados dos pedidos.
* **Mensageria:** Kafka | Sistema de mensageria para processamento de eventos em tempo real.

## Como Rodar o Sistema Localmente

### Pré-requisitos

- Docker instalado na máquina
- Postman para realizar requisições

### Passo a Passo

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/antonio-cajueiro-campos/com.ecommerce.customer.orders.git
   cd com.ecommerce.customer.orders
   ```

2. **Inicie o docker compose para subir os containers:**
   ```bash
   docker-compose up -d
   ```

2. **Importe a Collection Postman:**
   Acesse docs/ e localize 'Customer Orders API.postman_collection.json'
   
## Observabilidade
1. Caso queira ter métricas de consumo, produção, logs de erros e alarmistica, o projeto inclui o pacote já configurado **Prometheus** para expor metricas e **Grafana** para facilitar na visualização e criação de dashboards
- URL Prometheus: [http://localhost:9090/](http://localhost:9090/)
- URL Grafana: [http://localhost:3000/](http://localhost:3000/)
- **Usuário:** admin
- **Senha:** admin

2. O Projeto também conta com KafDrop para analise de tópicos kafka
- URL KafDrop: [http://localhost:19000/](http://localhost:19000/)


## Decisões Técnicas
### Escolha do Banco de Dados Não-Relacional
Optei pelo uso do MongoDB por várias razões:

- Fortemente Consistente: Com as configurações de ReadConcern e WriteConcern como Majority, você consegue alcançar uma escrita e leitura fortemente consistente pois o mongo fará a verificação na maioria dos nós.
- Flexibilidade de Dados: A modelagem dos dados no MongoDB permite armazenar informações de forma não rígida, ideal para aplicações onde a estrutura dos dados pode evoluir ao longo do tempo.
- Escalabilidade: O MongoDB oferece suporte a uma fácil escalabilidade horizontal, o que é benéfico para um sistema que pode crescer em volume de dados e tráfego.
- Desempenho: Para operações de leitura e gravação intensivas, o MongoDB tem desempenho superior em comparação com bancos de dados relacionais tradicionais.

### Modelagem dos Dados
A modelagem dos dados foi feita levando em consideração:

- Estruturas de Dados Simples: Cada pedido é armazenado como um documento, o que simplifica a leitura e a manipulação dos dados.
- Relações e Indexação: Utilizamos referências entre entidades onde necessário, garantindo um acesso rápido e eficiente às informações relacionadas.
- Simplicidade de Consulta: A estrutura dos documentos facilita consultas complexas e análises em tempo real, aproveitando a flexibilidade do MongoDB.

### Consistência de Dados
Para manter a consistência dos dado em uma arquitetura de evento utilizei **Praticas de Compensação**:

- Ordem de Processamento dos Eventos: Particionamento de eventos
- Duplicidade de Eventos: Sistema de validação de Idempotência
- Erros por inconsistências temporárias: Dead letter queues (DLQs)
- Tolerância a Falhas e Disponibilidade: Circuit breakers

### Referências
- Consistência MongoDB: [https://www.mongodb.com/pt-br/docs/manual/core/read-isolation-consistency-recency/](https://www.mongodb.com/pt-br/docs/manual/core/read-isolation-consistency-recency/)
