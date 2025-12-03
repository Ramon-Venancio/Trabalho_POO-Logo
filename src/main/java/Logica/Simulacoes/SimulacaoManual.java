/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.Simulacoes;

import Logica.Robo;
import Logica.RoboNormal;
import Logica.SimulacaoBase;
import excecao.MovimentoInvalidoException;

/**
 *
 * @author vinan
 */
public class SimulacaoManual extends SimulacaoBase {
    private final Robo meuRobo;
    
    public SimulacaoManual() {
        super();
        meuRobo = new RoboNormal("Verde");
    }
    
    @Override
    public void exibirMensagensIniciais() {
        String titulo = String.format("%sROBO LOGO MANUAL", " ".repeat(12));
        String mensagem = String.format("%s%n%s%n%s", LINHA_SEPARADORA, titulo, LINHA_SEPARADORA);
        System.out.println(mensagem);
        System.out.printf("Alimento em: (%d, %d)%n", posicaoXAlimento, posicaoYAlimento);
        System.out.println(NOME_ROBO + " " + meuRobo.toString() + " Iniciado");
        
        System.out.println(LINHA_SEPARADORA);
        System.out.println("TURNO 0");
        System.out.println(LINHA_SEPARADORA);
        
        desenharArea(meuRobo, posicaoXAlimento, posicaoYAlimento);
    }

    @Override
    public void executarTurno(int turnoAtual) {
        int direcao = 0;
        System.out.println("------------------------------------------");
        System.out.println("TURNO " + turnoAtual);
        System.out.println("------------------------------------------");        
        System.out.println("Mova o Robo:");
        System.out.println("  1: up | 2: down | 3: right | 4: left");
        System.out.print("Sua escolha: ");

        try {
            if (sc.hasNextInt()) {
                direcao = sc.nextInt();

                meuRobo.mover(direcao); 
            } else {
                // Captura e trata a InputMismatchException (se digitar letra, por exemplo)
                System.err.println("ERRO DE ENTRADA: Digite um número inteiro de 1 a 4.");
                sc.next(); // Consome a entrada inválida
            }
        } catch (MovimentoInvalidoException e) {
            // Captura e trata a exceção(movimento negativo)
            System.err.println("\n MOVIMENTO BLOQUEADO!");
            System.out.println("----------------------------");
            System.err.println("Motivo: " + e.getMessage());
            System.out.println("O robo nao se moveu e permanece na posicao anterior.");
        }
        
        desenharArea(meuRobo, posicaoXAlimento, posicaoYAlimento);
    }

    @Override
    public boolean jogoAcabou() {
        return meuRobo.encontrouAlimento(posicaoXAlimento, posicaoYAlimento);
    }

    @Override
    public void exibirResultados() {
        System.out.println(LINHA_SEPARADORA);
        System.out.println("!!! ALIMENTO ENCONTRADO !!!");
        System.out.printf("O Robo chegou a posicao do alimento (%d, %d), Onde estava o Alimento!.\n", posicaoXAlimento, posicaoYAlimento);
        System.out.println(LINHA_SEPARADORA);
    }
    
}
