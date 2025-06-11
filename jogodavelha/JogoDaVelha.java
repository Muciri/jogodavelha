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
    
    private ArrayList<String> celulas = new ArrayList<String>(); //Posições vencedoras: 012, 345, 678, 036, 147, 258, 048 e 246
    private String[] simbolos = new String[2];
    private LinkedHashMap<Integer, String> historico = new LinkedHashMap<Integer, String>();
    private int quantidade_jogadas;
    private int esperteza;

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
        if(this.esperteza == 1){
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
            
            //1. Tentar ganhar
            if (podeJogar(0, 1, 2, maquina)) return fazJogada(posVazio(0, 1, 2), maquina);
            if (podeJogar(3, 4, 5, maquina)) return fazJogada(posVazio(3, 4, 5), maquina);
            if (podeJogar(6, 7, 8, maquina)) return fazJogada(posVazio(6, 7, 8), maquina);
            if (podeJogar(0, 3, 6, maquina)) return fazJogada(posVazio(0, 3, 6), maquina);
            if (podeJogar(1, 4, 7, maquina)) return fazJogada(posVazio(1, 4, 7), maquina);
            if (podeJogar(2, 5, 8, maquina)) return fazJogada(posVazio(2, 5, 8), maquina);
            if (podeJogar(0, 4, 8, maquina)) return fazJogada(posVazio(0, 4, 8), maquina);
            if (podeJogar(2, 4, 6, maquina)) return fazJogada(posVazio(2, 4, 6), maquina);
            

            // 2. Bloquear o jogador
            if (podeJogar(0, 1, 2, jogador)) return fazJogada(posVazio(0, 1, 2), maquina);
            if (podeJogar(3, 4, 5, jogador)) return fazJogada(posVazio(3, 4, 5), maquina);
            if (podeJogar(6, 7, 8, jogador)) return fazJogada(posVazio(6, 7, 8), maquina);
            if (podeJogar(0, 3, 6, jogador)) return fazJogada(posVazio(0, 3, 6), maquina);
            if (podeJogar(1, 4, 7, jogador)) return fazJogada(posVazio(1, 4, 7), maquina);
            if (podeJogar(2, 5, 8, jogador)) return fazJogada(posVazio(2, 5, 8), maquina);
            if (podeJogar(0, 4, 8, jogador)) return fazJogada(posVazio(0, 4, 8), maquina);
            if (podeJogar(2, 4, 6, jogador)) return fazJogada(posVazio(2, 4, 6), maquina);
            

            // 3. Centro
            if (this.celulas.get(4).equals("-")) return fazJogada(4, maquina);

            // 4. Canto
            int[] cantos = {0, 2, 6, 8};
            for (int canto : cantos) {
                if (this.celulas.get(canto).equals("-")) return fazJogada(canto, maquina);
            }

            // 5. Lateral
            int[] laterais = {1, 3, 5, 7};
            for (int lateral : laterais) {
                if (this.celulas.get(lateral).equals("-")) return fazJogada(lateral, maquina);
            }

            // 6. Fallback (último recurso)
            for (int i = 0; i < 9; i++) {
                if (this.celulas.get(i).equals("-")) return fazJogada(i, maquina);
            }

            return 0; // Nenhuma jogada possível
        }
    }

    private boolean podeJogar(int a, int b, int c, String simbolo) {
            return (this.celulas.get(a).equals(simbolo) && this.celulas.get(b).equals(simbolo) && this.celulas.get(c).equals("-")) ||
                   (this.celulas.get(a).equals(simbolo) && this.celulas.get(c).equals(simbolo) && this.celulas.get(b).equals("-")) ||
                   (this.celulas.get(b).equals(simbolo) && this.celulas.get(c).equals(simbolo) && this.celulas.get(a).equals("-"));
        }

    private int posVazio(int a, int b, int c) {
            if (this.celulas.get(a).equals("-")) return a;
            if (this.celulas.get(b).equals("-")) return b;
            return c;
        }

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

    // retorna //-1(inexistente), 0(empate), 1(vitória do jogador1), 2(vitória do jogador2/máquina)
    public int getResultado() {
        if (verificarCombinacao(0, 1, 2)) return getVencedor(0);
        if (verificarCombinacao(3, 4, 5)) return getVencedor(3);
        if (verificarCombinacao(6, 7, 8)) return getVencedor(6);
        if (verificarCombinacao(0, 3, 6)) return getVencedor(0);
        if (verificarCombinacao(1, 4, 7)) return getVencedor(1);
        if (verificarCombinacao(2, 5, 8)) return getVencedor(2);
        if (verificarCombinacao(0, 4, 8)) return getVencedor(0);
        if (verificarCombinacao(2, 4, 6)) return getVencedor(2);
        
        if (!celulas.contains("-")) {
            return 0; // Empate
        }

        return -1; // Nenhum vencedor
    }

    // Função auxiliar para verificar se três posições são iguais e não vazias
    private boolean verificarCombinacao(int a, int b, int c) {
        String valA = celulas.get(a);
        String valB = celulas.get(b);
        String valC = celulas.get(c);
        return !valA.equals("-") && valA.equals(valB) && valB.equals(valC);
    }

    // Função auxiliar para determinar o vencedor baseado no índice
    private int getVencedor(int index) {
        String valor = celulas.get(index);
        if (valor.equals(this.simbolos[0])) {
            return 1;
        } else if (valor.equals(this.simbolos[1])) {
            return 2;
        }
        return -1;
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

    //método get para obter o valor de uma célula
    public String getCelula(int i){
        return this.celulas.get(i);
    }
}