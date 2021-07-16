import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Nave {

    private BufferedImage desenho;
    private int x;
    private int velocidade = 3;
    private boolean cooldown;
    private int recarregando; // 0,5 segundos (25 * 20);

    // CONSTRUTOR
    public Nave() {
        try {
            desenho = ImageIO.read(new File("img/nave.png"));
        } catch(IOException e) {
            System.out.println("Não foi possível carregar a imagem da nave.");
            e.printStackTrace();
        }
        x = 400;
        cooldown = true;
    }

    public void pintar(Graphics2D g) {
        
        g.drawImage(
            desenho,                                    // A imagem utilizada
            x - 25, Main.monitor.getHeight()-150,       // Cordenada X e Y da imagem
            x + 25, Main.monitor.getHeight()-100,       // Tamanho da imagem
            0, 0,                                       // Canto inicial da imagem
            desenho.getWidth(), desenho.getHeight(),    // Canto final da imagem
            null
        );
    }

    // A nave retorna um tiro na posição que ela está
    public Tiro atirar() {
        cooldown = false;
        recarregando = 0;
        Tiro novoTiro =  new Tiro(this.x - 2, Main.monitor.getHeight()-155);
        
        return novoTiro;
    }

    public void move(int x) {
        // se x for positivo >>>
        // se x for negativo <<<
        if(x == 1) {
            if (this.x < Main.monitor.getWidth() - 50) {
                this.x += this.velocidade;
            } 
        } else if(x == -1) {
            if(this.x > 25){
                this.x -= this.velocidade;
            }
        }

        recarregando++;
        if(recarregando >= 13) {
            cooldown = true;
            recarregando = 0;
        }
    }
    public boolean cooldown() {
        return cooldown;
    }
    

    // Cheat
    public void moreMove(int speed) {
        if(this.velocidade >= 0 && this.velocidade <= 20)
            this.velocidade += speed;
    }
}
