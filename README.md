# Sistema de Gestão de Restaurantes — Tech Challenge

Este projeto é uma API REST desenvolvida com Spring Boot com o objetivo de oferecer uma plataforma centralizada para a gestão de múltiplos restaurantes. A aplicação permite o cadastro de usuários, tipos de usuário (cliente e dono), restaurantes, itens de cardápio, além da realização de pedidos e avaliações.

A arquitetura segue os princípios de Clean Architecture, com foco em boas práticas de desenvolvimento e testes automatizados. O projeto está preparado para execução com Docker.

---

## 📚 Tecnologias e Ferramentas

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Lombok
- Swagger (springdoc-openapi)
- Docker & Docker Compose
- JUnit 5 + MockMvc

---

## 🧱 Arquitetura

O projeto segue princípios de Clean Architecture com separação clara por camadas:

```
src/
├── domain/              # Entidades e enums
├── application/         # Serviços e lógica de negócio
├── infrastructure/      # Repositórios, mappers, etc.
├── web/                 # Controllers e DTOs
├── config/              # Swagger, ExceptionHandler, etc.
└── test/                # Testes unitários e de integração
```

---

## 🚀 Execução via Docker Compose

### Pré-requisitos

- Docker
- Docker Compose

### Build da aplicação

Antes de subir os containers, gere o `.jar` da aplicação:

```bash
./mvnw clean package
```

### Subindo os containers

```bash
docker-compose up --build
```

A aplicação estará disponível em:  
📍 `http://localhost:8080`  
Swagger (documentação interativa):  
📚 `http://localhost:8080/swagger-ui.html`

---


## 🧪 Testes Automatizados

Para rodar os testes automatizados:

```bash
./mvnw test
```

A aplicação possui cobertura de testes com JUnit 5 e MockMvc, com foco em cenários reais de uso (integração + validações).

---

## 📁 Collection Postman

A collection com todos os endpoints e payloads está disponível no diretório `/docs` do projeto.

---

## 🎥 Vídeo de Demonstração

O vídeo demonstrando a aplicação, as principais funcionalidades e a execução via Docker está disponível em:

🔗 [link-do-youtube-ou-drive]

---

## 🔗 Repositório

https://github.com/seu-usuario/restaurante-tech-challenge

---

## ✍️ Autor

Projeto desenvolvido por **Vinicius** — Fase 2 | Tech Challenge FIAP
