/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Logica.Simulacoes.SimulacaoInteligente;
import Logica.Simulacoes.SimulacaoObstaculos;
import Logica.Simulacoes.SimulacaoManual;
import Logica.Simulacoes.SimulacaoRandomica;

/**
 *
 * @author vinan
 */
public class SimulacaoRunner {
    SimulacaoBase simulacao;
    protected static final int MAX_TURNOS = 50;
    
    public void iniciarSimulacao(String opcao) {
        if (opcao.equals("Manual")) {
            simulacao = new SimulacaoManual();
        } else if (opcao.equals("Randomica")) {
            simulacao = new SimulacaoRandomica();
        } else if (opcao.equals("Inteligente")) {
            simulacao = new  SimulacaoInteligente();
        } else if (opcao.equals("Obstaculos")) {
            simulacao = new SimulacaoObstaculos();
        }
        
        simulacao.exibirMensagensIniciais();
        int countTurno = 1;
        while(!simulacao.jogoAcabou() && countTurno<=MAX_TURNOS) {
            simulacao.executarTurno(countTurno);
            countTurno++;
        }
        simulacao.exibirResultados();
    }
}
