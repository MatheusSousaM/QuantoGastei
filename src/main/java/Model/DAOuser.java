package Model;

import Controll.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DAOuser {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios(email, nome, senha)VALUES(?,?,?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();
            System.out.println("Usuário inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro SQL ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Driver JDBC não encontrado: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro inesperado ao inserir usuário: " + e.getMessage(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos na inserção de usuário: " + e.getMessage());
            }
        }
    }

    // MÉTODO PESQUISAR (CORRIGIDO!)
    public Usuario pesquisar(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, email, nome, senha FROM usuarios WHERE email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());
            rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuarioEncontrado = new Usuario(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("nome"),
                    rs.getString("senha")
                );
                return usuarioEncontrado;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro SQL ao pesquisar usuário: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao pesquisar usuário: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("Erro inesperado ao pesquisar usuário: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos na pesquisa de usuário: " + e.getMessage());
            }
        }
    }

    public void Editar(Usuario usuario) {
        String sql = "UPDATE usuarios SET email = ?, nome = ?, senha = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());
            stmt.executeUpdate();
            System.out.println("Usuário editado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro SQL ao editar usuário: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao editar usuário: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao editar usuário: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos na edição de usuário: " + e.getMessage());
            }
        }
    }

    public boolean verificarAgendaVinculada(int userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM agenda WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro SQL ao verificar agenda vinculada: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado ao verificar agenda vinculada: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos na verificação de agenda vinculada: " + e.getMessage());
            }
        }
        return false;
    }

    public void Excluir(Usuario usuario) {
        try {
            if (verificarAgendaVinculada(usuario.getId())) {
                JOptionPane.showMessageDialog(null, "Não é possível excluir o usuário. Existe uma agenda vinculada a ele.", "Erro de Exclusão", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sql = "DELETE FROM usuarios WHERE id=?";
            try (Connection conn = Conexao.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, usuario.getId());

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado com o ID fornecido para exclusão.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                System.err.println("Erro SQL ao excluir usuário: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao excluir usuário do banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e) {
                System.err.println("Erro: Driver JDBC não encontrado ao excluir usuário: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro interno: Driver do banco de dados não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Erro ao verificar agenda vinculada antes da exclusão do usuário: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao verificar vínculos do usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}