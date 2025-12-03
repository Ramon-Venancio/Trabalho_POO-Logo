/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.Simulacoes;

import Logica.Robo;
import Logica.RoboInteligente;
import Logica.RoboNormal;
import Logica.SimulacaoBase;
import excecao.MovimentoInvalidoException;
import java.util.Random;

/**
 *
 * @author vinan
 */
public class SimulacaoInteligente extends SimulacaoBase {
    private final Robo roboNormal;
    private final Robo roboInteligente;
    
    public SimulacaoInteligente() {
        super();
        roboNormal = new RoboNormal("Verde");
        roboInteligente = new RoboInteligente("Marrom");
    }
    
    @Override
    public void exibirMensagensIniciais() {
        String titulo = String.format("%sROBO LOGO ALTERNADO", " ".repeat(12));
        String mensagem = String.format("%s%n%s%n%s", LINHA_SEPARADORA, titulo, LINHA_SEPARADORA);
        System.out.println(mensagem);
        System.out.printf("Alimento em: (%d, %d)%n", posicaoXAlimento, posicaoYAlimento);
        System.out.println(NOME_ROBO + " " + roboNormal.toString() + " Iniciado");
        System.out.println(NOME_ROBO + " " + roboInteligente.toString() + " Iniciado");
        
        System.out.println(LINHA_SEPARADORA);
        System.out.println("TURNO 0");
        System.out.println(LINHA_SEPARADORA);
        
        desenharArea(roboNormal, roboInteligente, posicaoXAlimento, posicaoYAlimento);
    }

    @Override
    public void executarTurno(int turnoAtual) {
        System.out.println("------------------------------------------");
        System.out.println("TURNO " + turnoAtual);
        System.out.println("------------------------------------------");
        
        try {
            // Movimento aleatório do robô 1
            realizarMovimento(roboNormal);
        } catch (MovimentoInvalidoException e) {
            System.err.println(NOME_ROBO + " " + roboNormal.getCor() + " falhou no movimento!");
            System.out.println("----------------------------");
            System.err.println("Motivo: " + e.getMessage());
        }
        
        try {
            // Movimento aleatório do robô 2
            realizarMovimento(roboInteligente);
        } catch (Exception e) {
            System.err.println(NOME_ROBO + " " + roboInteligente.getCor() + " falhou no movimento!");
            System.out.println("----------------------------");
            System.err.println("Motivo: " + e.getMessage());

        }
        
        desenharArea(roboNormal, roboInteligente, posicaoXAlimento, posicaoYAlimento);
        
        try {
            Thread.sleep(200); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean jogoAcabou() {
        return roboNormal.encontrouAlimento(posicaoXAlimento, posicaoYAlimento) && roboInteligente.encontrouAlimento(posicaoXAlimento, posicaoYAlimento);
    }

    @Override
    public void exibirResultados() {
        System.out.println(LINHA_SEPARADORA);
        System.out.println("!!! SIMULACAO CONCLUIDA !!!");
        System.out.println(LINHA_SEPARADORA);
        
        System.out.println("Robô Normal (" + roboNormal.getCor() + ") encontrou o alimento em: " + roboNormal.getMovimentosTotal() + " movimentos.");
        System.out.println("Robô Inteligente (" + roboInteligente.getCor() + ") encontrou o alimento em: " + roboInteligente.getMovimentosTotal() + " movimentos.");
    }
}
