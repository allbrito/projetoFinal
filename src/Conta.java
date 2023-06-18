import java.util.Random;

public abstract class Conta implements IAutenticacao {

    private int numero;
    private double saldo;
    private int senha;
    private static Random gerador;
    private Cliente cliente;

    public Conta(String nome, String cpf) {
        cliente = new Cliente(nome, cpf);
        gerador = new Random();
        this.senha = gerarSenha();
        this.numero = gerarNumero();
    }

    public boolean sacar(double valor) {
        if (this.saldo < valor) {
            System.out.println("Saldo insuficiente");
            return false;
        }
        this.saldo -= valor;
        return true;
    }

    public boolean depositar(double valor) {
        if (valor < 0) {
            System.out.println("Valor inválido");
            return false;
        }
        this.saldo += valor;
        return true;
    }

    public boolean transferir(Conta destino, double valor) {
        if (destino == null) {
            System.out.println("Conta de destino inválida.");
            return false;
        }
        if (this.sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    @Override
    public boolean autenticar(String login, int senha) {
        if (!this.getCliente().getCpf().equals(login) || this.getSenha() != senha) {
            return false;
        }
        return true;
    }

    @Override
    public boolean alterarSenha(int senha, int novaSenha) {
        if (this.senha != senha) {
            return false;
        }
        this.senha = novaSenha;
        return true;
    }


    @Override
    public String toString() {
        return "Titular: " + getCliente().getNome() +
                ", Tipo: " + getClass().getSimpleName()+
                ", Numero = " + getNumero() +
                ", Saldo = " + getSaldo();
    }

    private int gerarNumero() {
        return gerador.nextInt(9999, 100000);
    }

    private int gerarSenha() {
        return gerador.nextInt(1000, 10000);
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getSenha() {
        return senha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }
}
