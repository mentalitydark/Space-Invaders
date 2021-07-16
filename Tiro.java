import java.awt.Graphics2D;
import java.awt.Color;

public class Tiro {
    private int x, y, velocidade;
    private int tamX = 3;
    private int tamY = 10;

    // Quando cria o tiro
    public Tiro(int x, int y) {

        this.x = x;
        this.y = y;
        this.velocidade = 7;
    }

    public void atualiza() {
        this.y -= velocidade;
    }

    public boolean destruir() {
        if(this.y < 0) {
            return true;
        } else {
            return false;
        }
        // ou return y < 0 que é a mesma coisa
    }

    public boolean target(Inimigo inimigo) {
        if (this.x >= inimigo.getX() && x + tamX < inimigo.getX() + inimigo.getTamanho()) {
            if(this.y <= inimigo.getY() + inimigo.getTamanho() ) {
                return true;
            }
        }
        return false;
    }

    public void pintar(Graphics2D g) {
        g.setColor(Color.orange);
        g.fillRect(x, y, this.tamX, this.tamY);
    }
}
