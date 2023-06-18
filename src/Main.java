import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static DBConnect dbConnect = new DBConnect();
    private static Scanner sc = new Scanner(System.in);

    public static List<ContaCorrente> listCorrente = new ArrayList<>();
    public static List<ContaPoupanca> listPoupanca = new ArrayList<>();

    public static void main(String[] args) {
        exibirMenu();
    }

    private static void exibirMenu(){
        int opcao;

        do {
            System.out.println("------ Menu da Conta ------");
            System.out.println("1. Criar Conta");
            System.out.println("2. Acessar Conta");
            System.out.println("3. Sair");
            System.out.println("Opcao");
            opcao = sc.nextInt();
            sc.nextLine();

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

        if (opcao == 1) {
            var contaCorrente = new ContaCorrente(nome, cpf);
            listCorrente.add(contaCorrente);
            System.out.println("Sua senha: " + contaCorrente.getSenha());
            System.out.println("Seu número da conta: " + contaCorrente.getNumero());
            dbConnect.salvar(contaCorrente);
        } else if (opcao == 2) {
            var contaPoupanca = new ContaPoupanca(nome, cpf);
            listPoupanca.add(contaPoupanca);
            System.out.println("Sua senha: " + contaPoupanca.getSenha());
            System.out.println("Seu número da conta: " + contaPoupanca.getNumero());
            dbConnect.salvar(contaPoupanca);
        } else {
            System.out.println("Opcao Inválida");
        }
    }

    private static void acessarConta() {
        System.out.print("Digite seu CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Digite a senha: ");
        int senha = sc.nextInt();

        sc.nextLine(); // Limpar o buffer do scanner

        Conta conta = dbConnect.buscarPorCpf(cpf);

        for (ContaCorrente cc: listCorrente) {
            if (cc.autenticar(cpf, senha) && conta != null) {
                exibirMenuCorrente(cc);
                return;
            }
        }

        for (ContaPoupanca cp: listPoupanca) {
            if (cp.autenticar(cpf, senha) && conta != null) {
                exibirMenuPoupanca(cp);
                return;
            }
        }
        System.out.println("Cpf ou Senha inválidos");
    }

    private static void exibirMenuCorrente(ContaCorrente contaCorrente) {
        int opcao;
        do {
            System.out.println("------ Menu da Conta ------");
            System.out.println("1. Depositar");
            System.out.println("2. Sacar");
            System.out.println("3. Transferir");
            System.out.println("4. Pedir Empréstimo");
            System.out.println("5. Trocar Senha");
            System.out.println("6. Ver Dados da Conta");
            System.out.println("7. Voltar ao menu principal");
            System.out.print("Opção: ");
            opcao = sc.nextInt();

            sc.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    depositar(contaCorrente);
                    break;
                case 2:
                    sacar(contaCorrente);
                    break;
                case 3:
                    transferir(contaCorrente);
                case 4:
                    emprestimo(contaCorrente);
                case 5:
                    trocarSenha(contaCorrente);
                    break;
                case 6:
                    contaCorrente.toString();
                    break;
                case 7:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 7);
    }

    private static void exibirMenuPoupanca(ContaPoupanca contaPoupanca) {
        int opcao;
        do {
            System.out.println("------ Menu da Conta ------");
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
                    depositar(contaPoupanca);
                    break;
                case 2:
                    sacar(contaPoupanca);
                    break;
                case 3:
                    trocarSenha(contaPoupanca);
                    break;
                case 4:
                    contaPoupanca.toString();
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

    private static void transferir(ContaCorrente contaCorrente) {
        System.out.println("Digite o valor a transferir: ");
        int valor = sc.nextInt();

        System.out.println("Digite o número da conta destino: ");
        int numero = sc.nextInt();

        for (ContaCorrente cc: listCorrente) {
            if (cc.getNumero() == numero) {
                if (contaCorrente.transferir(cc, valor)) {
                    System.out.println("Ocorreu tudo bem");
                }
            } else {
                System.out.println("Não foi possível fazer a transferência");
            }
        }
    }

    private static void emprestimo(ContaCorrente contaCorrente) {
        System.out.print("Insira o valor do empréstimo: ");
        double valor = sc.nextDouble();
        contaCorrente.pedirEmprestimo(valor);
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
}
