package jogodavelha;

/*
 * IFPB - TSI - POO - PROJETO1 - classe TelaJogo
 * Prof: Fausto Ayres - Alunos: Murilo Maciel Rodrigues e Felipe Oliveira Raimundo
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TelaJogo {
	private JFrame frmJogoDaVelha;
	private JLabel[][] grid;
	private JogoDaVelha jogo;
	private boolean modoContraMaquina = false;
	private boolean vezJogador1 = true;
	private JTextArea areaHistorico;
	private JLabel lblJogadas;
	private JLabel lblResultado;
	private JLabel lblPosicoesDisponiveis;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TelaJogo window = new TelaJogo();
				window.frmJogoDaVelha.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public TelaJogo() {
		initialize();
	}

	private void initialize() {
		frmJogoDaVelha = new JFrame();
		frmJogoDaVelha.setTitle("Jogo da Velha");
		frmJogoDaVelha.setBounds(750, 100, 454, 406);
		frmJogoDaVelha.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJogoDaVelha.getContentPane().setLayout(null);

		String[] simbolo = {"X", "O"};
		JComboBox<String> comboBox_1 = new JComboBox<>(simbolo);
		comboBox_1.setBounds(169, 25, 44, 23);
		frmJogoDaVelha.getContentPane().add(comboBox_1);
		
		JComboBox<String> comboBox_2 = new JComboBox<>(simbolo);
		comboBox_2.setBounds(169, 49, 44, 22);
		frmJogoDaVelha.getContentPane().add(comboBox_2);

		String[] opcao = {"1", "2"};
		JComboBox<String> comboBox = new JComboBox<>(opcao);		
		comboBox.setBounds(374, 0, 44, 22);
		frmJogoDaVelha.getContentPane().add(comboBox);
		
		JRadioButton rdbtnJogador1 = new JRadioButton("Jogador x Jogador");
		rdbtnJogador1.setBounds(91, 0, 144, 23);
		frmJogoDaVelha.getContentPane().add(rdbtnJogador1);
		
		JRadioButton rdbtnJogador2 = new JRadioButton("Jogador x Máquina");
		rdbtnJogador2.setBounds(237, 0, 133, 23);
		frmJogoDaVelha.getContentPane().add(rdbtnJogador2);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnJogador1);
		group.add(rdbtnJogador2);
		rdbtnJogador1.setSelected(true);

		JButton btniniciar = new JButton("Iniciar");
		btniniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String simbolo1 = (String) comboBox_1.getSelectedItem();
				String simbolo2 = (String) comboBox_2.getSelectedItem();
				int nivel = Integer.parseInt((String) comboBox.getSelectedItem());

				modoContraMaquina = rdbtnJogador2.isSelected();
				if (modoContraMaquina) {
					jogo = new JogoDaVelha(simbolo1, nivel);
				} else {
					jogo = new JogoDaVelha(simbolo1, simbolo2);
				}

				vezJogador1 = true;
				areaHistorico.setText("");
				atualizarTabuleiro();
				lblJogadas.setText("Jogadas: 0");
				lblResultado.setText("Resultado:");
				lblPosicoesDisponiveis.setText("posições disponíveis" + jogo.getPosicoesDisponiveis());
			}
		});
		btniniciar.setBounds(0, 0, 89, 23);
		frmJogoDaVelha.getContentPane().add(btniniciar);

		JLabel lbl1 = new JLabel("Simbolo J1");
		lbl1.setBounds(91, 25, 77, 14);
		frmJogoDaVelha.getContentPane().add(lbl1);
		
		JLabel lbl2 = new JLabel("Simbolo J2");
		lbl2.setBounds(91, 50, 82, 14);
		frmJogoDaVelha.getContentPane().add(lbl2);

		JLabel lblHistorico = new JLabel("Histórico:");
		lblHistorico.setBounds(304, 99, 100, 14);
		frmJogoDaVelha.getContentPane().add(lblHistorico);
		areaHistorico = new JTextArea();
		areaHistorico.setEditable(false);
		JScrollPane scrollHistorico = new JScrollPane(areaHistorico);
		scrollHistorico.setBounds(290, 120, 130, 130);
		frmJogoDaVelha.getContentPane().add(scrollHistorico);

		lblJogadas = new JLabel("Jogadas: ");
		lblJogadas.setBounds(46, 273, 100, 14);
		frmJogoDaVelha.getContentPane().add(lblJogadas);
		
		lblResultado = new JLabel("Resultado:");
		lblResultado.setBounds(43, 298, 200, 14);
		frmJogoDaVelha.getContentPane().add(lblResultado);

		lblPosicoesDisponiveis = new JLabel("Posições disponiveis:");
		lblPosicoesDisponiveis.setBounds(43, 325, 250, 14);
		frmJogoDaVelha.getContentPane().add(lblPosicoesDisponiveis);

		// Painel da grade
		GridLayout layout = new GridLayout(3, 3, 1, 1);
		JPanel painelGrid = new JPanel(layout);
		painelGrid.setBounds(143, 99, 130, 130);
		frmJogoDaVelha.getContentPane().add(painelGrid);

		grid = new JLabel[3][3];
		for (int i = 0; i < 9; i++) {
			int x = i % 3;
			int y = i / 3;
			JLabel cell = new JLabel(String.valueOf(i), SwingConstants.CENTER);
			cell.setOpaque(true);
			cell.setBackground(Color.GREEN);
			cell.setBorder(new LineBorder(Color.BLACK, 1, true));
			int pos = i;

			cell.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (jogo != null && !jogo.terminou()) {
						jogar(pos);
					}
				}
			});

			grid[x][y] = cell;
			painelGrid.add(cell);
		}
	}

	private void jogar(int posicao) {
		try {
			if (modoContraMaquina) {
				jogo.jogaJogador(1, posicao);
				atualizarTabuleiro();
				if (!jogo.terminou()) {
					int posMaquina = jogo.jogaMaquina();
					atualizarTabuleiro();
				}
			} else {
				jogo.jogaJogador(vezJogador1 ? 1 : 2, posicao);
				vezJogador1 = !vezJogador1;
				atualizarTabuleiro();
			}

			if (jogo.terminou()) {
				int r = jogo.getResultado();
				String msg = switch (r) {
					case 0 -> "Empate!";
					case 1 -> "Jogador " + jogo.getSimbolo(1) + " venceu!";
					case 2 -> modoContraMaquina ? "Máquina venceu!" : "Jogador " + jogo.getSimbolo(2) + " venceu!";
					default -> "Erro ao verificar resultado.";
				};
				lblResultado.setText("Resultado: " + msg);
			}

			lblJogadas.setText("Jogadas: " + jogo.getTotalJogadas());
			lblPosicoesDisponiveis.setText("posições disponíveis" + jogo.getPosicoesDisponiveis());
			atualizarHistorico();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frmJogoDaVelha, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void atualizarTabuleiro() {
		if (jogo == null) return;
		for (int i = 0; i < 9; i++) {
			int x = i % 3;
			int y = i / 3;
			grid[x][y].setText(jogo.getCelula(i));
		}
	}

	private void atualizarHistorico() {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<Integer, String> entrada : jogo.getHistorico().entrySet()) {
			builder.append("Posição ").append(entrada.getKey()).append(": ").append(entrada.getValue()).append("\n");
		}
		areaHistorico.setText(builder.toString());
	}
}