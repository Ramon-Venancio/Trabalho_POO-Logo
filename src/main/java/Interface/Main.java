package Interface;
import java.util.InputMismatchException;
import java.util.Scanner;

import Logica.Robo;
import Logica.SimulacaoRunner;
import excecao.MovimentoInvalidoException;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SimulacaoRunner simulacao = new SimulacaoRunner();
                
        System.out.println("Escolha qual modo de simulação você quer");
        System.out.println("1 - Manual");
        System.out.println("2 - Randomica");
        System.out.println("3 - Inteligente");
        System.out.println("4 - Obstaculos");
        
        int opcao = sc.nextInt();
        
        switch (opcao) {
            case 1:
                simulacao.iniciarSimulacao("Manual");
                break;
            case 2:
                simulacao.iniciarSimulacao("Randomica");
                break;
            case 3:
                simulacao.iniciarSimulacao("Inteligente");
                break;
            case 4:
                simulacao.iniciarSimulacao("Obstaculos");
                break;
            default:
                throw new AssertionError();
        }
    }
}
