# 📚 Library API

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-green?style=for-the-badge&logo=springsecurity)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

Uma API RESTful robusta e escalável para gestão de bibliotecas, desenvolvida com **Java** e **Spring Boot**[cite: 2]. O sistema permite administrar livros e autores, incluindo suporte para autenticação segura via **JWT** e validação de dados[cite: 2].

---

## 🚀 Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Framework Principal:** Spring Boot 3
- **Persistência de Dados:** Spring Data JPA / Hibernate
- **Segurança:** Spring Security & JWT (JSON Web Tokens)[cite: 2]
- **Validação:** Jakarta Validation[cite: 2]
- **Gestão de Dependências:** Maven[cite: 2]

---

## 📌 Funcionalidades

- 📖 **Gestão de Livros:** Criação, leitura, atualização e remoção (CRUD) de livros.
- ✍️ **Gestão de Autores:** Associação de autores aos respetivos livros.
- 🔒 **Autenticação & Autorização:** Controlo de acessos seguro utilizando tokens JWT[cite: 2].
- 🛡️ **Validação de Dados:** Garantia de integridade nas requisições via Jakarta Validation[cite: 2].

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- **Java JDK 17** ou superior instalado.
- **Maven** instalado (ou utilize o wrapper `./mvnw`).
- Base de dados configurada (H2 / PostgreSQL / MySQL) no ficheiro `application.properties`.

### Passos para Instalação

1. **Clona o repositório:**
   ```bash
   git clone [https://github.com/Henrikel-1/LibraryAPI-Spring-Boot.git](https://github.com/Henrikel-1/LibraryAPI-Spring-Boot.git)
   cd LibraryAPI-Spring-Boot
