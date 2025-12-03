package Logica;

import excecao.MovimentoInvalidoException;

public class RoboInteligente extends Robo {
    
    private int ultimoMovimentoInvalido = 0; 

    public RoboInteligente(String cor) {
        super(cor);
    }

    @Override
    public void mover(int codigoDirecao) throws MovimentoInvalidoException {
        
        // Lógica Exclusiva do Inteligente: Verifica memória antes de tentar
        if (codigoDirecao == ultimoMovimentoInvalido && ultimoMovimentoInvalido != 0) {
             this.ultimoMovimentoInvalido = 0; 
             throw new MovimentoInvalidoException("Movimento evitado (Cód: " + codigoDirecao + "). Memória de erro.");
        }
        
        try {
            // Chama a lógica física da classe Pai (Abstract)
            super.mover(codigoDirecao);
            
            // Se funcionou, limpa a memória de erro
            this.ultimoMovimentoInvalido = 0;
            
        } catch (MovimentoInvalidoException e) {
            // Se falhou, aprende e guarda na memória
            this.ultimoMovimentoInvalido = codigoDirecao;
            throw e; 
        }
    }
}