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
public class SimulacaoRandomica extends SimulacaoBase {
    private final Robo robo1;
    private final Robo robo2;
    
    public SimulacaoRandomica() {
        super();
        robo1 = new RoboNormal("Verde");
        robo2 = new RoboNormal("Marrom");
    }
    
    @Override
    public void exibirMensagensIniciais() {
        String titulo = String.format("%sROBO LOGO RANDOMICO", " ".repeat(12));
        String mensagem = String.format("%s%n%s%n%s", LINHA_SEPARADORA, titulo, LINHA_SEPARADORA);
        System.out.println(mensagem);
        System.out.printf("Alimento em: (%d, %d)%n", posicaoXAlimento, posicaoYAlimento);
        System.out.println(NOME_ROBO + " " + robo1.toString() + " Iniciado");
        System.out.println(NOME_ROBO + " " + robo2.toString() + " Iniciado");
        
        System.out.println(LINHA_SEPARADORA);
        System.out.println("TURNO 0");
        System.out.println(LINHA_SEPARADORA);
        
        desenharArea(robo1, robo2, posicaoXAlimento, posicaoYAlimento);
    }

    @Override
    public void executarTurno(int turnoAtual) {
        System.out.println("------------------------------------------");
        System.out.println("TURNO " + turnoAtual);
        System.out.println("------------------------------------------");
        
        try {
            // Movimento aleatório do robô 1
            realizarMovimento(robo1);
        } catch (MovimentoInvalidoException e) {
            System.err.println(NOME_ROBO + " " + robo1.getCor() + " falhou no movimento!");
            System.out.println("----------------------------");
            System.err.println("Motivo: " + e.getMessage());
        }
        
        try {
            // Movimento aleatório do robô 2
            realizarMovimento(robo2);
        } catch (Exception e) {
            System.err.println(NOME_ROBO + " " + robo2.getCor() + " falhou no movimento!");
            System.out.println("----------------------------");
            System.err.println("Motivo: " + e.getMessage());

        }
        desenharArea(robo1, robo2, posicaoXAlimento, posicaoYAlimento);
    }

    @Override
    public boolean jogoAcabou() {
        return robo1.encontrouAlimento(posicaoXAlimento, posicaoYAlimento) && robo2.encontrouAlimento(posicaoXAlimento, posicaoYAlimento);
    }

    @Override
    public void exibirResultados() {
        System.out.println("\nResumo de movimentos:");
        System.out.println(NOME_ROBO + " " + robo1.getCor() + " - Válidos: " + robo1.getMovimentosValidos() + " | Inválidos: " + robo1.getMovimentosInvalidos());
        System.out.println(NOME_ROBO + " " + robo2.getCor() + " - Válidos: " + robo2.getMovimentosValidos() + " | Inválidos: " + robo2.getMovimentosInvalidos());
        
         // Resultado final
        System.out.println("\n" + LINHA_SEPARADORA);
        System.out.println("        FIM DA SIMULAÇÃO AUTOMÁTICA");
        System.out.println(LINHA_SEPARADORA);
    }
}
