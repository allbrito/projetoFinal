import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnect {
    private static final String URL = "jdbc:mysql://localhost/proj_virtubank";
    private static final String USER = "seu_usuario";
    private static final String PASSWORD = "sua_senha";

    public void salvar(Conta conta) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/proj_virtubank", "root", "rootpassw")) {
            String sql = "INSERT INTO conta (numero, saldo, senha, cliente_nome, cliente_cpf, tipo_conta) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, conta.getNumero());
            statement.setDouble(2, conta.getSaldo());
            statement.setInt(3, conta.getSenha());
            statement.setString(4, conta.getCliente().getNome());
            statement.setString(5, conta.getCliente().getCpf());
            statement.setString(6, conta.getClass().getSimpleName());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Conta buscarPorNumero(int numero) {
        Conta conta = null;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/proj_virtubank", "root", "rootpassw")) {
            String sql = "SELECT * FROM conta WHERE numero = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numero);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String clienteNome = resultSet.getString("cliente_nome");
                String clienteCpf = resultSet.getString("cliente_cpf");
                String tipoConta = resultSet.getString("tipo_conta");

                if (tipoConta.equals("ContaCorrente")) {
                    conta = new ContaCorrente(clienteNome, clienteCpf);
                } else if (tipoConta.equals("ContaPoupanca")) {
                    conta = new ContaPoupanca(clienteNome, clienteCpf);
                }

                conta.setNumero(resultSet.getInt("numero"));
                conta.setSaldo(resultSet.getDouble("saldo"));
                conta.setSenha(resultSet.getInt("senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conta;
    }

    public void atualizar(Conta conta) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/proj_virtubank", "root", "rootpassw")) {
            String sql = "UPDATE conta SET saldo = ?, senha = ? WHERE numero = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, conta.getSaldo());
            statement.setInt(2, conta.getSenha());
            statement.setInt(3, conta.getNumero());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(Conta conta) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/proj_virtubank", "root", "rootpassw")) {
            String sql = "DELETE FROM conta WHERE numero = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, conta.getNumero());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Conta> listarContas() {
        List<Conta> contas = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/proj_virtubank", "root", "rootpassw")) {
            String sql = "SELECT * FROM conta";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String clienteNome = resultSet.getString("cliente_nome");
                String clienteCpf = resultSet.getString("cliente_cpf");
                String tipoConta = resultSet.getString("tipo_conta");

                Conta conta = null;

                if (tipoConta.equals("ContaCorrente")) {
                    conta = new ContaCorrente(clienteNome, clienteCpf);
                } else if (tipoConta.equals("ContaPoupanca")) {
                    conta = new ContaPoupanca(clienteNome, clienteCpf);
                }

                conta.setNumero(resultSet.getInt("numero"));
                conta.setSaldo(resultSet.getDouble("saldo"));
                conta.setSenha(resultSet.getInt("senha"));

                contas.add(conta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contas;
    }
}