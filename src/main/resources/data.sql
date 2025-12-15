-- Criar o banco de dados
CREATE DATABASE financeiro;

-- Conectar no banco
\c financeiro;

-- Criar tabela de usuários
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL
);

-- Criar tabela de categorias
CREATE TABLE categories (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(50) NOT NULL,
                            user_id INT REFERENCES users(id) ON DELETE CASCADE
);

-- Criar tabela de transações
CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              description VARCHAR(255) NOT NULL,
                              amount NUMERIC(12,2) NOT NULL,
                              date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              category_id INT REFERENCES categories(id) ON DELETE SET NULL,
                              user_id INT REFERENCES users(id) ON DELETE CASCADE
);
-- Inserir usuários
INSERT INTO users (username, password, role) VALUES
                                                 ('admin', '$2a$10$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'ROLE_ADMIN'), -- senha já hash (ex: "admin123")
                                                 ('wash', '$2a$10$YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY', 'ROLE_USER');   -- senha hash (ex: "1234")

-- Inserir categorias para usuário 'wash'
INSERT INTO categories (name, user_id) VALUES
                                           ('Alimentação', 2),
                                           ('Transporte', 2),
                                           ('Lazer', 2);

-- Inserir transações para usuário 'wash'
INSERT INTO transactions (description, amount, date, category_id, user_id) VALUES
                                                                               ('Supermercado', 150.00, '2025-12-10 10:00:00', 1, 2),
                                                                               ('Uber', 35.50, '2025-12-11 18:30:00', 2, 2),
                                                                               ('Cinema', 45.00, '2025-12-12 20:00:00', 3, 2);
