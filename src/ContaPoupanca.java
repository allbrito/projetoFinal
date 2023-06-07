public class ContaPoupanca extends Conta {

    private final double taxaSaque = 0.05;

    public ContaPoupanca(String nome, String cpf) {
        super(nome, cpf);
    }

    @Override
    public boolean sacar(double valor) {
        valor += valor*taxaSaque;
        return super.sacar(valor);
    }
}
