# Sistema Hotel

Sistema de gerenciamento de hóspedes, quartos e reservas para um hotel.

## Estrutura do Projeto

```
hotelDB.sql
src/
  SistemaHotel.java          # Classe principal
  dao/
    Conexao.java             # Gerencia conexão com banco de dados
    HospedeDAO.java          # DAO para hóspedes
    QuartoDAO.java           # DAO para quartos
    ReservaDAO.java          # DAO para reservas
  model/
    Hospede.java             # Modelo de hóspede
    Quarto.java              # Modelo de quarto
    Reserva.java             # Modelo de reserva
  lib/
    mysql-connector-java-5.1.49.jar  # Driver JDBC MySQL
```

## Pré-requisitos

- Java JDK 8 ou superior
- MySQL Server instalado e rodando
- Driver JDBC MySQL (já incluído em `lib/`)

## Configuração do Banco de Dados

1. Exporte o script hotelDB.sql em um servidor local, o utilizado foi o XAMPP:

2. Verifique as credenciais em `src/dao/Conexao.java`:
   - URL: `jdbc:mysql://localhost:3306/hotelDB`
   - Usuário: `root`
   - Senha: (em branco por padrão)

## Compilação powershell ou terminal

Selecione o diretório utilizando o comando:
`cd src`

No diretório `src`, execute:

```
javac -cp "lib\mysql-connector-java-5.1.49.jar" -d . SistemaHotel.java dao\Conexao.java dao\HospedeDAO.java dao\QuartoDAO.java dao\ReservaDAO.java model\Hospede.java model\Quarto.java model\Reserva.java
```

## Execução

```powershell
java -cp "lib\mysql-connector-java-5.1.49.jar;." SistemaHotel
```

## Dependências

- **mysql-connector-java-5.1.49.jar**: Driver JDBC para conexão com MySQL, já adicionado na pasta `lib` do projeto.

## Funcionalidades

- Gerenciar hóspedes (CRUD)
- Gerenciar quartos (CRUD)
- Gerenciar reservas (CRUD)
- Conexão com banco de dados MySQL

## Notas

- Certifique-se de que o MySQL está rodando antes de executar a aplicação
- O driver MySQL foi adicionado ao `src/dao/Conexao.java` com carregamento automático via `Class.forName()`
