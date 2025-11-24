package dao;
import java.sql.*;
import java.util.ArrayList;
import model.Hospede;

public class HospedeDAO {
    public void inserir(Hospede h) {
        String sql = "INSERT INTO hospede (nome, cpf, telefone) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, h.getNome());
            stmt.setString(2, h.getCpf());
            stmt.setString(3, h.getTelefone());
            stmt.executeUpdate();
            System.out.println("Hóspede cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar hóspede: " + e.getMessage());
        }
    }

    public ArrayList<Hospede> listar() {
        ArrayList<Hospede> lista = new ArrayList<>();
        String sql = "SELECT * FROM hospede";
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Hospede h = new Hospede(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone")
                );
                lista.add(h);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar hóspedes: " + e.getMessage());
        }
        return lista;
    }
}
