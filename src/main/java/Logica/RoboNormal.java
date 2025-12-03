package Logica;

/**
 * Representa um robô com comportamento padrão.
 * Ele herda o movimento básico e não possui inteligência de memória.
 */
public class RoboNormal extends Robo {
    
    public RoboNormal(String cor) {
        super(cor);
    }

    // Não precisa sobrescrever mover(), pois ele usa a lógica padrão do pai.
}