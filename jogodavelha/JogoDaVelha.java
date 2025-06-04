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
    public JogoDaVelha(String simbolo1, String simbolo2){ // construtor do jogo para 2 jogadores.
        this.simbolos[0] = simbolo1;
        this.simbolos[1] = simbolo2;
        this.inicializarTabuleiro();
    }

    public JogoDaVelha(String nomejogador, int nivel){ // construtor do jogo para um jogador e a máquina, onde nível de esperteza da máquina é 1-baixo ou 2-alto)
        this.simbolos[0] = nomejogador;
        this.simbolos[1] = "m";
        this.esperteza = nivel;
        this.inicializarTabuleiro();
    }

    //método auxiliar para inicializar o tabuleiro
    private void inicializarTabuleiro() {
        for (int i = 0; i < 9; i++) {
            celulas.add("-"); // espaço vazio representa posição livre
        }
        this.quantidade_jogadas = 0;
    }

    //métodos

    // valida a posição e efetiva a jogada para o jogador.
    public void jogaJogador(int numeroJogador, int posicao) throws Exception{
        //verificação de posição inválida
        if (posicao < 0 || posicao > 8) {
            throw new Exception("Posição inválida. Escolha de 0 a 8.");
        }

        //verificando se o espaço escolhido está disponível
        if(Objects.equals(this.celulas.get(posicao), " ")){
            this.celulas.set(posicao, this.simbolos[numeroJogador]); //adicionando a jogada do jogador no array
            this.historico.put(posicao, this.simbolos[numeroJogador]); //adicionando a jogada do jogador no histórico
            this.quantidade_jogadas += 1;
        }
        else {
            throw new Exception("Posição já ocupada");
        }
    }

    // escolhe uma posição para a máquina.
    public int jogaMaquina(){ // escolhe uma posição para a máquina.
        //TO INCREMENT

        //máquina burra: escolhe qualquer posição aleatória para jogar
        if(this.esperteza == 1 | this.esperteza == 2){ //deixei esse "this.esperteza == 2" só pra testar, depois tem que tirar isso
            Random aleatorio = new Random();
            while(true){
                int posicao = aleatorio.nextInt(9);

                if(Objects.equals(this.celulas.get(posicao), "-")){ //verificando se o espaço escolhido está disponível
                    this.celulas.set(posicao, this.simbolos[1]); //adicionando a jogada da máquina no array
                    this.historico.put(posicao, this.simbolos[1]); //adicionando a jogada da máquina no histórico
                    this.quantidade_jogadas += 1;
                    return posicao;
                }
            }
        }

        //máquina inteligente: escolhe a posição a partir de um algorítimo
        else {
            //TO DO
            return 0;
        }
    }

    // retorna true quando um jogador ganha ou não há mais células livres, e retorna false caso contrário.
    public boolean terminou(){
        return !celulas.contains("-") || this.getResultado() != -1;
    }

    // retorna //1(inexistente), 0(empate), 1(vitória do jogador1), 2(vitória do jogador2/máquina)
    public int getResultado(){
        //TO DO
        return -1; //o retorno está como -1 apenas para teste, corrigir isto depois
    }

    // retorna o símbolo do jogador
    public String getSimbolo(int numeroJogador){
        return this.simbolos[numeroJogador];
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
        for(int i = 0; i <= this.celulas.size(); i++){
            if (Objects.equals(this.celulas.get(i), " ")){
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