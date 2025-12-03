package Logica;

public class Bomba extends Obstaculo {
    
    public Bomba(int id, int posicaoX, int posicaoY) {
        super(id, posicaoX, posicaoY);
    }
    
    @Override
    public boolean bater(Robo robo , int ultimaPosicaoX , int ultimaPosicaoY){
        System.out.println("O robo " + robo.getCor() + " bateu na Bomba " + getId() + " e EXPLODIU!!!!");
        robo.explodir();
        return true;
    }
    @Override
    public String toString(){
        return "Bomba ";
    }
}
