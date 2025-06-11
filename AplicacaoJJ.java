
/*
 * IFPB - TSI - POO - PROJETO1
 * Prof Fausto Ayres
 * Aplicaçãoo para testar a classe JogoDaVelha
 *
 */
import javax.swing.JOptionPane;
import jogodavelha.JogoDaVelha;

public class AplicacaoJJ {
	private JogoDaVelha jogodavelha;

	public AplicacaoJJ() {
		jogodavelha = new JogoDaVelha("x", "x"); // jogador1=x, jogador2=o

		int ordem = 0;
		do {
			String quemInicia = JOptionPane.showInputDialog("quem inicia o jogo (1=jogador1 ou 2=jogador2)?");
			try {
				ordem = Integer.parseInt(quemInicia);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Entrada invalida! Digite 1 ou 2.", "Jogo da Velha",
						JOptionPane.DEFAULT_OPTION);
			}
		} while (ordem != 1 && ordem != 2);

		// ***********************
		// Loop principal do jogo
		// ***********************
		do {
			if (ordem == 1) {
				jogadaDoJogador(1);
				ordem++;
			} 
			else {
				jogadaDoJogador(2);
				ordem--;
			}

		} while (!jogodavelha.terminou());

		String texto;
		if (jogodavelha.getResultado() == -1)
			texto = "Inexistente";
		else if (jogodavelha.getResultado() == 0)
			texto = "Empate";
		else
			texto = "Vitoria de: " + jogodavelha.getSimbolo(jogodavelha.getResultado());

		texto = texto + "\n" + jogodavelha.getFoto() + "\nhistorico:"
				+ jogodavelha.getHistorico();
		JOptionPane.showMessageDialog(null, texto, "Resultado final", JOptionPane.DEFAULT_OPTION);
	}

	public void jogadaDoJogador(int numero) { // numero do jogador: 1 ou 2
		try {
			String texto = "Jogada do jogador: " + jogodavelha.getSimbolo(numero) + "\n" + jogodavelha.getFoto()
					+ "\ndigite uma posicao valida:" + jogodavelha.getPosicoesDisponiveis()
					+ "\ndigite -1 para terminar o jogo";
			int posicao = Integer.parseInt(JOptionPane.showInputDialog(texto));
			if (posicao == -1) {
				JOptionPane.showMessageDialog(null, "jogo terminado pelo usuario", "Jogo da Velha",
						JOptionPane.DEFAULT_OPTION);
				System.exit(0);
			}

			jogodavelha.jogaJogador(numero, posicao);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "---> formato numerico invalido", "Jogo da Velha",
					JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "--->" + e.getMessage(), "Jogo da Velha", JOptionPane.DEFAULT_OPTION);
		}
	}

	public static void main(String[] args) {
		AplicacaoJJ app = new AplicacaoJJ();
	}
}
