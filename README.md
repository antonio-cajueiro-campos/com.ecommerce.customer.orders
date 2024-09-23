# E-commerce Order Processing System

Este repositório contém um sistema de processamento de pedidos para um e-commerce, utilizando Kafka, Spring Boot e um banco de dados não-relacional.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Kafka**: Sistema de mensagens para processamento de eventos em tempo real.
- **MongoDB**: Banco de dados não-relacional para armazenar os dados dos pedidos.
- **ModelMapper**: Biblioteca para mapeamento de objetos.

## Como Rodar o Sistema Localmente

### Pré-requisitos

- Java 11 ou superior
- Maven
- MongoDB
- Kafka

### Passo a Passo

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/repo.git
   cd repo
   docker-compose up -d
   ```


# Decisões Técnicas
## Escolha do Banco de Dados Não-Relacional
Optei pelo uso do MongoDB por várias razões:

- Flexibilidade de Dados: A modelagem dos dados no MongoDB permite armazenar informações de forma não rígida, ideal para aplicações onde a estrutura dos dados pode evoluir ao longo do tempo.
- Escalabilidade: O MongoDB oferece suporte a uma fácil escalabilidade horizontal, o que é benéfico para um sistema que pode crescer em volume de dados e tráfego.
- Desempenho: Para operações de leitura e gravação intensivas, o MongoDB demonstrou desempenho superior em comparação com bancos de dados relacionais tradicionais.

## Modelagem dos Dados
A modelagem dos dados foi feita levando em consideração:

- Estruturas de Dados Simples: Cada pedido é armazenado como um documento, o que simplifica a leitura e a manipulação dos dados.
- Relações e Indexação: Utilizamos referências entre entidades onde necessário, garantindo um acesso rápido e eficiente às informações relacionadas.
- Simplicidade de Consulta: A estrutura dos documentos facilita consultas complexas e análises em tempo real, aproveitando a flexibilidade do MongoDB.
