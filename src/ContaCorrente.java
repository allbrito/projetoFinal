public class ContaCorrente extends Conta implements Emprestimo{

    private int limitador;

    public ContaCorrente(String nome, String cpf) {
        super(nome, cpf);
    }

    public boolean transferir(Conta destino, int valor) {
        if (destino == null) {
            return false;
        }
        this.sacar(valor);
        destino.depositar(valor);
        return true;
    }

    @Override
    public boolean pedirEmprestimo(double valor) {
        if (valor > getSaldo() + (getSaldo()*0.35) || limitador > 2) {
            return false;
        }
        depositar(valor);
        limitador++;
        return true;
    }
}
