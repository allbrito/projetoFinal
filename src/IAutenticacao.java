public interface IAutenticacao {
    public boolean autenticar(String login, int senha);
    public boolean alterarSenha(int senha, int novaSenha);
}
