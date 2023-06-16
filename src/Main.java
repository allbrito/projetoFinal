public class Main {
    public static void main(String[] args) {

        DBConnect dbConnect = new DBConnect();


        ContaPoupanca conta = new ContaPoupanca("allan", "9876543210");


        conta.depositar(80);
        conta.sacar(200);


        dbConnect.salvar(conta);


        Conta contaRecuperada = dbConnect.buscarPorNumero(conta.getNumero());


        if (contaRecuperada != null) {
            System.out.println("Conta encontrada: " + contaRecuperada);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }
}
