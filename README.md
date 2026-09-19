# 📚 Library API

![Java](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen?style=for-the-badge&logo=spring)
![Gradle](https://img.shields.io/badge/Gradle-Kotlin%20DSL-02303A?style=for-the-badge&logo=gradle)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-green?style=for-the-badge&logo=springsecurity)
![H2](https://img.shields.io/badge/Database-H2-blue?style=for-the-badge&logo=h2)

API RESTful para gestão de biblioteca, desenvolvida com **Java** e **Spring Boot**. Permite gerenciar livros e usuários, com autenticação e autorização via **JWT** e controle de acesso baseado em papéis (**ADMIN** / **USER**).

---

## 🚀 Tecnologias Utilizadas

- **Linguagem:** Java 25
- **Framework:** Spring Boot 4.1.0
- **Build:** Gradle (Kotlin DSL)
- **Persistência:** Spring Data JPA / Hibernate
- **Banco de Dados:** H2 (em memória)
- **Segurança:** Spring Security + JWT (biblioteca `jjwt`)
- **Validação:** Jakarta Validation
- **Utilitários:** Lombok

---

## 📌 Funcionalidades

- 📖 **Gestão de Livros:** CRUD completo, atualização parcial (PATCH), busca por título e paginação.
- 👤 **Gestão de Usuários:** CRUD completo, atualização parcial (PATCH), busca por nome e paginação.
- 🔐 **Autenticação:** cadastro público (`/auth/signup`) e login (`/auth/login`) com emissão de token JWT.
- 🛡️ **Autorização por papel:** rotas de `/usuarios` restritas a **ADMIN**; leitura de `/livros` liberada para **USER** e **ADMIN**, escrita restrita a **ADMIN**.
- ✅ **Validação de dados** com mensagens de erro em português.
- ⚠️ **Tratamento centralizado de exceções** (validação e regras de negócio) com respostas padronizadas.
- 🔑 **Senhas criptografadas** com BCrypt.

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- **JDK 25** instalado (o projeto usa Gradle Toolchain, então o Gradle pode baixar o JDK correto automaticamente).
- Não é necessário instalar o Gradle — o projeto já inclui o wrapper (`gradlew`).

### Passos para Instalação

1. **Clone o repositório:**
```bash
   git clone https://github.com/Henrikel-1/LibraryAPI-Spring-Boot.git
   cd LibraryAPI-Spring-Boot
```

2. **Execute a aplicação:**
```bash
   ./gradlew bootRun
```
   No Windows, use `gradlew.bat bootRun`.

3. **Acesse a API** em `http://localhost:8080`.

O banco de dados é um **H2 em memória**, criado automaticamente ao subir a aplicação (os dados são perdidos ao reiniciar). O console do H2 fica disponível em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:library`, usuário `sa`, sem senha).

### Configuração do JWT

O segredo usado para assinar os tokens pode ser definido pela variável de ambiente `JWT_SECRET`. Se não for definida, um valor padrão de desenvolvimento é usado (não recomendado para produção):

```bash
export JWT_SECRET=sua-chave-secreta-aqui
```

### Configuração do Admin Padrão

Como não existia como criar o primeiro `ADMIN` (só era possível via `POST /usuarios`, que já exige login como `ADMIN`), a aplicação cria um admin automaticamente no startup se nenhum existir ainda. As credenciais padrão são `admin@libraryapi.com` / `admin123`, e podem ser sobrescritas pelas variáveis `ADMIN_EMAIL` e `ADMIN_SENHA`:

```bash
export ADMIN_EMAIL=seu-email@dominio.com
export ADMIN_SENHA=sua-senha-aqui
```

---

## 🔑 Autenticação e Autorização

Todos os usuários criados via `POST /auth/signup` recebem o papel `USER`. O primeiro `ADMIN` (`admin@libraryapi.com` / `admin123` por padrão) é criado automaticamente no startup da aplicação — veja [Configuração do Admin Padrão](#configuração-do-admin-padrão). A partir dele, `POST /usuarios` cria os demais administradores.

Para acessar rotas protegidas, envie o token no cabeçalho:

| Recurso | Regra de acesso |
|---|---|
| `POST /auth/signup`, `POST /auth/login` | Público |
| `GET /livros/**` | `USER` ou `ADMIN` |
| `POST` / `PUT` / `PATCH` / `DELETE /livros/**` | Somente `ADMIN` |
| `/usuarios/**` (todas as operações) | Somente `ADMIN` |

---

## 📑 Endpoints

### Autenticação (`/auth`)
| Método | Rota | Descrição |
|---|---|---|
| POST | `/auth/signup` | Cadastra um novo usuário (papel `USER`) |
| POST | `/auth/login` | Autentica e retorna o token JWT (texto puro) |

**Corpo de `/auth/signup`:**
```json
{
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "senha": "senha123"
}
```

**Corpo de `/auth/login`:**
```json
{
  "email": "maria@email.com",
  "senha": "senha123"
}
```

### Livros (`/livros`)
| Método | Rota | Descrição |
|---|---|---|
| GET | `/livros` | Lista livros paginados (padrão: 10 por página, ordenado por título) |
| GET | `/livros/{id}` | Busca um livro pelo ID |
| GET | `/livros/titulo?titulo=` | Busca livros por título |
| POST | `/livros` | Cadastra um novo livro |
| PUT | `/livros/{id}` | Atualiza um livro (todos os campos) |
| PATCH | `/livros/{id}` | Atualiza parcialmente um livro |
| DELETE | `/livros/{id}` | Remove um livro |

**Corpo de `POST`/`PUT /livros`:**
```json
{
  "titulo": "Dom Casmurro",
  "anoPubli": 1899,
  "editora": "Garnier",
  "escritor": "Machado de Assis"
}
```

> O título é único: tentar cadastrar ou renomear um livro para um título já existente retorna `409 Conflict`.

### Usuários (`/usuarios`) — acesso somente ADMIN
| Método | Rota | Descrição |
|---|---|---|
| GET | `/usuarios` | Lista usuários paginados (padrão: 10 por página, ordenado por nome) |
| GET | `/usuarios/{id}` | Busca um usuário pelo ID |
| GET | `/usuarios/nome?nome=` | Busca usuários por nome |
| POST | `/usuarios` | Cadastra um novo usuário com papel `ADMIN` |
| PUT | `/usuarios/{id}` | Atualiza um usuário (todos os campos) |
| PATCH | `/usuarios/{id}` | Atualiza parcialmente um usuário (nome, email, senha e/ou papel) |
| DELETE | `/usuarios/{id}` | Remove um usuário |

> O e-mail é único: tentar cadastrar ou alterar para um e-mail já existente retorna `409 Conflict`.

---

## ⚠️ Tratamento de Erros

**Erros de validação** (`400 Bad Request`):
```json
{
  "errors": {
    "titulo": "O Titulo é obrigatório"
  }
}
```

**Erros de regra de negócio** (`404`, `409` ou `400`, dependendo do caso):
```json
{
  "erro": "Já existe um livro cadastrado com esse título"
}
```

---

## 👤 Autor

Desenvolvido por **Keldson Henriques**.

- GitHub: [@Henrikel-1](https://github.com/Henrikel-1)
