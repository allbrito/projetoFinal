import java.util.Random;

public abstract class Conta implements IAutenticacao{
	
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
            System.out.println("nao tem");
            return false;
        }
        this.saldo -= valor;
        return true;
    }

    public boolean depositar(double valor) {
        if (valor < 0) {
            System.out.println("nao pode");
            return false;
        }
        this.saldo += valor;
        return true;
    }

    @Override
    public boolean autenticar(String login, int senha) {
        if (this.getCliente().getCpf() != login || this.getSenha() != senha) {
            return false;
        }
        return true;
    }

    @Override
    public boolean alterarSenha(int senha, int novaSenha) {
        if (this.senha != senha){
            return false;
        }
        this.senha = senha;
        return true;
    }

    @Override
    public String toString() {
        return "Titular: " + getCliente().getNome() +
                ", Tipo: " + getClass().getName() +
                ", Numero = " + getNumero() +
                ", Saldo = " + getSaldo();
    }

    private int gerarNumero() {
        return gerador.nextInt(9999, 100000);
    }

    private int gerarSenha() {
        return gerador.nextInt(999, 10000);
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
}