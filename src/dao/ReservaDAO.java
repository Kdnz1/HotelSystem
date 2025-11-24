package dao;
import java.sql.*;
import java.util.ArrayList;
import model.*;

public class ReservaDAO {
    public void inserir(Reserva r, int hospedeId, int quartoId) {
        String sql = "INSERT INTO reserva (hospede_id, quarto_id, dias, total) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hospedeId);
            stmt.setInt(2, quartoId);
            stmt.setInt(3, r.getDias());
            stmt.setDouble(4, r.calcularTotal());
            stmt.executeUpdate();
            System.out.println("Reserva salva no banco!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar reserva: " + e.getMessage());
        }
    }

    public ArrayList<Reserva> listar() {
        ArrayList<Reserva> lista = new ArrayList<>();
        String sql = "SELECT r.*, h.nome, q.numero, q.tipo, q.valor_diaria FROM reserva r JOIN hospede h ON r.hospede_id = h.id JOIN quarto q ON r.quarto_id = q.id";
        if (lista.isEmpty()) {
            System.out.println("Nenhuma reserva foi feita.");
            return lista;
        }
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Hospede h = new Hospede(rs.getString("nome"), "", "");
                Quarto q = new Quarto(rs.getInt("numero"), rs.getString("tipo"), rs.getDouble("valor_diaria"));
                Reserva r = new Reserva(h, q, rs.getInt("dias"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar reservas: " + e.getMessage());
        }
        return lista;
    }

    public static int buscarHospedeIdPorCpf(String cpf) {
        int id = -1;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM hospede WHERE cpf = ?")) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) id = rs.getInt("id");
        } catch (Exception e) {
            System.out.println("Erro ao buscar hóspede: " + e.getMessage());
        }
        return id;
    }
}
