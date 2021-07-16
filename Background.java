import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Background {
    private BufferedImage bg;
    private int y;


    public Background() {
        try {
            bg = ImageIO.read(new File("img/Space02.png"));
        } catch (Exception e) {
            System.out.println("Não foi possível carregar o BackGround.");
            e.printStackTrace();
        }

        y = 0;
    }

    public void pinta(Graphics2D g) {
        int altura = Main.monitor.getHeight();
        g.setColor(Color.black);
        g.fillRect(0, 0, Main.monitor.getWidth(), Main.monitor.getHeight());

        // Imagem
        g.drawImage(bg, 0, y-900, bg.getWidth() + 500, bg.getHeight(), null);
        g.drawImage(bg, 0, y-600, bg.getWidth() + 500, bg.getHeight(), null);
        g.drawImage(bg, 0, y-300, bg.getWidth() + 500, bg.getHeight(), null);
        g.drawImage(bg, 0, y, bg.getWidth() + 500, bg.getHeight(), null);
        g.drawImage(bg, 0, y+300, bg.getWidth() + 500, bg.getHeight(), null);
        g.drawImage(bg, 0, y+600, bg.getWidth() + 500, bg.getHeight(), null);

        
        y += 5;
        if (y > altura + 135) {
            y = 0;
        }
    }
}
