CREATE DATABASE IF NOT EXISTS hotelDB;
USE hotelDB;

CREATE TABLE IF NOT EXISTS hospede (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cpf VARCHAR(20) UNIQUE NOT NULL,
  telefone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS quarto (
  id INT AUTO_INCREMENT PRIMARY KEY,
  numero INT NOT NULL,
  tipo VARCHAR(50),
  valor_diaria DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS reserva (
  id INT AUTO_INCREMENT PRIMARY KEY,
  hospede_id INT,
  quarto_id INT,
  dias INT,
  total DOUBLE,
  FOREIGN KEY (hospede_id) REFERENCES hospede(id),
  FOREIGN KEY (quarto_id) REFERENCES quarto(id)
);

INSERT INTO quarto (numero, tipo, valor_diaria) VALUES
(101, 'Simples', 150),
(102, 'Luxo', 300),
(201, 'Suíte Master', 500);
