public class ContaPoupanca extends Conta {
    private final double taxaSaque = 0.05;
    private final byte identificador = 2;

    public ContaPoupanca(String nome, String cpf) {
        super(nome, cpf);
    }

    @Override
    public boolean sacar(double valor) {
        double valorComTaxa = valor + (valor * taxaSaque);
        return super.sacar(valorComTaxa);
    }

    public final byte getIdentificador(){
        return identificador;
    }
}

