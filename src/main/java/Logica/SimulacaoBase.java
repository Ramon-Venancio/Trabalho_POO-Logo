/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import excecao.MovimentoInvalidoException;
import java.util.*;


/**
 *
 * @author vinan
 */
public abstract class SimulacaoBase implements Simulacao {
    protected int posicaoXAlimento;
    protected int posicaoYAlimento;
    protected static final int LIMITE_MAX = Robo.TAMANHO_AREA - 1;
    protected static final Scanner sc = new Scanner(System.in);
    protected static final String LINHA_SEPARADORA = "==========================================";
    protected static final String NOME_ROBO = "Robô";
    private static final String ERRO_ENTRADA_INVALIDA = "ERRO: Entrada Inválida. Digite um número inteiro.";


    protected SimulacaoBase() {
        ConfigurarAlimento();
    }
    
    public abstract void exibirMensagensIniciais();
    
    @Override
    public abstract void executarTurno(int turnoAtual);
    @Override
    public abstract boolean jogoAcabou();
    @Override
    public abstract void exibirResultados();
    
    public void ConfigurarAlimento(){
        System.out.println(LINHA_SEPARADORA);
        System.out.println("   CONFIGURACAO ALIMENTO");
        System.out.println(LINHA_SEPARADORA);
        
        posicaoXAlimento = obterEntradaValida("X");
        posicaoYAlimento = obterEntradaValida("Y");
        
    }
    
    // Sobrecarga para o Main_4 (Com Obstáculos)
    public void desenharArea(Robo r1, Robo r2, int aX, int aY, List<Obstaculo> obstaculos) {
        imprimirMapa(r1, r2, aX, aY, obstaculos);
    }

    // Sobrecarga padrão (Sem Obstáculos - para Main 1, 2, 3)
    public void desenharArea(Robo r1, Robo r2, int aX, int aY) {
        imprimirMapa(r1, r2, aX, aY, null);
    }

    // Sobrecarga para 1 Robô (Manual)
    public void desenharArea(Robo r1, int ax, int ay) {
        imprimirMapa(r1, null, ax, ay, null);
    }
    
    // O Método Principal de Desenho (Privado)
    private void imprimirMapa(Robo r1, Robo r2, int aX, int aY, List<Obstaculo> obstaculos) {
        System.out.println("\n--- MAPA (" + (LIMITE_MAX+1) + "x" + (LIMITE_MAX+1) + ") ---");
        for (int y = LIMITE_MAX; y >= 0; y--) { 
            System.out.print(y + " | ");
            for (int x = 0; x < LIMITE_MAX+1; x++) {
                System.out.print(obterSimbolo(x, y, r1, r2, aX, aY, obstaculos) + " ");
            }
            System.out.println();
        }
        desenharRodape();
    }
    
    private String obterSimbolo(int x, int y, Robo r1, Robo r2, int aX, int aY, List<Obstaculo> obstaculos) {
    
        // 1. Tenta pegar o símbolo do robô
        String simbolo = obterSimboloRobos(x, y, r1, r2);

        // SE o retorno NÃO for vazio ("*"), significa que achou um robô. 
        // Retorna imediatamente para desenhá-lo.
        if (!simbolo.equals("*")) {
            return simbolo;
        }

        // 2. Se não tinha robô, checa Obstáculos
        // (Lembre-se de corrigir o método para receber x e y)
        simbolo = obterSimboloObstaculo(x, y, obstaculos);

        if (!simbolo.equals("*")) {
            return simbolo;
        }

        // 3. Se não tinha obstáculo, checa Alimento
        if (x == aX && y == aY) {
            return "A";
        }

        // 4. Se não é nada disso, então é realmente um espaço vazio
        return "*";
    }
    
    private String obterSimboloRobos(int x, int y, Robo r1, Robo r2) {
        boolean isR1 = (r1 != null && r1.isAtivo() && x == r1.getPosicaoX() && y == r1.getPosicaoY());
        boolean isR2 = (r2 != null && r2.isAtivo() && x == r2.getPosicaoX() && y == r2.getPosicaoY());

        if (isR1 && isR2) {
            return "P"; // Colisão
        } else if (isR1) {
            return r1.getCor().substring(0, 1);
        } else if (isR2) {
            return r2.getCor().substring(0, 1);
        }

        return "*"; 
    }

    private String obterSimboloObstaculo(int x, int y, List<Obstaculo> obstaculos) {
        if (obstaculos != null) {
            for (Obstaculo po : obstaculos) {
                // Agora o x e y existem dentro do método
                if (po.getPosicaoX() == x && po.getPosicaoY() == y) {
                     if (po instanceof Bomba) return "B";
                     else return "R";
                }
            }
        }
        
        return "*";
    }
    
    private void desenharRodape() {
        System.out.print("  +");
        // Substituído loop manual por repeat (Java 11+) ou mantido loop simples
        for (int i = 0; i < (LIMITE_MAX+1) * 2; i++) System.out.print("-");
        System.out.println();
        
        System.out.print("    ");
        for (int x = 0; x < LIMITE_MAX+1; x++) {
            System.out.print(x + " ");
        }
        System.out.println("\n---------------------------------");
    }
    
    protected void realizarMovimento(Robo robo) throws MovimentoInvalidoException {
        int direcao = robo.gerarMovimentoRandomico();
        
        robo.mover(direcao);
    }
    
    protected int obterEntradaValida(String eixo) {
        int coord = -1;
        while(coord < 0 || coord >= LIMITE_MAX+1){
            System.out.printf("Digite a coordenada %s (0 a %d): ", eixo, LIMITE_MAX);
            try{
                if (!sc.hasNextInt()) {
                    System.out.println(ERRO_ENTRADA_INVALIDA);
                    sc.next();
                    continue;
                }
                
                coord = sc.nextInt();
                
                if(coord < 0 || coord >= LIMITE_MAX+1){
                    System.out.printf("ERRO: A coordenada deve estar entre 0 e %d.%n", LIMITE_MAX);
                }
            }catch(InputMismatchException e){
                System.out.println(ERRO_ENTRADA_INVALIDA);
                sc.next(); 
            }
        }
        return coord;
    }
}
