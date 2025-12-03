package Logica;

import excecao.MovimentoInvalidoException;
import java.util.Random;

// 1. Mudamos para ABSTRACT. Isso impede 'new Robo()'
public abstract class Robo {
    public static final int TAMANHO_AREA = 4;
    protected static final Random random = new Random(); // Protected para os filhos usarem se precisarem
    
    private int posicaoX;
    private int posicaoY;
    private String cor;
    private boolean ativo;
    
    // Contadores e estado
    protected int ultimoMovimentoValido = 0; 
    private int movimentosValidos = 0;
    private int movimentosInvalidos = 0;
    
    // Construtor PROTECTED (só filhos podem chamar)
    protected Robo(String cor){
        this.cor = cor;
        this.posicaoX = 0;
        this.posicaoY = 0;
        this.ativo = true;
    }

    // --- MÉTODOS COMUNS (Lógica que todo robô tem igual) ---

    // A lógica básica de movimento físico fica aqui.
    // Os filhos podem usar ela diretamente (Normal) ou sobrescrever (Inteligente).
    public void mover(int codigoDirecao) throws MovimentoInvalidoException {
        if (!this.ativo){
            throw new MovimentoInvalidoException("Robo inativo pela Bomba. Não pode se mover.");
        }
        
        int novoX = this.posicaoX;
        int novoY = this.posicaoY;
        
        switch(codigoDirecao){
            case 1: novoY++; break; // up
            case 2: novoY--; break; // down
            case 3: novoX++; break; // right
            case 4: novoX--; break; // left
            default:
                movimentosInvalidos++;
                throw new MovimentoInvalidoException("Código de direção Inválido! (Use 1 a 4)");
        }
        
        // Validação de Limites
        if(novoX < 0 || novoX >= TAMANHO_AREA || novoY < 0 || novoY >= TAMANHO_AREA){
            movimentosInvalidos++;
            throw new MovimentoInvalidoException("Movimento fora da área (0-" + (TAMANHO_AREA-1) + ").");
        }

        // Confirmação do movimento
        this.posicaoX = novoX;
        this.posicaoY = novoY;
        this.ultimoMovimentoValido = codigoDirecao;
        this.movimentosValidos++;
    }

    public int gerarMovimentoRandomico() {
        return random.nextInt(4) + 1;
    }
    
    public String codigoParaDirecao(int codigoDirecao) {
        switch(codigoDirecao) {
            case 1: return "up";
            case 2: return "down";
            case 3: return "right";
            case 4: return "left";
            default: return "INVALIDO";
        }
    }

    public boolean encontrouAlimento(int alimentoX, int alimentoY){
        return this.posicaoX == alimentoX && this.posicaoY == alimentoY;
    }

    // Getters, Setters e Utilitários
    public boolean isAtivo(){ return ativo; }
    public void explodir(){ this.ativo = false; }
    public String getCor(){ return cor; }
    public int getPosicaoX(){ return posicaoX; }
    public int getPosicaoY(){ return posicaoY; }
    public int getMovimentosValidos() { return movimentosValidos; }
    public int getMovimentosInvalidos() { return movimentosInvalidos; }
    public int getMovimentosTotal () { return movimentosInvalidos + movimentosValidos; }

    public void setPosicaoX(int novaPosicaoX) throws MovimentoInvalidoException {
        if(novaPosicaoX < 0 || novaPosicaoX >= TAMANHO_AREA) throw new MovimentoInvalidoException("X Inválido");
        this.posicaoX = novaPosicaoX;
    }
    
    public void setPosicaoY(int novaPosicaoY) throws MovimentoInvalidoException {
        if(novaPosicaoY < 0 || novaPosicaoY >= TAMANHO_AREA) throw new MovimentoInvalidoException("Y Inválido");
        this.posicaoY = novaPosicaoY;
    }
    
    @Override
    public String toString() {
        return cor + " (" + posicaoX + ", " + posicaoY + ")"; 
    }
}