package jogodavelha;

 /*
 * IFPB - TSI - POO - PROJETO1
 * Prof Fausto Ayres
 * Murilo Maciel Rodrigues
 * Felipe Oliveira Raimundo
 * classe JogoDaVelha
 */

import java.util.*;

public class JogoDaVelha {
    //atributos
    
    ArrayList<String> celulas = new ArrayList<String>(); //Posições vencedoras: 012, 345, 678, 036, 147, 258, 048 e 246
    String[] simbolos = new String[2];
    LinkedHashMap<Integer, String> historico = new LinkedHashMap<Integer, String>();
    int quantidade_jogadas;
    int esperteza;

    //construtores
    
    // construtor do jogo para 2 jogadores.
    public JogoDaVelha(String simbolo1, String simbolo2){ 
        this.simbolos[0] = simbolo1;
        this.simbolos[1] = simbolo2;
        this.inicializarTabuleiro();
    }

    // construtor do jogo para um jogador e a máquina, onde nível de esperteza da máquina é 1-baixo ou 2-alto)
    public JogoDaVelha(String nomejogador, int nivel){ 
        this.simbolos[0] = nomejogador;
        this.simbolos[1] = "m";
        this.esperteza = nivel;
        this.inicializarTabuleiro();
    }

    //métodos
    
    //método auxiliar para inicializar o tabuleiro
    private void inicializarTabuleiro() {
        for (int i = 0; i < 9; i++) {
            celulas.add("-"); // espaço vazio representa posição livre
        }
        this.quantidade_jogadas = 0;
    }

    // valida a posição e efetiva a jogada para o jogador.
    public void jogaJogador(int numeroJogador, int posicao) throws Exception{
        //verificação de posição inválida
        if (posicao < 0 || posicao > 8) {
            throw new Exception("Posição inválida. Escolha de 0 a 8.");
        }

        //verificando se o espaço escolhido está disponível
        if(Objects.equals(this.celulas.get(posicao), "-")){
            this.celulas.set(posicao, this.simbolos[numeroJogador-1]); //adicionando a jogada do jogador no array
            this.historico.put(posicao, this.simbolos[numeroJogador-1]); //adicionando a jogada do jogador no histórico
            this.quantidade_jogadas += 1;
        }
        else {
            throw new Exception("Posição já ocupada");
        }
    }

    // escolhe uma posição para a máquina.
    public int jogaMaquina(){ 
    	
        //máquina burra: escolhe qualquer posição aleatória para jogar
        if(this.esperteza == 1 | this.esperteza == 2){ //deixei esse "this.esperteza == 2" só pra testar, depois tem que tirar isso
            Random aleatorio = new Random();
            while(true){
                int posicao = aleatorio.nextInt(9);
                
                //verificando se o espaço escolhido está disponível
                if(Objects.equals(this.celulas.get(posicao), "-")){ 
                    this.celulas.set(posicao, this.simbolos[1]); //adicionando a jogada da máquina no array
                    this.historico.put(posicao, this.simbolos[1]); //adicionando a jogada da máquina no histórico
                    this.quantidade_jogadas += 1;
                    return posicao;
                }
            }
        }

        //máquina inteligente: escolhe a posição a partir de um algorítimo
        else {
            String maquina = this.simbolos[1];
            String jogador = this.simbolos[0];

            int[][] combinacoes = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Linhas
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Colunas
                {0, 4, 8}, {2, 4, 6}             // Diagonais
            };

            // Tenta vencer ou bloquear
            for (int[] c : combinacoes) {
                int a = c[0], b = c[1], c_ = c[2];
                if (podeJogar(a, b, c_, maquina)) return fazJogada(posVazio(a, b, c_), maquina);
                if (podeJogar(a, b, c_, jogador)) return fazJogada(posVazio(a, b, c_), maquina);
            }

            // Joga na primeira posição livre
            for (int i = 0; i < 9; i++) {
                if (this.celulas.get(i).equals("-"))
                    return fazJogada(i, maquina);
            }

            return 0; // Nenhuma jogada possível
        }
    }
    
    //método auxiliar para a máquina inteligente
    private boolean podeJogar(int a, int b, int c, String simbolo) {
        return this.celulas.get(a).equals(simbolo) && this.celulas.get(b).equals(simbolo) && this.celulas.get(c).equals("-") ||
               this.celulas.get(a).equals(simbolo) && this.celulas.get(c).equals(simbolo) && this.celulas.get(b).equals("-") ||
               this.celulas.get(b).equals(simbolo) && this.celulas.get(c).equals(simbolo) && this.celulas.get(a).equals("-");
    }
    
    //método auxiliar para a máquina inteligente
    private int posVazio(int a, int b, int c) {
        if (this.celulas.get(a).equals("-")) return a;
        if (this.celulas.get(b).equals("-")) return b;
        return c;
    }

    //método auxiliar para a máquina inteligente fazer a jogada
    private int fazJogada(int pos, String simbolo) {
        this.celulas.set(pos, simbolo);
        this.historico.put(pos, simbolo);
        this.quantidade_jogadas++;
        return pos;
    }

    // retorna true quando um jogador ganha ou não há mais células livres, e retorna false caso contrário.
    public boolean terminou(){
        return !celulas.contains("-") || this.getResultado() != -1;
    }

    // retorna //1(inexistente), 0(empate), 1(vitória do jogador1), 2(vitória do jogador2/máquina)
    public int getResultado() {
        int[][] combinacoes = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
        };

        for (int[] combinacao : combinacoes) {
            int a = combinacao[0];
            int b = combinacao[1];
            int c = combinacao[2];

            String valA = celulas.get(a);
            String valB = celulas.get(b);
            String valC = celulas.get(c);

            if (!valA.equals("-") && valA.equals(valB) && valB.equals(valC)) {
                // Se o vencedor for o "x" retorna 1, se for "o" retorna 2
                if (valA.equals("x")) {
                    return 1;
                } else if (valA.equals("o") | valA.equals("m")) {
                    return 2;
                }
            }
        }
        return -1; // Nenhum vencedor
    }

    // retorna o símbolo do jogador
    public String getSimbolo(int numeroJogador){
        return this.simbolos[numeroJogador-1];
    }

    // retorna um texto com as 9 posições (livres e ocupadas) do tabuleiro dispostas de forma bidimensional
    public String getFoto(){
        //inicializando uma string para representar as posições do jogo
        String foto = "";

        //adicionando as células, com quebra de linha na terceira e sexta célula
        for(int i = 0; i<=8; i++){
            foto += this.celulas.get(i) + "  ";
            if(i == 2 | i == 5){
                foto += "\n";
            }
        }
        return foto;
    }

    // retorna uma lista com as posições ainda não utilizadas pelo jogo
    public ArrayList<Integer> getPosicoesDisponiveis(){
        //Inicializando um Arraylist para as posições disponíveis
        ArrayList<Integer> PosicoesDisponiveis = new ArrayList<Integer>();

        //verificando quais posições estão disponíveis e colocando seus números no arraylist
        for(int i = 0; i < this.celulas.size(); i++){
            if (Objects.equals(this.celulas.get(i), "-")){
                PosicoesDisponiveis.add(i);
            }
        }

        return PosicoesDisponiveis;
    }

    // retorna pares <posição, símbolo> jogados durante o jogo
    public LinkedHashMap<Integer, String> getHistorico(){
        return this.historico;
    }

    //método get para o total de jogadas
    public int getTotalJogadas(){
        return this.quantidade_jogadas;
    }
}
