package Model;

import Controll.Agenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date; // Para java.util.Date
import java.sql.Time; // Para java.sql.Time (já estava presente)


public class DAOagenda {

    public void inserir(Agenda agenda) {
        String sql = "INSERT INTO agenda (user_id, gastos, data, hora, tipo_gasto) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, agenda.getUser_id());
            stmt.setDouble(2, agenda.getGastos());

            java.sql.Date sqlDate = new java.sql.Date(agenda.getData().getTime());
            stmt.setDate(3, sqlDate);
            stmt.setTime(4, agenda.getHora());
            stmt.setString(5, agenda.getTipo_gasto());
            stmt.executeUpdate();

            System.out.println("Gasto da agenda inserido com sucesso para user_id: " + agenda.getUser_id());

        } catch (SQLException e) {
            System.err.println("Erro SQL ao inserir gasto na agenda: " + e.getMessage());
            e.printStackTrace();
            // Você pode adicionar um JOptionPane.showMessageDialog aqui se quiser
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao inserir gasto na agenda: " + e.getMessage());
            e.printStackTrace();
            // Você pode adicionar um JOptionPane.showMessageDialog aqui se quiser
        } catch (Exception e) {
            System.err.println("Erro inesperado ao inserir gasto na agenda: " + e.getMessage());
            e.printStackTrace();
            // Você pode adicionar um JOptionPane.showMessageDialog aqui se quiser
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos na inserção de agenda: " + e.getMessage());
            }
        }
    }

    // MÉTODO PESQUISAR POR DATA
    public ArrayList<Agenda> pesquisarPorData(int userId, Date dataAgenda) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, user_id, gastos, data, hora, tipo_gasto FROM agenda WHERE user_id = ? AND data = ? ORDER BY hora ASC";
        ArrayList<Agenda> agendasEncontradas = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            java.sql.Date sqlDate = new java.sql.Date(dataAgenda.getTime());
            stmt.setDate(2, sqlDate);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agenda agendaEncontrada = new Agenda(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("gastos"),
                        new Date(rs.getDate("data").getTime()),
                        rs.getTime("hora"),
                        rs.getString("tipo_gasto")
                    );
                    agendasEncontradas.add(agendaEncontrada);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro SQL ao pesquisar agenda por data: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao pesquisar agenda por data: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return agendasEncontradas;
    }

    // MÉTODO PESQUISAR POR TIPO DE GASTO
    public ArrayList<Agenda> pesquisarPorTipoGasto(int userId, String tipoGasto) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, user_id, gastos, data, hora, tipo_gasto FROM agenda WHERE user_id = ? AND tipo_gasto = ? ORDER BY data ASC, hora ASC";
        ArrayList<Agenda> agendasEncontradas = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, tipoGasto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agenda agendaEncontrada = new Agenda(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("gastos"),
                        new Date(rs.getDate("data").getTime()),
                        rs.getTime("hora"),
                        rs.getString("tipo_gasto")
                    );
                    agendasEncontradas.add(agendaEncontrada);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro SQL ao pesquisar agenda por tipo de gasto: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao pesquisar agenda por tipo de gasto: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return agendasEncontradas;
    }

    // MÉTODO PESQUISAR TODAS AS AGENDAS DE UM USUÁRIO (PARA USO GERAL)
  
    public ArrayList<Agenda> pesquisarAgendasPorUsuario(int userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, user_id, gastos, data, hora, tipo_gasto FROM agenda WHERE user_id = ? ORDER BY data ASC, hora ASC";
        ArrayList<Agenda> agendasEncontradas = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Agenda agenda = new Agenda(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("gastos"),
                        new Date(rs.getDate("data").getTime()),
                        rs.getTime("hora"),
                        rs.getString("tipo_gasto")
                    );
                    agendasEncontradas.add(agenda);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro SQL ao pesquisar todas as agendas: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao pesquisar todas as agendas: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return agendasEncontradas;
    }

    public void Editar(Agenda agenda) {
        String sql = "UPDATE agenda SET gastos = ?, data = ?, hora = ?, tipo_gasto = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, agenda.getGastos());
            java.sql.Date sqlDate = new java.sql.Date(agenda.getData().getTime());
            stmt.setDate(2, sqlDate);
            stmt.setTime(3, agenda.getHora());
            stmt.setString(4, agenda.getTipo_gasto());
            stmt.setInt(5, agenda.getId()); // Usa o ID da agenda para editar
            stmt.executeUpdate();
            System.out.println("Gasto da agenda editado com sucesso (com data e hora)!");
        } catch (SQLException e) {
            System.err.println("Erro SQL ao editar agenda: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao editar agenda: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao editar agenda: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos na edição de agenda: " + e.getMessage());
            }
        }
    }

    public void Excluir(Agenda agenda) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM agenda WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, agenda.getId()); // Usa o ID da agenda para excluir
            stmt.executeUpdate();
            System.out.println("Gasto da agenda excluído com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro SQL ao excluir gasto da agenda: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao excluir gasto da agenda: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}