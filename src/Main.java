import java.util.Scanner;

public class Main {
    private static DBConnect dbConnect = new DBConnect();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        exibirMenu();
    }

    private static void exibirMenu() {
        int opcao;

        do {
            System.out.println("------ Menu ------");
            System.out.println("1. Criar conta");
            System.out.println("2. Acessar conta existente");
            System.out.println("3. Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    acessarConta();
                    break;
                case 3:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 3);
    }

    private static void criarConta() {
        System.out.print("Digite seu nome: ");
        String nome = sc.nextLine();

        System.out.print("Digite seu CPF: ");
        String cpf = sc.nextLine();

        System.out.println("Escolha o tipo de conta:");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta Poupança");
        System.out.print("Opção: ");
        int opcao = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        Conta conta;

        if (opcao == 1) {
            conta = new ContaCorrente(nome, cpf);
        } else if (opcao == 2) {
            conta = new ContaPoupanca(nome, cpf);
        } else {
            System.out.println("Opção inválida.");
            return;
        }

        int senha = conta.getSenha();
        dbConnect.salvar(conta);
        System.out.println("Conta criada com sucesso. A senha da conta é: " + senha);
    }

    private static void acessarConta() {
        System.out.print("Digite seu CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Digite a senha: ");
        int senha = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        Conta conta = dbConnect.buscarPorCpf(cpf);

        if (conta != null && conta.autenticar(cpf, senha)) {
            exibirMenuConta(conta);
        } else {
            System.out.println("CPF ou senha inválidos.");
        }
    }

    private static void exibirMenuConta(Conta conta) {
        int opcao;

        do {
            System.out.println("\n------ Menu da Conta ------");
            System.out.println("Titular: " + conta.getCliente().getNome());
            System.out.println("Tipo: " + conta.getClass().getSimpleName());
            System.out.println("Número: " + conta.getNumero());
            System.out.println("Saldo: " + conta.getSaldo());
            System.out.println("-----------------------------");
            System.out.println("1. Depositar");
            System.out.println("2. Sacar");
            System.out.println("3. Trocar senha");
            System.out.println("4. Ver dados da conta");
            System.out.println("5. Voltar ao menu principal");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    depositar(conta);
                    break;
                case 2:
                    sacar(conta);
                    break;
                case 3:
                    trocarSenha(conta);
                    break;
                case 4:
                    exibirDadosConta(conta);
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 5);
    }

    private static void depositar(Conta conta) {
        System.out.print("Digite o valor a ser depositado: ");
        double valor = sc.nextDouble();
        sc.nextLine(); // Limpar o buffer do scanner

        conta.depositar(valor);
        System.out.println("Depósito realizado, Novo saldo: " + conta.getSaldo());
    }

    private static void sacar(Conta conta) {
        System.out.print("Digite o valor a ser sacado: ");
        double valor = sc.nextDouble();
        sc.nextLine(); // Limpar o buffer do scanner

        if (conta.sacar(valor)) {
            System.out.println("Saque realizado. Novo saldo: " + conta.getSaldo());
        } else {
            System.out.println("Tente um valor menor");
        }
    }

    private static void trocarSenha(Conta conta) {
        System.out.print("Digite a senha atual: ");
        int senhaAtual = sc.nextInt();
        sc.nextLine(); // Limpar o buffer do scanner

        if (conta.autenticar(conta.getCliente().getCpf(), senhaAtual)) {
            System.out.print("Digite sua nova senha: ");
            int novaSenha = sc.nextInt();
            sc.nextLine();

            conta.setSenha(novaSenha);
            dbConnect.atualizar(conta);
            System.out.println("Senha alterada com sucesso.");
        } else {
            System.out.println("Senha atual inválida.");
        }
    }

    private static void exibirDadosConta(Conta conta) {
        System.out.println("\n------ Dados da Conta ------");
        System.out.println("Titular: " + conta.getCliente().getNome());
        System.out.println("Tipo: " + conta.getClass().getSimpleName());
        System.out.println("Número: " + conta.getNumero());
        System.out.println("Saldo: " + conta.getSaldo());
        System.out.println("----------------------------");
    }
}
