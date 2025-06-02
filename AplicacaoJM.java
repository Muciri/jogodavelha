
/*
 * IFPB - TSI - POO - PROJETO1
 * Prof Fausto Ayres
 * Aplicação para testar a classe JogoDaVelha
 * 
 */
import javax.swing.JOptionPane;
import jogodavelha.JogoDaVelha;

public class AplicacaoJM {
	private JogoDaVelha jogodavelha;

	public AplicacaoJM() {
		jogodavelha = new JogoDaVelha("x", 2); // jogador=x, nivel da maquina=1(baixo) ou 2(alto)
	
		int ordem=0;
		do {
			String quemInicia = JOptionPane.showInputDialog("quem inicia o jogo (1=jogador ou 2=maquina)?");
			try {
				ordem = Integer.parseInt(quemInicia);
			} 
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Entrada invalida! Digite 1 ou 2.", "Jogo da Velha",JOptionPane.DEFAULT_OPTION);
			}
		} 
		while(ordem != 1 && ordem != 2);

		//***********************
		// Loop principal do jogo
		//***********************
		do {
			if (ordem == 1) {
				jogadaDoJogador(1);
				ordem++;

			} else {
				jogadaDaMaquina();
				ordem--;
			}
		} while (!jogodavelha.terminou());
		
		String texto ;
		if (jogodavelha.getResultado() == -1) 
			texto = "Inexistente";
		else if (jogodavelha.getResultado() == 0) 
			texto = "Empate";
		else
			texto = "Vitoria de: "+jogodavelha.getSimbolo(jogodavelha.getResultado());
		
		texto = texto + "\n" + jogodavelha.getFoto() + "\nhistorico:" + jogodavelha.getHistorico();
		JOptionPane.showMessageDialog(null, texto, "Resultado final", JOptionPane.DEFAULT_OPTION);
	}

	public void jogadaDoJogador(int numero) {	//numero do jogador: 1 ou 2
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

	
	public void jogadaDaMaquina() {
		int posicao = jogodavelha.jogaMaquina();
		String texto = "Jogada da maquina foi " + posicao + "\n" + jogodavelha.getFoto() + "\ntotal de jogadas="
				+ jogodavelha.getTotalJogadas();
		JOptionPane.showMessageDialog(null, texto, "Jogo da Velha", JOptionPane.DEFAULT_OPTION);
	}

	public static void main(String[] args) {
		AplicacaoJM app = new AplicacaoJM();
	}
}
