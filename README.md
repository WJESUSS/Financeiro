# 💰 API Financeiro Pessoal

API REST profissional para controle financeiro pessoal, com autenticação JWT, controle de usuários e permissões por perfil.

---

## 🚀 Tecnologias Utilizadas

* Java 21
* Spring Boot 3
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* PostgreSQL (ou H2 para testes)
* Maven
* Lombok

---

## 🔐 Autenticação e Autorização

A aplicação utiliza **JWT** para autenticação e controle de acesso.

### Perfis de usuário

* `ROLE_USER` → Usuário comum
* `ROLE_ADMIN` → Administrador

Alguns endpoints são restritos apenas a administradores.

---

## 📦 Endpoints da API

### 🔑 Autenticação

#### Registrar usuário

```
POST /api/auth/register
```

```json
{
  "username": "admin",
  "password": "123456",
  "role": "ADMIN"
}
```

#### Login

```
POST /api/auth/login
```

```json
{
  "username": "admin",
  "password": "123456"
}
```

🔹 Retorna um **token JWT**.

---

### 👤 Usuários

#### Listar todos os usuários (ADMIN)

```
GET /api/users
Authorization: Bearer TOKEN
```

#### Buscar usuário por ID (ADMIN)

```
GET /api/users/{id}
Authorization: Bearer TOKEN
```

#### Perfil do usuário logado

```
GET /api/users/me
Authorization: Bearer TOKEN
```

#### Atualizar perfil do usuário logado

```
PUT /api/users/me
Authorization: Bearer TOKEN
```

```json
{
  "username": "novoNome",
  "password": "novaSenha"
}
```

---

## ⚙️ Configuração JWT

No arquivo `application.properties`:

```properties
jwt.secret=CHAVE_SECRETA_FORTE_AQUI
jwt.expiration=3600000
```

---

## ▶️ Como executar o projeto

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/api-financeiro

# Entrar no projeto
cd api-financeiro

# Rodar aplicação
./mvnw spring-boot:run
```

A aplicação será iniciada em:

```
http://localhost:8080
```

---

## 🧪 Testes

Os endpoints podem ser testados via:

* Postman
* Insomnia

Sempre utilize o **token JWT** no header:

```
Authorization: Bearer TOKEN
```

---

## 📁 Estrutura do Projeto

```
com.apifinanceiro.Financeiro
├── controller
├── domain
├── repository
├── service
├── security
└── FinanceiroApplication.java
```

---

## 🧠 Boas Práticas Aplicadas

* Separação de camadas (Controller / Service / Repository)
* Autenticação stateless com JWT
* Controle de acesso por roles
* Código limpo e organizado
* Pronto para evolução (DTOs, testes, Docker)

---

## 📌 Próximas melhorias

* DTOs e validação com Bean Validation
* Refresh Token
* Documentação com Swagger
* Testes unitários e de integração
* Dockerização

---

## 👨‍💻 Autor

**Washington Santos**
Desenvolvedor Back-End Java | Spring Boot | PostgreSQL

📧 Contato: [washquesia@gmail.com](mailto:washquesia@gmail.com)
