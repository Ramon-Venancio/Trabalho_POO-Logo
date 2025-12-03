package Logica;
import excecao.MovimentoInvalidoException;

public abstract class Obstaculo {
    protected final int id;
    protected int posicaoX;
    protected int posicaoY; 

    protected Obstaculo(int id, int posicaoX, int posicaoY){
        this.id = id;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }
    
    public int getId(){return id;}
    
    public int getPosicaoX() {return posicaoX;}
    public void setPosicaoX(int valor) {posicaoX = valor;}
    
    public int getPosicaoY() {return posicaoY;}
    public void setPosicaoY(int valor) {posicaoY = valor;}

    public abstract boolean bater(Robo robo , int ultimaPosicaoX , int ultimaPosicaoY);
}
