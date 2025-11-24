package dao;
import java.sql.*;
import java.util.ArrayList;
import model.Quarto;

public class QuartoDAO {
    public ArrayList<Quarto> listar() {
        ArrayList<Quarto> lista = new ArrayList<>();
        String sql = "SELECT * FROM quarto";
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Quarto q = new Quarto(
                    rs.getInt("numero"),
                    rs.getString("tipo"),
                    rs.getDouble("valor_diaria")
                );
                lista.add(q);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar quartos: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Quarto> listarDisponiveis() {
        ArrayList<Quarto> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT q.* FROM quarto q WHERE q.numero NOT IN (SELECT DISTINCT quarto_id FROM reserva)";
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Quarto q = new Quarto(
                    rs.getInt("numero"),
                    rs.getString("tipo"),
                    rs.getDouble("valor_diaria")
                );
                lista.add(q);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar quartos disponíveis: " + e.getMessage());
        }
        return lista;
    }

    public void inserir(Quarto q) {
        String sql = "INSERT INTO quarto (numero, tipo, valor_diaria) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, q.getNumero());
            stmt.setString(2, q.getTipo());
            stmt.setDouble(3, q.getValorDiaria());
            stmt.executeUpdate();
            System.out.println("✅ Quarto " + q.getNumero() + " adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao adicionar quarto: " + e.getMessage());
        }
    }

    public void excluir(int numero) {
        String sql = "DELETE FROM quarto WHERE numero = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Quarto " + numero + " excluído com sucesso!");
            } else {
                System.out.println("❌ Quarto não encontrado!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir quarto: " + e.getMessage());
        }
    }

    public static int buscarIdPorNumero(int numero) {
        int id = -1;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM quarto WHERE numero = ?")) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Erro ao buscar ID do quarto: " + e.getMessage());
        }
        return id;
    }
}
