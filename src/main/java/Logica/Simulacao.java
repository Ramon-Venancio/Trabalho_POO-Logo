package Logica;

public interface Simulacao {
    void executarTurno(int turnoAtual); // A lógica de 1 rodada
    boolean jogoAcabou(); // A condição de parada
    void exibirResultados(); // O print final
}