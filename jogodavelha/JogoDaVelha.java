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
    HashMap<Integer, String> historico = new HashMap<Integer, String>();
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
            celulas.add(" "); // espaço vazio representa posição livre
        }
        quantidade_jogadas = 0;
    }

    //métodos
    public void jogaJogador(int numeroJogador, int posicao) throws Exception{ // valida a posição e efetiva a jogada para o jogador.
        //verificação de posição inválida
        if (posicao < 0 || posicao > 8) {
            throw new Exception("Posição inválida. Escolha de 0 a 8.");
        }

        //verificando se o espaço escolhido está disponível
        if(Objects.equals(this.celulas.get(posicao), " ")){
            this.celulas.set(posicao, this.simbolos[numeroJogador]); //adicionando a jogada do jogador no array
            this.historico.put(posicao, this.simbolos[numeroJogador]); //adicionando a jogada do jogador no histórico
        }
        else {
            throw new Exception("Posição já ocupada");
        }
    } 
    
    public void jogaMaquina(){ // escolhe uma posição para a máquina.
        //TO INCREMENT

        //máquina burra: escolhe qualquer posição aleatória para jogar
        if(this.esperteza == 1){
            Random aleatorio = new Random();
            while(true){
                int posicao = aleatorio.nextInt(9);

                if(Objects.equals(this.celulas.get(posicao), " ")){ //verificando se o espaço escolhido está disponível
                    this.celulas.set(posicao, this.simbolos[1]); //adicionando a jogada da máquina no array
                    this.historico.put(posicao, this.simbolos[1]); //adicionando a jogada da máquina no histórico
                    break;
                }
            }
        }

        //máquina inteligente: escolhe a posição a partir de um algorítimo
        else {
            return;
            //TO DO
        }
    }
    
    public boolean terminou(){ // retorna true quando um jogador ganha ou não há mais células livres, e retorna false caso contrário.
        return !celulas.contains(" ") || this.getResultado() != -1;
    }

    public int getResultado(){ // retorna //1(inexistente), 0(empate), 1(vitória do jogador1), 2(vitória do jogador2/máquina)
        //TO DO
    }

    public String getSimbolo(int numeroJogador){ // retorna o símbolo do jogador
        return this.simbolos[numeroJogador];
    }

    public String getFoto(){ // retorna um texto com as 9 posições (livres e ocupadas) do tabuleiro dispostas de forma bidimensional
        return celulas.toString();
    }

    public ArrayList<Integer> getPosicoesDisponiveis(){  // retorna uma lista com as posições ainda não utilizadas pelo jogo
        //TO DO
    }

    public LinkedHashMap<Integer, String> getHistorico(){ // retorna pares <posição, símbolo> jogados durante o jogo
        //TO DO
    }
}