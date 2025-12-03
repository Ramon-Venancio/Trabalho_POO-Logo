/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica.Simulacoes;

import Logica.Bomba;
import Logica.Obstaculo;
import Logica.Robo;
import Logica.RoboInteligente;
import Logica.RoboNormal;
import Logica.Rocha;
import Logica.SimulacaoBase;
import excecao.MovimentoInvalidoException;
import java.util.*;

/**
 *
 * @author vinan
 */
public class SimulacaoObstaculos extends SimulacaoBase {
    private final Robo roboNormal;
    private final Robo roboInteligente;
    private final List<Obstaculo> obstaculos;
    private final int MAX_OBSTACULOS = 5;
    public int quantBombas;
    public int quantRochas;
    
    public SimulacaoObstaculos() {
        super();
        roboNormal = new RoboNormal("Verde");
        roboInteligente = new RoboInteligente("Marrom");
        quantBombas = 0;
        quantRochas = 0;
        obstaculos = obterObstaculos(posicaoXAlimento, posicaoYAlimento);
    }
    
    @Override
    public void exibirMensagensIniciais() {
        String titulo = String.format("%sSIMULACAO : ROBOS E OBSTACULOS", " ".repeat(12));
        String mensagem = String.format("%s%n%s%n%s", LINHA_SEPARADORA, titulo, LINHA_SEPARADORA);
        System.out.println(mensagem);
        System.out.printf("Alimento em: (%d, %d)%n", posicaoXAlimento, posicaoYAlimento);
        System.out.println("Obstáculos posicionados: " + obstaculos.size());
        System.out.println(NOME_ROBO + " " + roboNormal.toString() + " Iniciado");
        System.out.println(NOME_ROBO + " " + roboInteligente.toString() + " Iniciado");
        
        System.out.println(LINHA_SEPARADORA);
        System.out.println("TURNO 0");
        System.out.println(LINHA_SEPARADORA);
        
        desenharArea(roboNormal, roboInteligente, posicaoXAlimento, posicaoYAlimento, obstaculos);
    }

    @Override
    public void executarTurno(int turnoAtual) {
        System.out.println(LINHA_SEPARADORA);
        System.out.println("TURNO " + turnoAtual);
        System.out.println(LINHA_SEPARADORA);
        if (roboNormal.isAtivo()) {
            try {
                // Movimento aleatório do robô 1
                realizarMovimento(roboNormal);
                verificarBateuObstaculo(roboNormal);
            } catch (MovimentoInvalidoException e) {
                System.err.println(NOME_ROBO + " " + roboNormal.getCor() + " falhou no movimento!");
                System.out.println("----------------------------");
                System.err.println("Motivo: " + e.getMessage());
            }
        }
        
        
        if (roboInteligente.isAtivo()) {
            try {
                // Movimento aleatório do robô 1
                realizarMovimento(roboInteligente);
                verificarBateuObstaculo(roboInteligente);
            } catch (MovimentoInvalidoException e) {
                System.err.println(NOME_ROBO + " " + roboInteligente.getCor() + " falhou no movimento!");
                System.out.println("----------------------------");
                System.err.println("Motivo: " + e.getMessage());
            }
        }
        
        
        desenharArea(roboNormal, roboInteligente, posicaoXAlimento, posicaoYAlimento, obstaculos);
    }

    @Override
    public boolean jogoAcabou() {
        boolean vitoria = roboNormal.encontrouAlimento(posicaoXAlimento, posicaoYAlimento) || roboInteligente.encontrouAlimento(posicaoXAlimento, posicaoYAlimento);
        boolean derrota = !roboNormal.isAtivo() && !roboInteligente.isAtivo();
        return vitoria || derrota;

    }

    @Override
    public void exibirResultados() {
        System.out.println(LINHA_SEPARADORA);
        System.out.println("    !!! SIMULACAO CONCLUIDA !!!");
        System.out.println(LINHA_SEPARADORA);
        
        verificarStatus(roboNormal, posicaoXAlimento, posicaoYAlimento, roboNormal.getMovimentosTotal());
        verificarStatus(roboInteligente, posicaoXAlimento, posicaoYAlimento, roboInteligente.getMovimentosTotal());
        

    }
    
    private int obterCoordenadaObstaculo(String eixo, int coordOposta, int alimentoX, int alimentoY, List<Obstaculo> obstaculos) {
        int coord = 0;
        boolean posicaoValida = false;
        
        while (!posicaoValida) {
            coord = obterEntradaValida(eixo);
            
            // Só checa colisão na segunda coordenada
            if (coordOposta != -1) {
                // Monta a posição (x, y) corretamente
                int x = (eixo.equals("X") ? coord : coordOposta);
                int y = (eixo.equals("Y") ? coord : coordOposta);
                
                if (!checarColisoesBasicas(x, y, alimentoX, alimentoY) && !checarObstaculoExistente(x, y, obstaculos)) {
                    posicaoValida = true;
                }
                
            } else {
                // Se é a primeira coordenada, apenas retorna
                posicaoValida = true;
            }
        }
        
        return coord;
    }
    
    private boolean checarColisoesBasicas(int x, int y, int alimentoX, int alimentoY) {
        if (x == alimentoX && y == alimentoY) {
            System.out.println("ERRO: Posição do obstáculo não pode ser a do alimento!");
            return true;
        }
        if (x == 0 && y == 0) {
            System.out.println("ERRO: Posição do obstáculo não pode ser a posição inicial (0, 0)!");
            return true;
        }
        return false;
    }

    /**
     * Checa se a posição (x, y) já está ocupada por um obstáculo na lista.
     * @return true se houver colisão.
     */
    private boolean checarObstaculoExistente(int x, int y, List<Obstaculo> obstaculos) {
        for (Obstaculo po : obstaculos) {
            if (po.getPosicaoX() == x && po.getPosicaoY() == y) {
                System.out.printf("ERRO: Posição (%d, %d) já ocupada por outro obstáculo!%n", x, y);
                return true;
            }
        }
        return false;
    }
    
    // Método para permitir o usuário inserir os obstáculos
    private List<Obstaculo> obterObstaculos(int alimentoX, int alimentoY) {
        List<Obstaculo> listaTemporaria = new ArrayList<>();
        String continuar = "S";
        
        System.out.println("\n--- CONFIGURACAO DE OBSTÁCULOS ---");
        System.out.println("Você pode adicionar Rochas ou Bombas na área. (O maximo são 5)");

        while (continuar.toUpperCase().startsWith("S") && listaTemporaria.size() < MAX_OBSTACULOS) {
            System.out.println("\nAdicionar novo obstáculo:");
            
            // TIPO
            int tipo = 0;
            while(tipo < 1 || tipo > 2) {
                System.out.print("Tipo (1: Bomba / 2: Rocha): ");
                if (sc.hasNextInt()) {
                    tipo = sc.nextInt();
                } else {
                    sc.next();
                }
            }
            
            // COORDENADAS: Chamamos o método específico que valida a colisão
            int obstaculoX = obterCoordenadaObstaculo("X", -1, alimentoX, alimentoY, listaTemporaria);
            int obstaculoY = obterCoordenadaObstaculo("Y", obstaculoX, alimentoX, alimentoY, listaTemporaria);
            
            Obstaculo obstaculo;
            
            // CRIAÇÃO
            if (tipo == 1) {
                obstaculo = new Bomba(quantBombas, obstaculoX, obstaculoY);
                listaTemporaria.add(obstaculo);
                quantBombas++;
            } else {
                obstaculo = new Rocha(quantRochas, obstaculoX, obstaculoY);
                listaTemporaria.add(obstaculo);
                quantRochas++;
            }
            
            System.out.println("(" + listaTemporaria.size() + "/5)");
            System.out.println("Obstáculo adicionado!");
            
            if (listaTemporaria.size() != MAX_OBSTACULOS) {
                System.out.print("Deseja adicionar mais obstáculos? (S/N): ");
                continuar = sc.next();
            } else {
                System.out.print("Limite de obstaculos excedido!");
            }
        }
        return listaTemporaria;
    }
    
    private void verificarStatus(Robo robo, int alimentoX, int alimentoY, long movimentos) {
        String status;
        
        if (robo.encontrouAlimento(alimentoX, alimentoY)) {
            status = "VENCEU! Encontrou o alimento.";
        } else if (!robo.isAtivo()) {
            status = "EXPLODIU! Posição: " + robo.toString();
        } else {
            status = "Perdeu, mas permaneceu ativo. Posição: " + robo.toString();
        }
        
        System.out.printf("%n--- %s %s (%s) ---%n", NOME_ROBO, 
            (robo instanceof RoboInteligente ? "Inteligente" : "Normal"),
            robo.getCor());
        System.out.println("STATUS: " + status);
        System.out.println("Total de movimentos tentados: " + movimentos);
    }
    
    private void verificarBateuObstaculo(Robo robo) {
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.getPosicaoX() == robo.getPosicaoX() && obstaculo.getPosicaoY() == robo.getPosicaoY()) {
                obstaculo.bater(robo, robo.getPosicaoX(), robo.getPosicaoY());
            }
        }
    }
}