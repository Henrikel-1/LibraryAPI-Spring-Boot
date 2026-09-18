# 📚 Library API

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-green?style=for-the-badge&logo=springsecurity)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

Uma API RESTful robusta e escalável para gestão de bibliotecas, desenvolvida com **Java** e **Spring Boot**. O sistema permite administrar livros e autores, incluindo suporte para autenticação segura via **JWT** e validação de dados.

---

## 🚀 Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Framework Principal:** Spring Boot 3
- **Persistência de Dados:** Spring Data JPA / Hibernate
- **Segurança:** Spring Security & JWT (JSON Web Tokens)
- **Validação:** Jakarta Validation
- **Gestão de Dependências:** Maven

---

## 📌 Funcionalidades

- 📖 **Gestão de Livros:** Criação, leitura, atualização e remoção (CRUD) de livros.
- ✍️ **Gestão de Autores:** Associação de autores aos respetivos livros.
- 🔒 **Autenticação & Autorização:** Controlo de acessos seguro utilizando tokens JWT.
- 🛡️ **Validação de Dados:** Garantia de integridade nas requisições via Jakarta Validation.

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

Configura as propriedades (opcional):
Ajusta as credenciais e configurações da base de dados no ficheiro:
src/main/resources/application.properties

Compila e executa a aplicação:

Bash
./mvnw spring-boot:run

A API estará acessível em: http://localhost:8080
📑 Endpoints Principais (Exemplo)
Método   Endpoint            Descrição
POST	   /autenticacao/login	Realiza o login e retorna o token JWT
GET	   /livros	            Lista todos os livros cadastrados
POST	   /livros	            Cadastra um novo livro
GET	   /livros/{id}	      Busca um livro específico pelo ID
DELETE	/livros/{id}	      Remove um livro do sistema

Autor
Desenvolvido por Keldson Henriques.

GitHub: @Henrikel-1

LinkedIn: Keldson Henriques
