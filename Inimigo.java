import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.Math;

public class Inimigo {

    private BufferedImage desenho;
    private int x, y;
    private int[][] navesInimigas = {{172, 14, 274, 85}, {172, 14, 274, 85}, {292, 15, 395, 85},
                    {14, 100, 108, 171}, {126, 100, 221, 171}, {238, 100, 308, 171}, {334, 100, 405, 171}};
    private int nave;
    private double velocidade;
    private int direcao;
    private double log = 2;
    
    public Inimigo(BufferedImage desenho, int x, int y,int direcao, int nave) {
        this.desenho = desenho;
        this.x = x;
        this.y = y;
        this.nave = nave;
        this.velocidade = 2.0;
        this.direcao = direcao;

    }


    public void atualizar() {
        x += (velocidade * direcao);
    }

    public void voltar() {
        this.log += 5;
        this.direcao = this.direcao * -1;
        this.y += 50/4;
        this.velocidade = Math.log(log);
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getTamanho() {
        return 50;
    }
    public double getVelocidade() {
        return this.velocidade;
    }
    public int getDirecao() {
        return this.direcao;
    }

    public void pintar(Graphics2D g) {
        
        g.drawImage(desenho, x, y, x +50, y +38, this.navesInimigas[nave][0], this.navesInimigas[nave][1], this.navesInimigas[nave][2], this.navesInimigas[nave][3], null);
    }
}
