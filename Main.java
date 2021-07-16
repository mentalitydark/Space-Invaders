import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.DisplayMode;
import java.awt.image.BufferedImage;
import java.awt.Cursor;
import java.awt.Point;

import javax.swing.JFrame;


public class Main{

    static DisplayMode monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    public static void main(String[] args) {
        
        JFrame janela = new JFrame("Space Invaders");

        // Cursor
        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank cursor");

        //janela.setSize(800, 600);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setUndecorated(true);
        janela.setLayout(null);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpaceInvader jogo = new SpaceInvader();
        jogo.setBounds(0, 0, monitor.getWidth(),  monitor.getWidth());

        // Add Cursor
        janela.getContentPane().setCursor(blankCursor);

        janela.add(jogo);

        janela.addKeyListener(jogo);

        janela.setVisible(true);

    }
}