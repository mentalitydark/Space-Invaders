import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Explosao {
    private BufferedImage imagem;
    private int x, y;
    private double velocidade;
    private int direcao;
    private int duration;

    private int Xinicial;
    private int Yinicial;

    public Explosao(BufferedImage imagem, int x, int y, double velocidade, int direcao) {
        
        this.imagem = imagem;
        this.x = x;
        this.y = y;
        this.velocidade = velocidade;
        this.direcao = direcao;
        this.Xinicial = 0;
        this.Yinicial = 0;
        this.duration = 80;
    }

    public void pintar(Graphics2D g) {
        g.drawImage(this.imagem, this.x, this.y, this.x + 50, this.y + 38,
        this.Xinicial, 
        this.Yinicial,
        this.Xinicial+100,
        this.Yinicial+100, 
         
        null);

    }
    public void atualizar() {
        
        this.x += velocidade * direcao;
        this.Xinicial += 100;
        if(Xinicial == 900) {
            this.Yinicial += 100;
            this.Xinicial = 0;
        }
    }
    public boolean getDuration() {
        if(this.duration <= 0) {
            return true;
        } else {
            return false;
        }
    }
    
}
