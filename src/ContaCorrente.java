public class ContaCorrente extends Conta {
    private int limitador;

    public ContaCorrente(String nome, String cpf) {
        super(nome, cpf);
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

    public boolean pedirEmprestimo(double valor) {
        if (valor > getSaldo() + (getSaldo() * 0.35) || limitador > 2) {
            System.out.println("Limite de empréstimo excedido.");
            return false;
        }
        depositar(valor);
        limitador++;
        return true;
    }
}
